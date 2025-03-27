package com.shortthirdman.flowmart.temporal.workflow;

import com.shortthirdman.flowmart.model.OrderResult;
import com.shortthirdman.flowmart.model.OrderStatus;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderProcessingWorkflow {

    @WorkflowMethod
    OrderResult processOrder(String orderId);

    @QueryMethod
    OrderStatus getOrderStatus();

    @SignalMethod
    void cancelOrder(String reason);
}
