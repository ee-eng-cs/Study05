package kp.company.client.side;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

/**
 * The base class for client side tests.<br>
 * The server is <b>started</b>.<br>
 * The full-stack integration test.<br>
 * Loads a WebServerApplicationContext and provides a real web environment.
 */
public class ClientSideTestsBase {
	@Autowired
	protected TestRestTemplate restTemplate;
}