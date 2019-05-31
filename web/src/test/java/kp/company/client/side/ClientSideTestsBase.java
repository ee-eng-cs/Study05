package kp.company.client.side;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import kp.company.TestsBase;

/**
 * The base class for client side tests.<br>
 * The server is <b>started</b>.
 *
 */
public abstract class ClientSideTestsBase extends TestsBase {

	@LocalServerPort
	protected int port;

	@Autowired
	protected TestRestTemplate restTemplate;
}