package kp.company;

import org.springframework.boot.SpringApplication;
/*-
 Spring MVC + Spring HATEOAS app with HAL representations of each resource
 
 HATEOAS - Hypermedia As The Engine Of Application State
 HAL - JSON Hypertext Application Language
 */
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*-
The @SpringBootApplication annotation is equivalent to using:
 1. @EnableAutoConfiguration: Spring Boot’s auto-configuration mechanism
 2. @ComponentScan: @Component scan on the package where the application is located
 3. @Configuration: registers extra beans in the context or imports additional configuration classes
 
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

CrudRepository
   |--- JpaRepository
   \--- MongoRepository  
*/
/**
 * An application that accesses relational JPA data<br>
 * through a hypermedia-based RESTful Web Service.
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
	}
}
