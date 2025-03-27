package com.shortthirdman.flowmart.temporal.activity;

import com.shortthirdman.flowmart.model.AlertLevel;
import com.shortthirdman.flowmart.model.OrderStatus;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface OrderActivity {

    void validateOrder(String orderId);

    void processPayment(String orderId);

    void shipOrder(String orderId);

    void sendConfirmation(String orderId);

    void refundPayment(String orderId);

    void cancelShipment(String orderId);

    void sendCancellationNotification(String orderId, String reason);

    void logOrderEvent(String orderId, String event, OrderStatus status);

    void sendAlert(String orderId, AlertLevel level, String message);
}
