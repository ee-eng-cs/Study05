package kp.company.mvc;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import kp.company.TestConstants;
import kp.company.controller.DepartmentController;

/**
 * Application tests for department with server-side support.<br>
 * The server is <b>not started</b>.
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(DepartmentController.class)
public class DepartmentMVCTests extends MVCTestsBase {

	/**
	 * Should list departments.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldListDepartments() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.get("/listDepartments");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("departments"))));
		// there is given department in the list
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.DEPARTMENT_NAME)));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("addDepartment"))));
	}

	/**
	 * Should start department adding.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldStartDepartmentAdding() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.get("/startDepartmentAdding");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("addDepartment"))));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("name"))));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("save"))));
	}

	/**
	 * Should start department editing.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldStartDepartmentEditing() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.get("/startDepartmentEditing")/*-*/
				.param("departmentId", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("editDepartment"))));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.DEPARTMENT_NAME)));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("save"))));
	}

	/**
	 * Should save department.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldSaveDepartment() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.post("/finishDepartmentEditing")/*-*/
				.param("save", "")/*-*/
				.param("id", "1")/*-*/
				.param("name", TestConstants.DEPARTMENT_NAME_CHANGED);
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		/*- The HTTP response status code 302 'Temporary Redirect' is a common way of performing URL redirection. */
		resultActions.andExpect(MockMvcResultMatchers.status().isFound());
		resultActions.andExpect(MockMvcResultMatchers.redirectedUrl("/listDepartments"));

		// GIVEN
		final MockHttpServletRequestBuilder requestBuilderRedir = MockMvcRequestBuilders/*-*/
				.get("/listDepartments");
		// WHEN
		final ResultActions resultActionsRedir = mockMvc.perform(requestBuilderRedir);
		// THEN
		resultActionsRedir.andExpect(MockMvcResultMatchers.status().isOk());
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("departments"))));
		// there is given department in the list
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.DEPARTMENT_NAME_CHANGED)));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("addDepartment"))));
	}

	/**
	 * Should validate department and show validation error.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldValidateDepartmentAndShowValidationError() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.post("/finishDepartmentEditing")/*-*/
				.param("save", "")/*-*/
				.param("id", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(MockMvcResultMatchers.model().attributeHasErrors("department"));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("editDepartment"))));
		// validation error
		resultActions.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("must not be blank")));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("save"))));
	}

	/**
	 * Should cancel department editing.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldCancelDepartmentEditing() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.post("/finishDepartmentEditing")/*-*/
				.param("cancel", "")/*-*/
				.param("id", "1")/*-*/
				.param("name", TestConstants.DEPARTMENT_NAME_CHANGED);
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isFound());
		resultActions.andExpect(MockMvcResultMatchers.redirectedUrl("/listDepartments"));

		// GIVEN
		final MockHttpServletRequestBuilder requestBuilderRedir = MockMvcRequestBuilders/*-*/
				.get("/listDepartments");
		// WHEN
		final ResultActions resultActionsRedir = mockMvc.perform(requestBuilderRedir);
		// THEN
		resultActionsRedir.andExpect(MockMvcResultMatchers.status().isOk());
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("departments"))));
		// canceled department is not found in the list
		resultActionsRedir.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.not(Matchers.containsString(TestConstants.DEPARTMENT_NAME_CHANGED))));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.DEPARTMENT_NAME)));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("addDepartment"))));
	}

	/**
	 * Should start department deleting.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldStartDepartmentDeleting() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.get("/startDepartmentDeleting")/*-*/
				.param("departmentId", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.containsString(accessor.getMessage("deleteDepartment"))));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.DEPARTMENT_NAME)));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("delete"))));
	}

	/**
	 * Should delete department.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldDeleteDepartment() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.post("/finishDepartmentDeleting")/*-*/
				.param("delete", "")/*-*/
				.param("id", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isFound());
		resultActions.andExpect(MockMvcResultMatchers.redirectedUrl("/listDepartments"));

		// GIVEN
		final MockHttpServletRequestBuilder requestBuilderRedir = MockMvcRequestBuilders/*-*/
				.get("/listDepartments");
		// WHEN
		final ResultActions resultActionsRedir = mockMvc.perform(requestBuilderRedir);
		// THEN
		resultActionsRedir.andExpect(MockMvcResultMatchers.status().isOk());
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("departments"))));
		// deleted department is not found in the list
		resultActionsRedir.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.not(Matchers.containsString(TestConstants.DEPARTMENT_NAME))));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("addDepartment"))));
	}

	/**
	 * Should cancel department deleting.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldCancelDepartmentDeleting() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.post("/finishDepartmentDeleting")/*-*/
				.param("cancel", "")/*-*/
				.param("id", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isFound());
		resultActions.andExpect(MockMvcResultMatchers.redirectedUrl("/listDepartments"));

		// GIVEN
		final MockHttpServletRequestBuilder requestBuilderRedir = MockMvcRequestBuilders/*-*/
				.get("/listDepartments");
		// WHEN
		final ResultActions resultActionsRedir = mockMvc.perform(requestBuilderRedir);
		// THEN
		resultActionsRedir.andExpect(MockMvcResultMatchers.status().isOk());
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("departments"))));
		// there is not deleted department in the list
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.DEPARTMENT_NAME)));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("addDepartment"))));
	}
}
