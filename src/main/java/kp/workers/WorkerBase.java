package kp.workers;

import io.camunda.client.api.response.ActivatedJob;

import java.util.Map;

/**
 * The base interface for Zeebe workers.
 * <p>
 * Zeebe workers are components that subscribe to Zeebe to execute available jobs.
 * </p>
 */
public interface WorkerBase {
    /**
     * Handles the service task.
     *
     * @param activatedJob the job to be handled
     * @return the result map containing the outcome of the service task
     */
    Map<String, Object> handle(ActivatedJob activatedJob);

}
