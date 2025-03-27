package com.shortthirdman.flowmart.model;

public record OrderResult(String orderId, OrderStatus status, String message) {
}
