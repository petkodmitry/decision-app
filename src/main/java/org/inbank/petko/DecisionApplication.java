package org.inbank.petko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Test task performing. <a href="file:../../../../../../InBank-TestTask.pdf" >See the Task here</a>
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@SpringBootApplication
public class DecisionApplication extends SpringBootServletInitializer {

	/**
	 * Main method to start Spring Boot Application
	 * @param args passed args
	 */
	public static void main(String[] args) {
		SpringApplication.run(DecisionApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DecisionApplication.class);
	}
}
