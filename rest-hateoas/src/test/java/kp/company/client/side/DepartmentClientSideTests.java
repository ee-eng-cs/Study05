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
import kp.company.domain.Department;

/**
 * Client side tests for department.<br>
 * The server is <b>started</b>.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class DepartmentClientSideTests extends ClientSideTestsBase {
	private static final Log logger = LogFactory.getLog(DepartmentClientSideTests.class);

	/**
	 * Should find department.
	 */
	@Test
	public void shouldFindDepartment() {

		// GIVEN
		// WHEN
		final ResponseEntity<Department> responseEntity = restTemplate.getForEntity("/departments/1", Department.class);
		// THEN
		Assertions.assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK));
		final Department department = responseEntity.getBody();
		Assertions.assertThat(department).isNotNull();
		Assertions.assertThat(department.getName().equals(TestConstants.DEPARTMENT_NAME));
		logger.info("shouldFindDepartment():");
	}
}