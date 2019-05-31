package kp.company;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.RequestHeadersSpec;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

/**
 * The department router test.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentRouterTest {

	@Autowired
	private WebTestClient webTestClient;

	private static final String DEPARTMENT_URI = "/company/department";
	private static final String EXPECTED_DEPARTMENT_NAME = "D-e-p-a-r-t-m-e-n-t-01";

	/**
	 * Tests the department.
	 * 
	 */
	@Test
	public void testDepartment() {
		// GIVEN
		final RequestHeadersSpec<?> requestHeadersSpec = webTestClient.get()/*-*/
				.uri(DEPARTMENT_URI).accept(MediaType.TEXT_PLAIN);
		// WHEN
		final ResponseSpec responseSpec = requestHeadersSpec.exchange();
		// THEN
		/*- use the dedicated DSL (domain-specific language)
		    to test assertions against the response */
		responseSpec.expectStatus().isOk();
		responseSpec.expectBody(String.class).isEqualTo(EXPECTED_DEPARTMENT_NAME);
	}
}
