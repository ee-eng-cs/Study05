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

/**
 * Client side tests for department.<br>
 * The server is <b>started</b>.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DepartmentClientSideTests extends ClientSideTestsBase {

	/**
	 * Should list departments.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldListDepartments() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/listDepartments", port);
		// WHEN
		final ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, response.getStatusCodeValue());
		final String responseBody = response.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("departments"));
		// there is given department in the list
		Assertions.assertThat(responseBody).contains(TestConstants.DEPARTMENT_NAME);
		Assertions.assertThat(responseBody).contains(accessor.getMessage("addDepartment"));
	}

	/**
	 * Should start department adding.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldStartDepartmentAdding() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/startDepartmentAdding", port);
		// WHEN
		final ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, response.getStatusCodeValue());
		final String responseBody = response.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("addDepartment"));
		Assertions.assertThat(responseBody).contains(accessor.getMessage("name"));
		Assertions.assertThat(responseBody).contains(accessor.getMessage("save"));
	}

	/**
	 * Should start department editing.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldStartDepartmentEditing() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/startDepartmentEditing?departmentId=1", port);
		// WHEN
		final ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, response.getStatusCodeValue());
		final String responseBody = response.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("editDepartment"));
		Assertions.assertThat(responseBody).contains(TestConstants.DEPARTMENT_NAME);
		Assertions.assertThat(responseBody).contains(accessor.getMessage("save"));
	}

	/**
	 * Should not start editing non-existent department.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldNotStartDepartmentEditing() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/startDepartmentEditing?departmentId=999", port);
		// WHEN
		final ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_NOT_FOUND, response.getStatusCodeValue());
		final String responseBody = response.getBody();
		Assertions.assertThat(responseBody).contains("\"message\":\"Department not found\"");
		Assertions.assertThat(responseBody).contains("\"path\":\"/startDepartmentEditing\"");
	}

	/**
	 * Should save department.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldSaveDepartment() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/finishDepartmentEditing", port);
		final MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("save", "");
		paramMap.add("id", "1");
		paramMap.add("name", TestConstants.DEPARTMENT_NAME_CHANGED);
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				paramMap, new HttpHeaders());
		// WHEN
		final ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
		// THEN
		/*- The HTTP response status code 302 'Temporary Redirect' is a common way of performing URL redirection. */
		Assert.assertEquals(HttpURLConnection.HTTP_MOVED_TEMP, response.getStatusCodeValue());
		Assert.assertEquals(response.getHeaders().getLocation().getPath(), "/listDepartments");

		// GIVEN
		final String requestUrlRedir = String.format("http://localhost:%s/listDepartments", port);
		// WHEN
		final ResponseEntity<String> responseRedir = restTemplate.getForEntity(requestUrlRedir, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, responseRedir.getStatusCodeValue());
		final String responseBody = responseRedir.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("departments"));
		// there is given saved department in the list
		Assertions.assertThat(responseBody).contains(TestConstants.DEPARTMENT_NAME_CHANGED);
		Assertions.assertThat(responseBody).contains(accessor.getMessage("addDepartment"));
	}

	/**
	 * Should validate department and show validation error.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldValidateDepartmentAndShowValidationError() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/finishDepartmentEditing", port);
		final MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("save", "");
		paramMap.add("id", "1");
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				paramMap, new HttpHeaders());
		// WHEN
		final ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, response.getStatusCodeValue());
		final String responseBody = response.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("editDepartment"));
		// validation error
		Assertions.assertThat(responseBody).contains("must not be blank");
		Assertions.assertThat(responseBody).contains(accessor.getMessage("save"));
	}

	/**
	 * Should cancel department editing.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldCancelDepartmentEditing() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/finishDepartmentEditing", port);
		final MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("cancel", "");
		paramMap.add("id", "1");
		paramMap.add("name", TestConstants.DEPARTMENT_NAME_CHANGED);
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				paramMap, new HttpHeaders());
		// WHEN
		final ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_MOVED_TEMP, response.getStatusCodeValue());
		Assert.assertEquals(response.getHeaders().getLocation().getPath(), "/listDepartments");

		// GIVEN
		final String requestUrlRedir = String.format("http://localhost:%s/listDepartments", port);
		// WHEN
		final ResponseEntity<String> responseRedir = restTemplate.getForEntity(requestUrlRedir, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, responseRedir.getStatusCodeValue());
		final String responseBody = responseRedir.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("departments"));
		// canceled department is not found in the list
		Assertions.assertThat(responseBody).doesNotContain(TestConstants.DEPARTMENT_NAME_CHANGED);
		Assertions.assertThat(responseBody).contains(TestConstants.DEPARTMENT_NAME);
		Assertions.assertThat(responseBody).contains(accessor.getMessage("addDepartment"));
	}

	/**
	 * Should start department deleting.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldStartDepartmentDeleting() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/startDepartmentDeleting?departmentId=1", port);
		// WHEN
		final ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, response.getStatusCodeValue());
		final String responseBody = response.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("deleteDepartment"));
		Assertions.assertThat(responseBody).contains(TestConstants.DEPARTMENT_NAME);
		Assertions.assertThat(responseBody).contains(accessor.getMessage("delete"));
	}

	/**
	 * Should delete department.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldDeleteDepartment() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/finishDepartmentDeleting", port);
		final MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("delete", "");
		paramMap.add("id", "1");
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				paramMap, new HttpHeaders());
		// WHEN
		final ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_MOVED_TEMP, response.getStatusCodeValue());
		Assert.assertEquals(response.getHeaders().getLocation().getPath(), "/listDepartments");

		// GIVEN
		final String requestUrlRedir = String.format("http://localhost:%s/listDepartments", port);
		// WHEN
		final ResponseEntity<String> responseRedir = restTemplate.getForEntity(requestUrlRedir, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, responseRedir.getStatusCodeValue());
		final String responseBody = responseRedir.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("departments"));
		// deleted department is not found in the list
		Assertions.assertThat(responseBody).doesNotContain(TestConstants.DEPARTMENT_NAME);
		Assertions.assertThat(responseBody).contains(accessor.getMessage("addDepartment"));
	}

	/**
	 * Should cancel department deleting.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldCancelDepartmentDeleting() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/finishDepartmentDeleting", port);
		final MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("cancel", "");
		paramMap.add("id", "1");
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				paramMap, new HttpHeaders());
		// WHEN
		final ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_MOVED_TEMP, response.getStatusCodeValue());
		Assert.assertEquals(response.getHeaders().getLocation().getPath(), "/listDepartments");

		// GIVEN
		final String requestUrlRedir = String.format("http://localhost:%s/listDepartments", port);
		// WHEN
		final ResponseEntity<String> responseRedir = restTemplate.getForEntity(requestUrlRedir, String.class);
		// THEN
		Assert.assertEquals(HttpURLConnection.HTTP_OK, responseRedir.getStatusCodeValue());
		final String responseBody = responseRedir.getBody();
		Assertions.assertThat(responseBody).contains(accessor.getMessage("departments"));
		// there is not deleted department in the list
		Assertions.assertThat(responseBody).contains(TestConstants.DEPARTMENT_NAME);
		Assertions.assertThat(responseBody).contains(accessor.getMessage("addDepartment"));
	}
}
