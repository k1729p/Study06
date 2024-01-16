package kp.workers;

import java.util.Map;

import io.camunda.zeebe.client.api.response.ActivatedJob;

/**
 * The base interface for the Zeebe workers.
 * <p>
 * The Zeebe workers: the components that subscribe to Zeebe to execute
 * available jobs.
 */
public interface WorkerBase {
	/**
	 * Handles the service task.
	 * 
	 * @param activatedJob the {@link ActivatedJob}
	 * @return the result map
	 */
	Map<String, Object> handle(ActivatedJob activatedJob);

}
