package com.shortthirdman.flowmart.controller;

import com.shortthirdman.flowmart.temporal.workflow.OrderProcessingWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final WorkflowClient workflowClient;

    @PostMapping("/orders/{orderId}/process")
    public ResponseEntity<String> processOrder(@PathVariable String orderId) {
        OrderProcessingWorkflow workflow = workflowClient.newWorkflowStub(
                OrderProcessingWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("OrderProcessingQueue")
                        .setWorkflowId("Order-" + orderId)
                        .build());

        WorkflowClient.start(workflow::processOrder, orderId);
        return ResponseEntity.accepted().body("Order processing started");
    }
}
