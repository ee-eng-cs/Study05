package kp.company;

/**
 * Test constants.
 *
 */
public interface TestConstants {
	String DEPARTMENT_NAME = "N-a-m-e-01";
	String DEPARTMENT_NAME_CHANGED = "N-a-m-e-CHANGED";
	String DEPARTMENT_CREATE_CONTENT = "{\"name\": \"N-a-m-e-01\"}";
	String DEPARTMENT_CHANGE_CONTENT = "{\"name\": \"N-a-m-e-CHANGED\"}";

	String EMPLOYEE_FIRST_NAME = "F-N-a-m-e-01";
	String EMPLOYEE_LAST_NAME = "L-N-a-m-e-01";
	String EMPLOYEE_FIRST_NAME_CHANGED = "F-N-a-m-e-CHANGED";
	String EMPLOYEE_LAST_NAME_CHANGED = "L-N-a-m-e-CHANGED";
	String EMPLOYEE_CREATE_CONTENT = "{\"firstName\": \"F-N-a-m-e-01\", \"lastName\":\"L-N-a-m-e-01\"}";
	String EMPLOYEE_CHANGE_CONTENT = "{\"firstName\": \"F-N-a-m-e-CHANGED\", \"lastName\":\"L-N-a-m-e-CHANGED\"}";
	String EMPLOYEE_PARTIAL_CHANGE_CONTENT = "{\"firstName\": \"F-N-a-m-e-CHANGED\"}";

	String NAME_END = "01";
}