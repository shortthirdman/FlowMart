package com.shortthirdman.flowmart.model;

public enum OrderStatus {
    CREATED,
    VALIDATED,
    PAYMENT_PROCESSED,
    SHIPPED,
    COMPLETED,
    FAILED,
    COMPENSATING,
    CANCELLED
}
