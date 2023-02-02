package kp.workers.impl;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import kp.Constants;
import kp.workers.WorkerBase;

/**
 * The Zeebe worker for the issue <b>approval</b>.
 * 
 */
@Component
public class ApproveWorker implements WorkerBase {
	private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass().getName());
	private static final int SLEEP_SECONDS = 1;

	/**
	 * The constructor.
	 * 
	 */
	public ApproveWorker() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	@JobWorker(type = "service-approve")
	public Map<String, Object> handle(ActivatedJob activatedJob) {

		sleepSeconds();

		final HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put(Constants.RESULT_KEY, Constants.RESULT_APPROVED);
		final String message = String.format("handle():%n\tinput variables[%s],%n\tresult map[%s]",
				activatedJob.getVariables(), resultMap);
		logger.info(message);
		return resultMap;
	}

	/**
	 * Pauses for given seconds.
	 * 
	 */
	private void sleepSeconds() {

		try {
			TimeUnit.SECONDS.sleep(SLEEP_SECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
