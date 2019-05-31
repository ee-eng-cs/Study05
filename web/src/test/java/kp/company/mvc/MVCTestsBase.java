package kp.company.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import kp.company.TestsBase;

/**
 * The base class for tests with server-side support.<br>
 * The server is <b>not started</b>.<br>
 * These tests use the 'Spring MVC Test Framework'.<br>
 * This framework does not use a running Servlet container.
 *
 */
public abstract class MVCTestsBase extends TestsBase {
	@Autowired
	protected MockMvc mockMvc;
}