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
import kp.company.repository.DepartmentRepository;

/**
 * Application tests for department with server-side support.<br>
 * The server is <b>not started</b>.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentMvcTests extends MvcTestsBase {

	@Autowired
	private DepartmentRepository departmentRepository;

	/**
	 * Run before the tests.
	 * 
	 */
	@Before
	public void deleteAllBeforeTests() {
		departmentRepository.deleteAll();
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
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$._links.departments").exists());
	}

	/**
	 * Should create the department.<br>
	 * Tests a POST request.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldCreateDepartment() throws Exception {

		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.post("/departments").content(TestConstants.DEPARTMENT_CREATE_CONTENT);
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
		resultActions
				.andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("departments/")));
	}

	/**
	 * Should retrieve the department.<br>
	 * Tests a GET request.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldRetrieveDepartment() throws Exception {

		// GIVEN
		final String location = createDepartment();
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.get(location);
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(TestConstants.DEPARTMENT_NAME));
	}

	/**
	 * Should query the department.<br>
	 * Tests a GET request.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldQueryDepartment() throws Exception {

		// GIVEN
		createDepartment();
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.get("/departments/search/findByName?name={name}", TestConstants.DEPARTMENT_NAME);
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(
				MockMvcResultMatchers.jsonPath("$._embedded.departments[0].name").value(TestConstants.DEPARTMENT_NAME));
	}

	/**
	 * Should update the department.<br>
	 * Tests a PUT request.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldUpdateDepartment() throws Exception {

		// GIVEN
		final String location = createDepartment();
		final MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders/*-*/
				.put(location).content(TestConstants.DEPARTMENT_CHANGE_CONTENT);
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
		resultActions2.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(TestConstants.DEPARTMENT_NAME_CHANGED));
	}

	/**
	 * Should partially update the department.<br>
	 * Tests a PATCH request.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldPartiallyUpdateEntity() throws Exception {

		// GIVEN
		final String location = createDepartment();
		final MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders/*-*/
				.patch(location).content(TestConstants.DEPARTMENT_CHANGE_CONTENT);
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
		resultActions2.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(TestConstants.DEPARTMENT_NAME_CHANGED));
	}

	/**
	 * Should delete the department.<br>
	 * Tests a DELETE request.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldDeleteEntity() throws Exception {

		// GIVEN
		final String location = createDepartment();
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
	 * Creates department.
	 *
	 * @return the location
	 * @throws Exception the exception
	 */
	private String createDepartment() throws Exception {

		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.post("/departments").content(TestConstants.DEPARTMENT_CREATE_CONTENT);

		final ResultActions resultActions = mockMvc.perform(requestBuilder);

		resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
		final MvcResult mvcResult = resultActions.andReturn();
		return mvcResult.getResponse().getHeader("Location");
	}
}