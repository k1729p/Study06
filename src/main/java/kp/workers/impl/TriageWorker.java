package kp.workers.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import kp.Constants;
import kp.workers.WorkerBase;

/**
 * The Zeebe worker for the defect's <b>triage</b>.
 * <p>
 * Uses the {@link DmnEngine} and the {@link DmnDecision}.
 */
@Component
public class TriageWorker implements WorkerBase {

	private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass().getName());

	private DmnEngine dmnEngine = null;
	private DmnDecision dmnDecision = null;

	/**
	 * The constructor.
	 * 
	 */
	public TriageWorker() {
		super();
		initialize();
	}

	/**
	 * Initializes the {@link DmnEngine} and the {@link DmnDecision}.
	 * 
	 */
	private void initialize() {

		dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(Constants.DECISION_DIAGRAM)) {
			dmnDecision = dmnEngine.parseDecision(Constants.DECISION_KEY, inputStream);
		} catch (IOException e) {
			logger.error("initialize(): exception[" + e.getMessage() + "]");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	@JobWorker(type = "service-triage")
	public Map<String, Object> handle(ActivatedJob activatedJob) {

		final VariableMap variableMap = Variables.fromMap(activatedJob.getVariablesAsMap());
		// evaluate a decision which is implemented as a decision table
		final DmnDecisionTableResult dmnDecisionTableResult = dmnEngine.evaluateDecisionTable(dmnDecision, variableMap);
		final Long priority = Optional.ofNullable(dmnDecisionTableResult)
				.map(result -> result.collectEntries(Constants.PRIORITY_KEY))//
				.filter(Predicate.not(List::isEmpty)).map(List::getFirst)//
				.filter(Long.class::isInstance).map(Long.class::cast).orElse(0L);

		final HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put(Constants.PRIORITY_KEY, priority);
		resultMap.put(Constants.RESULT_KEY, Constants.RESULT_EVALUATED);
		final String message = String.format("handle():%n\tinput variables[%s],%n\tresult map[%s]",
				activatedJob.getVariables(), resultMap);
		logger.info(message);
		return resultMap;
	}

}
