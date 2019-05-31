package kp.company.client.side;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import kp.company.TestConstants;
import kp.company.domain.Employee;

/**
 * Client side tests for employee.<br>
 * The server is <b>started</b>.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class EmployeeClientSideTests extends ClientSideTestsBase {
	private static final Log logger = LogFactory.getLog(EmployeeClientSideTests.class);

	/**
	 * Should find employee.
	 */
	@Test
	public void shouldFindEmployee() {

		// GIVEN
		// WHEN
		final ResponseEntity<Employee> responseEntity = restTemplate.getForEntity("/employees/3", Employee.class);
		// THEN
		Assertions.assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK));
		final Employee employee = responseEntity.getBody();
		Assertions.assertThat(employee).isNotNull();
		Assertions.assertThat(employee.getFirstName().equals(TestConstants.EMPLOYEE_FIRST_NAME));
		Assertions.assertThat(employee.getLastName().equals(TestConstants.EMPLOYEE_LAST_NAME));
		logger.info("shouldFindEmployee():");
	}
}