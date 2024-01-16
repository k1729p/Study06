package kp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.spring.client.annotation.Deployment;

/**
 * The SpringBoot application with enabled Zeebe client.
 * <p>
 * The process solution with included process definitions (BPMN diagram and DRD
 * diagram).
 */
@SpringBootApplication
@Deployment(resources = Constants.BUSINESS_PROCESS_DIAGRAM)
public class Application {

	/**
	 * The constructor.
	 */
	public Application() {
		super();
	}

	/**
	 * The entry point for the application.
	 * 
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}
}