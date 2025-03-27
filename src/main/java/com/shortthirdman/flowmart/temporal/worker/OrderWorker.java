package com.shortthirdman.flowmart.temporal.worker;

import com.shortthirdman.flowmart.temporal.activity.OrderActivityImpl;
import com.shortthirdman.flowmart.temporal.workflow.OrderProcessingWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderWorker {

    private final Worker worker;

    public OrderWorker(WorkflowClient workflowClient) {
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        this.worker = factory.newWorker("OrderProcessingQueue");

        worker.registerWorkflowImplementationTypes(OrderProcessingWorkflowImpl.class);
        worker.registerActivitiesImplementations(new OrderActivityImpl());
        factory.start();
    }
}
