package kp.workers.impl;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import kp.Constants;
import kp.workers.WorkerBase;

/**
 * The Zeebe worker for the <b>low</b> priority issues.
 *
 */
@Component
public class LowPriorityWorker implements WorkerBase {
	private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass().getName());

	/**
	 * The constructor.
	 * 
	 */
	public LowPriorityWorker() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	@JobWorker(type = "service-priority-low")
	public Map<String, Object> handle(ActivatedJob activatedJob) {

		final HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put(Constants.RESULT_KEY, Constants.RESULT_SOLVED);
		final String message = String.format("handle():%n\tinput variables[%s],%n\tresult map[%s]",
				activatedJob.getVariables(), resultMap);
		logger.info(message);
		return resultMap;
	}

}
