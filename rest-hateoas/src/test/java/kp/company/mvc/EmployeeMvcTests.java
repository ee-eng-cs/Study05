package kp.company.mvc;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import kp.company.TestConstants;
import kp.company.repository.EmployeeRepository;

/**
 * Application tests for employee with server-side support.<br>
 * The server is <b>not started</b>.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeMvcTests extends MvcTestsBase {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * Run before the tests.
	 * 
	 */
	@Before
	public void deleteAllBeforeTests() {
		employeeRepository.deleteAll();
	}

	/**
	 * Should return the repository index.<br>
	 * Tests a GET request.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldReturnRepositoryIndex() throws Exception {

		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.get("/");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$._links.employees").exists());
	}

	/**
	 * Should create the employee.<br>
	 * Tests a POST request.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldCreateEmployee() throws Exception {

		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.post("/employees").content(TestConstants.EMPLOYEE_CREATE_CONTENT);
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
		resultActions
				.andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("employees/")));
	}

	/**
	 * Should retrieve the employee.<br>
	 * Tests a GET request.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldRetrieveEmployee() throws Exception {

		// GIVEN
		final String location = createEmployee();
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.get(location);
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(TestConstants.EMPLOYEE_FIRST_NAME));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(TestConstants.EMPLOYEE_LAST_NAME));
	}

	/**
	 * Should query the employee.<br>
	 * Tests a GET request.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldQueryEmployee() throws Exception {

		// GIVEN
		createEmployee();
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.get("/employees/search/findByLastName?lastName={lastName}", TestConstants.EMPLOYEE_LAST_NAME);
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.employees[0].firstName")
				.value(TestConstants.EMPLOYEE_FIRST_NAME));
	}

	/**
	 * Should update the employee.<br>
	 * Tests a PUT request.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldUpdateEmployee() throws Exception {

		// GIVEN
		final String location = createEmployee();
		final MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders/*-*/
				.put(location).content(TestConstants.EMPLOYEE_CHANGE_CONTENT);
		// WHEN
		final ResultActions resultActions1 = mockMvc.perform(requestBuilder1);
		// THEN
		resultActions1.andExpect(MockMvcResultMatchers.status().isNoContent());

		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder2 = MockMvcRequestBuilders/*-*/
				.get(location);
		// WHEN
		final ResultActions resultActions2 = mockMvc.perform(requestBuilder2);
		// WHEN
		resultActions2.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions2.andExpect(
				MockMvcResultMatchers.jsonPath("$.firstName").value(TestConstants.EMPLOYEE_FIRST_NAME_CHANGED));
		resultActions2.andExpect(
				MockMvcResultMatchers.jsonPath("$.lastName").value(TestConstants.EMPLOYEE_LAST_NAME_CHANGED));
	}

	/**
	 * Should partially update the employee.<br>
	 * Tests a PATCH request.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldPartiallyUpdateEntity() throws Exception {

		// GIVEN
		final String location = createEmployee();
		final MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders/*-*/
				.patch(location).content(TestConstants.EMPLOYEE_PARTIAL_CHANGE_CONTENT);
		// WHEN
		final ResultActions resultActions1 = mockMvc.perform(requestBuilder1);
		// THEN
		resultActions1.andExpect(MockMvcResultMatchers.status().isNoContent());

		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder2 = MockMvcRequestBuilders/*-*/
				.get(location);
		// WHEN
		final ResultActions resultActions2 = mockMvc.perform(requestBuilder2);
		// THEN
		resultActions2.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions2.andExpect(
				MockMvcResultMatchers.jsonPath("$.firstName").value(TestConstants.EMPLOYEE_FIRST_NAME_CHANGED));
		resultActions2.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(TestConstants.EMPLOYEE_LAST_NAME));
	}

	/**
	 * Should delete the employee.<br>
	 * Tests a DELETE request.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldDeleteEntity() throws Exception {

		// GIVEN
		final String location = createEmployee();
		final MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders/*-*/
				.delete(location);
		// WHEN
		final ResultActions resultActions1 = mockMvc.perform(requestBuilder1);
		// THEN
		resultActions1.andExpect(MockMvcResultMatchers.status().isNoContent());

		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder2 = MockMvcRequestBuilders/*-*/
				.get(location);
		// WHEN
		final ResultActions resultActions2 = mockMvc.perform(requestBuilder2);
		// THEN
		resultActions2.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	/**
	 * Creates employee.
	 *
	 * @return the location
	 * @throws Exception the exception
	 */
	private String createEmployee() throws Exception {

		final MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders/*-*/
				.post("/employees").content(TestConstants.EMPLOYEE_CREATE_CONTENT);

		final ResultActions resultActions = mockMvc.perform(requestBuilder1);

		resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
		final MvcResult mvcResult = resultActions.andReturn();
		return mvcResult.getResponse().getHeader("Location");
	}
}