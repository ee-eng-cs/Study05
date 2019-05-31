package kp.company.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

/**
 * The base class for tests with server-side support.<br>
 * The server is <b>not started</b>.<br>
 * These tests use the 'Spring MVC Test Framework'.<br>
 * 
 * Loads a web ApplicationContext and provides a mock web environment.<br>
 * The full Spring application context is started, but without the server.
 */
public abstract class MvcTestsBase {

	@Autowired
	protected MockMvc mockMvc;
}