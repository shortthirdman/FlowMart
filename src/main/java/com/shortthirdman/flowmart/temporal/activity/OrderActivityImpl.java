package com.shortthirdman.flowmart.temporal.activity;

import com.shortthirdman.flowmart.model.AlertLevel;
import com.shortthirdman.flowmart.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderActivityImpl implements OrderActivity {

    @Override
    public void validateOrder(String orderId) {
        log.info("Validating order {}", orderId);
    }

    @Override
    public void processPayment(String orderId) {
        log.info("Processing payment {}", orderId);
    }

    @Override
    public void shipOrder(String orderId) {
        log.info("Shipping order {}", orderId);
    }

    @Override
    public void sendConfirmation(String orderId) {
        log.info("Sending confirmation {}", orderId);
    }

    @Override
    public void refundPayment(String orderId) {
        log.info("Refunding payment {}", orderId);
    }

    @Override
    public void cancelShipment(String orderId) {
        log.info("Cancelling shipment {}", orderId);
    }

    @Override
    public void sendCancellationNotification(String orderId, String reason) {
        log.info("Cancelling notification {}", orderId);
    }

    @Override
    public void logOrderEvent(String orderId, String event, OrderStatus status) {
        log.info("Order {} event {}", orderId, event);
    }

    @Override
    public void sendAlert(String orderId, AlertLevel level, String message) {
        log.info("Sending alert {}", orderId);
    }
}
