package kp.company.client.side;

import java.net.HttpURLConnection;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import kp.company.TestConstants;
import kp.company.domain.Title;
import kp.company.service.CompanyService;

/**
 * Client side tests for employee.<br>
 * The server is <b>started</b>.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeClientSideTests extends ClientSideTestsBase {

	/**
	 * Should list employees.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldListEmployees() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/listEmployees?departmentId=1", port);
		// WHEN
		final ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, response.getStatusCodeValue());
		final String responseBody = response.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("employees"));
		Assertions.assertThat(responseBody).contains(TestConstants.DEPARTMENT_NAME);
		// there is given employee in the list
		Assertions.assertThat(responseBody).contains(TestConstants.EMPLOYEE_FIRST_NAME);
		Assertions.assertThat(responseBody).contains(TestConstants.EMPLOYEE_LAST_NAME);
		Assertions.assertThat(responseBody).contains(CompanyService.getTitleList().get(0).getName());
		Assertions.assertThat(responseBody).contains(accessor.getMessage("addEmployee"));
	}

	/**
	 * Should start employee adding.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldStartEmployeeAdding() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/startEmployeeAdding?departmentId=1", port);
		// WHEN
		final ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, response.getStatusCodeValue());
		final String responseBody = response.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("addEmployee"));
		Assertions.assertThat(responseBody).contains(accessor.getMessage("firstName"));
		Assertions.assertThat(responseBody).contains(accessor.getMessage("lastName"));
		Assertions.assertThat(responseBody).contains(accessor.getMessage("title"));
		Assertions.assertThat(responseBody).contains(accessor.getMessage("save"));
	}

	/**
	 * Should start employee editing.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldStartEmployeeEditing() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/startEmployeeEditing?departmentId=1&employeeId=1",
				port);
		// WHEN
		final ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, response.getStatusCodeValue());
		final String responseBody = response.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("editEmployee"));
		Assertions.assertThat(responseBody).contains(TestConstants.EMPLOYEE_FIRST_NAME);
		Assertions.assertThat(responseBody).contains(TestConstants.EMPLOYEE_LAST_NAME);
		Assertions.assertThat(responseBody).contains(CompanyService.getTitleList().get(0).getName());
		Assertions.assertThat(responseBody).contains(accessor.getMessage("save"));
	}

	/**
	 * Should not start editing non-existent employee.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldNotStartEmployeeEditing() throws Exception {
		// GIVEN
		final String requestUrl = String
				.format("http://localhost:%s/startEmployeeEditing?departmentId=1&employeeId=999", port);
		// WHEN
		final ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_NOT_FOUND, response.getStatusCodeValue());
		final String responseBody = response.getBody();
		Assertions.assertThat(responseBody).contains("\"message\":\"Department or employee not found\"");
		Assertions.assertThat(responseBody).contains("\"path\":\"/startEmployeeEditing\"");
	}

	/**
	 * Should save employee.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldSaveEmployee() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/finishEmployeeEditing", port);
		final MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("save", "");
		paramMap.add("id", "1");
		paramMap.add("firstName", TestConstants.EMPLOYEE_FIRST_NAME_CHANGED);
		paramMap.add("lastName", TestConstants.EMPLOYEE_LAST_NAME_CHANGED);
		paramMap.add("title", Title.ANALYST.name().toUpperCase());
		paramMap.add("departmentId", "1");
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				paramMap, new HttpHeaders());
		// WHEN
		final ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_MOVED_TEMP, response.getStatusCodeValue());
		Assert.assertEquals(response.getHeaders().getLocation().getPath(), "/listEmployees");
		Assert.assertEquals(response.getHeaders().getLocation().getQuery(), "departmentId=1");

		// GIVEN
		final String requestUrlRedir = String.format("http://localhost:%s/listEmployees?departmentId=1", port);
		// WHEN
		final ResponseEntity<String> responseRedir = restTemplate.getForEntity(requestUrlRedir, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, responseRedir.getStatusCodeValue());
		final String responseBody = responseRedir.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("employees"));
		Assertions.assertThat(responseBody).contains(TestConstants.DEPARTMENT_NAME);
		// there is given employee in the list
		Assertions.assertThat(responseBody).contains(TestConstants.EMPLOYEE_FIRST_NAME_CHANGED);
		Assertions.assertThat(responseBody).contains(TestConstants.EMPLOYEE_LAST_NAME_CHANGED);
		Assertions.assertThat(responseBody).contains(CompanyService.getTitleList().get(0).getName());
		Assertions.assertThat(responseBody).contains(accessor.getMessage("addEmployee"));
	}

	/**
	 * Should validate employee and show validation error.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldValidateEmployeeAndShowValidationError() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/finishEmployeeEditing", port);
		final MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("save", "");
		paramMap.add("id", "1");
		paramMap.add("title", Title.ANALYST.name().toUpperCase());
		paramMap.add("departmentId", "1");
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				paramMap, new HttpHeaders());
		// WHEN
		final ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, response.getStatusCodeValue());
		final String responseBody = response.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("editEmployee"));
		// validation error
		Assertions.assertThat(responseBody).contains("must not be blank");
		Assertions.assertThat(responseBody).contains(accessor.getMessage("save"));
	}

	/**
	 * Should cancel employee editing.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldCancelEmployeeEditing() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/finishEmployeeEditing", port);
		final MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("cancel", "");
		paramMap.add("id", "1");
		paramMap.add("firstName", TestConstants.EMPLOYEE_FIRST_NAME_CHANGED);
		paramMap.add("lastName", TestConstants.EMPLOYEE_LAST_NAME_CHANGED);
		paramMap.add("title", Title.ANALYST.name().toUpperCase());
		paramMap.add("departmentId", "1");
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				paramMap, new HttpHeaders());
		// WHEN
		final ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_MOVED_TEMP, response.getStatusCodeValue());
		Assert.assertEquals(response.getHeaders().getLocation().getPath(), "/listEmployees");
		Assert.assertEquals(response.getHeaders().getLocation().getQuery(), "departmentId=1");

		// GIVEN
		final String requestUrlRedir = String.format("http://localhost:%s/listEmployees?departmentId=1", port);
		// WHEN
		final ResponseEntity<String> responseRedir = restTemplate.getForEntity(requestUrlRedir, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, responseRedir.getStatusCodeValue());
		final String responseBody = responseRedir.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("employees"));
		Assertions.assertThat(responseBody).contains(TestConstants.DEPARTMENT_NAME);
		// canceled employee is not found in the list
		Assertions.assertThat(responseBody).doesNotContain(TestConstants.EMPLOYEE_FIRST_NAME_CHANGED);
		Assertions.assertThat(responseBody).doesNotContain(TestConstants.EMPLOYEE_LAST_NAME_CHANGED);
		Assertions.assertThat(responseBody).contains(TestConstants.EMPLOYEE_FIRST_NAME);
		Assertions.assertThat(responseBody).contains(TestConstants.EMPLOYEE_LAST_NAME);
		Assertions.assertThat(responseBody).contains(CompanyService.getTitleList().get(0).getName());
		Assertions.assertThat(responseBody).contains(accessor.getMessage("addEmployee"));
	}

	/**
	 * Should start employee deleting.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldStartEmployeeDeleting() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/startEmployeeDeleting?departmentId=1&employeeId=1",
				port);
		// WHEN
		final ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, response.getStatusCodeValue());
		final String responseBody = response.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("deleteEmployee"));
		Assertions.assertThat(responseBody).contains(TestConstants.EMPLOYEE_FIRST_NAME);
		Assertions.assertThat(responseBody).contains(TestConstants.EMPLOYEE_LAST_NAME);
		Assertions.assertThat(responseBody).contains(CompanyService.getTitleList().get(0).getName());
		Assertions.assertThat(responseBody).contains(accessor.getMessage("delete"));
	}

	/**
	 * Should delete employee.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldDeleteEmployee() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/finishEmployeeDeleting", port);
		final MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("delete", "");
		paramMap.add("id", "1");
		paramMap.add("departmentId", "1");
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				paramMap, new HttpHeaders());
		// WHEN
		final ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_MOVED_TEMP, response.getStatusCodeValue());
		Assert.assertEquals(response.getHeaders().getLocation().getPath(), "/listEmployees");
		Assert.assertEquals(response.getHeaders().getLocation().getQuery(), "departmentId=1");

		// GIVEN
		final String requestUrlRedir = String.format("http://localhost:%s/listEmployees?departmentId=1", port);
		// WHEN
		final ResponseEntity<String> responseRedir = restTemplate.getForEntity(requestUrlRedir, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, responseRedir.getStatusCodeValue());
		final String responseBody = responseRedir.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("employees"));
		Assertions.assertThat(responseBody).contains(TestConstants.DEPARTMENT_NAME);
		// deleted employee is not found in the list
		Assertions.assertThat(responseBody).doesNotContain(TestConstants.EMPLOYEE_FIRST_NAME);
		Assertions.assertThat(responseBody).doesNotContain(TestConstants.EMPLOYEE_LAST_NAME);
		Assertions.assertThat(responseBody).contains(accessor.getMessage("addEmployee"));
	}

	/**
	 * Should cancel employee deleting.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldCancelEmployeeDeleting() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/finishEmployeeDeleting", port);
		final MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("cancel", "");
		paramMap.add("id", "1");
		paramMap.add("departmentId", "1");
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				paramMap, new HttpHeaders());
		// WHEN
		final ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_MOVED_TEMP, response.getStatusCodeValue());
		Assert.assertEquals(response.getHeaders().getLocation().getPath(), "/listEmployees");
		Assert.assertEquals(response.getHeaders().getLocation().getQuery(), "departmentId=1");

		// GIVEN
		final String requestUrlRedir = String.format("http://localhost:%s/listEmployees?departmentId=1", port);
		// WHEN
		final ResponseEntity<String> responseRedir = restTemplate.getForEntity(requestUrlRedir, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, responseRedir.getStatusCodeValue());
		final String responseBody = responseRedir.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("employees"));
		Assertions.assertThat(responseBody).contains(TestConstants.DEPARTMENT_NAME);
		// there is not deleted employee in the list
		Assertions.assertThat(responseBody).contains(TestConstants.EMPLOYEE_FIRST_NAME);
		Assertions.assertThat(responseBody).contains(TestConstants.EMPLOYEE_LAST_NAME);
		Assertions.assertThat(responseBody).contains(CompanyService.getTitleList().get(0).getName());
		Assertions.assertThat(responseBody).contains(accessor.getMessage("addEmployee"));
	}
}
