package kp.decisions;

import java.io.IOException;
import java.io.InputStream;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kp.Constants;

/**
 * The tests of the decisions .
 *
 */
class DecisionsTests {

	private DmnEngine dmnEngine;
	private DmnDecision dmnDecision;

	private static final VariableMap VARIABLES_HIGH = Variables//
			.putValue(Constants.IMPACT_KEY, "medium").putValue(Constants.URGENCY_KEY, "high");
	private static final VariableMap VARIABLES_MEDIUM = Variables//
			.putValue(Constants.IMPACT_KEY, "medium").putValue(Constants.URGENCY_KEY, "medium");
	private static final VariableMap VARIABLES_LOW = Variables//
			.putValue(Constants.IMPACT_KEY, "low").putValue(Constants.URGENCY_KEY, "medium");

	private static final long PRIORITY_HIGH = 80;
	private static final long PRIORITY_MEDIUM = 50;
	private static final long PRIORITY_LOW = 20;

	/**
	 * The constructor.
	 * 
	 */
	public DecisionsTests() {
		super();
	}

	/**
	 * Prepares the decision model and parses the decision.
	 * 
	 * @throws IOException the exception
	 */
	@BeforeEach
	void parseDecision() throws IOException {

		dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(Constants.DECISION_DIAGRAM)) {
			dmnDecision = dmnEngine.parseDecision(Constants.DECISION_KEY, inputStream);
		}
	}

	/**
	 * Should decide the high priority.
	 * 
	 */
	@Test
	void shouldDecideHighPriority() {
		// GIVEN
		// WHEN
		final DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(dmnDecision, VARIABLES_HIGH);
		// THEN
		final Long actualPriority = result.getSingleResult().getSingleEntry();
		Assertions.assertThat(actualPriority).isEqualTo(PRIORITY_HIGH);
	}

	/**
	 * Should decide the medium priority.
	 * 
	 */
	@Test
	void shouldDecideMediumPriority() {
		// GIVEN
		// WHEN
		final DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(dmnDecision, VARIABLES_MEDIUM);
		// THEN
		final Long actualPriority = result.getSingleResult().getSingleEntry();
		Assertions.assertThat(actualPriority).isEqualTo(PRIORITY_MEDIUM);
	}

	/**
	 * Should decide the low priority.
	 * 
	 */
	@Test
	void shouldDecideLowPriority() {
		// GIVEN
		// WHEN
		final DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(dmnDecision, VARIABLES_LOW);
		// THEN
		final Long actualPriority = result.getSingleResult().getSingleEntry();
		Assertions.assertThat(actualPriority).isEqualTo(PRIORITY_LOW);
	}
}
