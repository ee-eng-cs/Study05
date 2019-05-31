package kp.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kp.client.WebClientLauncher;

/**
 * The Reactive RESTful Web Service example.
 * 
 */
@SpringBootApplication
public class Application {

	/**
	 * Main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
		WebClientLauncher.launch();
	}
}
