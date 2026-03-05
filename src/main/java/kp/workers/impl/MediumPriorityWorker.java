package kp.workers.impl;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import kp.Constants;
import kp.workers.WorkerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

/**
 * The worker for handling <b>medium</b> priority issues.
 */
@Component
public class MediumPriorityWorker implements WorkerBase {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Handles the job for medium priority service.
     *
     * @param activatedJob the job to be handled
     * @return the result map containing the solution result
     */
    @Override
    @JobWorker(type = "service-priority-medium")
    public Map<String, Object> handle(ActivatedJob activatedJob) {

        final HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put(Constants.RESULT_KEY, Constants.RESULT_SOLVED);
        if (logger.isInfoEnabled()) {
            logger.info("handle():\n\tinput variables[{}],\n\tresult map[{}]",
                    activatedJob.getVariables(), resultMap);
        }
        return resultMap;
    }

}
