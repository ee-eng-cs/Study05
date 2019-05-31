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
import kp.company.controller.EmployeeController;
import kp.company.domain.Title;
import kp.company.service.CompanyService;

/**
 * Application tests for employee with server-side support.<br>
 * The server is <b>not started</b>.
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeMVCTests extends MVCTestsBase {

	/**
	 * Should list employees.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldListEmployees() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.get("/listEmployees")/*-*/
				.param("departmentId", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("employees"))));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.DEPARTMENT_NAME)));
		// there is given employee in the list
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.EMPLOYEE_FIRST_NAME)));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.EMPLOYEE_LAST_NAME)));
		resultActions.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.containsString(CompanyService.getTitleList().get(0).getName())));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("addEmployee"))));
	}

	/**
	 * Should start employee adding.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldStartEmployeeAdding() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.get("/startEmployeeAdding")/*-*/
				.param("departmentId", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("addEmployee"))));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("firstName"))));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("lastName"))));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("title"))));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("save"))));
	}

	/**
	 * Should start employee editing.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldStartEmployeeEditing() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.get("/startEmployeeEditing")/*-*/
				.param("departmentId", "1")/*-*/
				.param("employeeId", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("editEmployee"))));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.EMPLOYEE_FIRST_NAME)));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.EMPLOYEE_LAST_NAME)));
		resultActions.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.containsString(CompanyService.getTitleList().get(0).getName())));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("save"))));
	}

	/**
	 * Should save employee.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldSaveEmployee() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.post("/finishEmployeeEditing")/*-*/
				.param("save", "")/*-*/
				.param("id", "1")/*-*/
				.param("firstName", TestConstants.EMPLOYEE_FIRST_NAME_CHANGED)/*-*/
				.param("lastName", TestConstants.EMPLOYEE_LAST_NAME_CHANGED)/*-*/
				.param("title", Title.ANALYST.name().toUpperCase())/*-*/
				.param("departmentId", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isFound());
		resultActions.andExpect(MockMvcResultMatchers.redirectedUrl("/listEmployees?departmentId=1"));
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilderRedir = MockMvcRequestBuilders/*-*/
				.get("/listEmployees")/*-*/
				.param("departmentId", "1");
		// WHEN
		final ResultActions resultActionsRedir = mockMvc.perform(requestBuilderRedir);
		// THEN
		resultActionsRedir.andExpect(MockMvcResultMatchers.status().isOk());
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("employees"))));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.DEPARTMENT_NAME)));
		// there is given employee in the list
		resultActionsRedir.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.containsString(TestConstants.EMPLOYEE_FIRST_NAME_CHANGED)));
		resultActionsRedir.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.containsString(TestConstants.EMPLOYEE_LAST_NAME_CHANGED)));
		resultActionsRedir.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.containsString(CompanyService.getTitleList().get(0).getName())));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("addEmployee"))));
	}

	/**
	 * Should validate employee and show validation error.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldValidateEmployeeAndShowValidationError() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.post("/finishEmployeeEditing")/*-*/
				.param("save", "")/*-*/
				.param("id", "1")/*-*/
				.param("title", Title.ANALYST.name().toUpperCase())/*-*/
				.param("departmentId", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(MockMvcResultMatchers.model().attributeHasErrors("employee"));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("editEmployee"))));
		// validation error
		resultActions.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("must not be blank")));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("save"))));
	}

	/**
	 * Should cancel employee editing.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldCancelEmployeeEditing() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.post("/finishEmployeeEditing")/*-*/
				.param("cancel", "")/*-*/
				.param("id", "1")/*-*/
				.param("firstName", TestConstants.EMPLOYEE_FIRST_NAME)/*-*/
				.param("lastName", TestConstants.EMPLOYEE_LAST_NAME)/*-*/
				.param("title", Title.ANALYST.name().toUpperCase())/*-*/
				.param("departmentId", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isFound());
		resultActions.andExpect(MockMvcResultMatchers.redirectedUrl("/listEmployees?departmentId=1"));

		// GIVEN
		final MockHttpServletRequestBuilder requestBuilderRedir = MockMvcRequestBuilders/*-*/
				.get("/listEmployees")/*-*/
				.param("departmentId", "1");
		// WHEN
		final ResultActions resultActionsRedir = mockMvc.perform(requestBuilderRedir);
		// THEN
		resultActionsRedir.andExpect(MockMvcResultMatchers.status().isOk());
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("employees"))));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.DEPARTMENT_NAME)));
		// canceled employee is not found in the list
		resultActionsRedir.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.not(Matchers.containsString(TestConstants.EMPLOYEE_FIRST_NAME_CHANGED))));
		resultActionsRedir.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.not(Matchers.containsString(TestConstants.EMPLOYEE_LAST_NAME_CHANGED))));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.EMPLOYEE_FIRST_NAME)));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.EMPLOYEE_LAST_NAME)));
		resultActionsRedir.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.containsString(CompanyService.getTitleList().get(0).getName())));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("addEmployee"))));
	}

	/**
	 * Should start employee deleting.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldStartEmployeeDeleting() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.get("/startEmployeeDeleting")/*-*/
				.param("departmentId", "1")/*-*/
				.param("employeeId", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("deleteEmployee"))));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.EMPLOYEE_FIRST_NAME)));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.EMPLOYEE_LAST_NAME)));
		resultActions.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.containsString(CompanyService.getTitleList().get(0).getName())));
		resultActions.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("delete"))));
	}

	/**
	 * Should delete employee.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldDeleteEmployee() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.post("/finishEmployeeDeleting")/*-*/
				.param("delete", "")/*-*/
				.param("id", "1")/*-*/
				.param("departmentId", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isFound());
		resultActions.andExpect(MockMvcResultMatchers.redirectedUrl("/listEmployees?departmentId=1"));

		// GIVEN
		final MockHttpServletRequestBuilder requestBuilderRedir = MockMvcRequestBuilders/*-*/
				.get("/listEmployees")/*-*/
				.param("departmentId", "1");
		// WHEN
		final ResultActions resultActionsRedir = mockMvc.perform(requestBuilderRedir);
		// THEN
		resultActionsRedir.andExpect(MockMvcResultMatchers.status().isOk());
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("employees"))));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.DEPARTMENT_NAME)));
		// deleted employee is not found in the list
		resultActionsRedir.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.not(Matchers.containsString(TestConstants.EMPLOYEE_FIRST_NAME))));
		resultActionsRedir.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.not(Matchers.containsString(TestConstants.EMPLOYEE_LAST_NAME))));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("addEmployee"))));
	}

	/**
	 * Should cancel employee deleting.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldCancelEmployeeDeleting() throws Exception {
		// GIVEN
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders/*-*/
				.post("/finishEmployeeDeleting")/*-*/
				.param("cancel", "")/*-*/
				.param("id", "1")/*-*/
				.param("departmentId", "1");
		// WHEN
		final ResultActions resultActions = mockMvc.perform(requestBuilder);
		// THEN
		resultActions.andExpect(MockMvcResultMatchers.status().isFound());
		resultActions.andExpect(MockMvcResultMatchers.redirectedUrl("/listEmployees?departmentId=1"));

		// GIVEN
		final MockHttpServletRequestBuilder requestBuilderRedir = MockMvcRequestBuilders/*-*/
				.get("/listEmployees")/*-*/
				.param("departmentId", "1");
		// WHEN
		final ResultActions resultActionsRedir = mockMvc.perform(requestBuilderRedir);
		// THEN
		resultActionsRedir.andExpect(MockMvcResultMatchers.status().isOk());
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("employees"))));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.DEPARTMENT_NAME)));
		// there is not deleted employee in the list
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.EMPLOYEE_FIRST_NAME)));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(TestConstants.EMPLOYEE_LAST_NAME)));
		resultActionsRedir.andExpect(MockMvcResultMatchers.content()
				.string(Matchers.containsString(CompanyService.getTitleList().get(0).getName())));
		resultActionsRedir.andExpect(
				MockMvcResultMatchers.content().string(Matchers.containsString(accessor.getMessage("addEmployee"))));
	}
}
