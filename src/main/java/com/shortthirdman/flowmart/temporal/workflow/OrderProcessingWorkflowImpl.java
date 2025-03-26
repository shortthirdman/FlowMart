package com.shortthirdman.flowmart.temporal.workflow;

import com.shortthirdman.flowmart.model.AlertLevel;
import com.shortthirdman.flowmart.model.OrderResult;
import com.shortthirdman.flowmart.model.OrderStatus;
import com.shortthirdman.flowmart.temporal.activity.OrderActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class OrderProcessingWorkflowImpl implements OrderProcessingWorkflow {

    private OrderStatus currentStatus = OrderStatus.CREATED;
    private boolean paymentProcessed = false;
    private boolean shipmentCreated = false;

    private final ActivityOptions options = ActivityOptions.newBuilder()
            .setScheduleToCloseTimeout(Duration.ofMinutes(5))
            .setRetryOptions(RetryOptions.newBuilder()
                    .setMaximumAttempts(3)
                    .setInitialInterval(Duration.ofSeconds(1))
                    .setMaximumInterval(Duration.ofSeconds(10))
                    .build())
            .build();

    private final OrderActivity orderActivity = Workflow.newActivityStub(OrderActivity.class, options);

    @Override
    public OrderResult processOrder(String orderId) {
        try {
            try {
                orderActivity.validateOrder(orderId);
                currentStatus = OrderStatus.VALIDATED;
                orderActivity.logOrderEvent(orderId, "Order validated", currentStatus);
            } catch (Exception e) {
                handleFailure(orderId, "Order validation failed", e);
                return new OrderResult(orderId, currentStatus, "Validation failed: " + e.getMessage());
            }

            // Process Payment
            try {
                log.info("Processing payment for order {}", orderId);
                orderActivity.processPayment(orderId);
                paymentProcessed = true;
                currentStatus = OrderStatus.PAYMENT_PROCESSED;
                orderActivity.logOrderEvent(orderId, "Payment processed", currentStatus);
            } catch (Exception e) {
                handleFailure(orderId, "Payment processing failed", e);
                return new OrderResult(orderId, currentStatus, "Payment failed: " + e.getMessage());
            }

            // Ship Order
            try {
                log.info("Shipping order {}", orderId);
                orderActivity.shipOrder(orderId);
                shipmentCreated = true;
                currentStatus = OrderStatus.SHIPPED;
                orderActivity.logOrderEvent(orderId, "Order shipped", currentStatus);
            } catch (Exception e) {
                handleFailure(orderId, "Shipping failed", e);
                return new OrderResult(orderId, currentStatus, "Shipping failed: " + e.getMessage());
            }

            // Send Confirmation
            try {
                log.info("Sending confirmation after order processing {}", orderId);
                orderActivity.sendConfirmation(orderId);
                currentStatus = OrderStatus.COMPLETED;
                orderActivity.logOrderEvent(orderId, "Order completed", currentStatus);
                return new OrderResult(orderId, currentStatus, "Order completed successfully");
            } catch (Exception e) {
                handleFailure(orderId, "Confirmation failed", e);
                return new OrderResult(orderId, currentStatus, "Confirmation failed: " + e.getMessage());
            }

        } catch (Exception e) {
            handleFailure(orderId, "Unexpected error", e);
            return new OrderResult(orderId, currentStatus, "Unexpected error: " + e.getMessage());
        }
    }

    @Override
    public OrderStatus getOrderStatus() {
        return currentStatus;
    }

    @Override
    public void cancelOrder(String reason) {
        if (currentStatus == OrderStatus.COMPLETED || currentStatus == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot cancel completed or already cancelled order");
        }
        compensate(Workflow.getInfo().getWorkflowId(), reason);
    }

    /**
     * Handle workflow failure
     * @param orderId the order identifier
     * @param message the message
     * @param e the throwable cause
     */
    private void handleFailure(String orderId, String message, Exception e) {
        currentStatus = OrderStatus.COMPENSATING;
        orderActivity.logOrderEvent(orderId, message, currentStatus);
        orderActivity.sendAlert(orderId, AlertLevel.ERROR, message + ":" + e.getMessage());
    }

    /**
     * Compensate order
     * @param orderId the order identifier
     * @param reason the reason for compensation
     */
    private void compensate(String orderId, String reason) {
        try {
            if (shipmentCreated) {
                orderActivity.cancelShipment(orderId);
                orderActivity.logOrderEvent(orderId, "Shipment cancelled", currentStatus);
            }

            if (paymentProcessed) {
                orderActivity.refundPayment(orderId);
                orderActivity.logOrderEvent(orderId, "Payment refunded", currentStatus);
            }

            orderActivity.sendCancellationNotification(orderId, reason);
            currentStatus = OrderStatus.CANCELLED;
            orderActivity.logOrderEvent(orderId, "Order cancelled", currentStatus);
        } catch (Exception e) {
            currentStatus = OrderStatus.FAILED;
            orderActivity.sendAlert(orderId, AlertLevel.CRITICAL, "Compensation failed: " + e.getMessage());
        }
    }
}
