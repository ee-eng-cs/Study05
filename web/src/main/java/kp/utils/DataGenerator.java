package kp.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.domain.Title;
import kp.company.service.CompanyService;

/**
 * The data generator for departments and employees.<br>
 * <ol>
 * <li>Department
 * <ol>
 * <li>Employee
 * <li>Employee
 * <li>Employee
 * </ol>
 * <li>Department
 * <ol>
 * <li>Employee
 * <li>Employee
 * <li>Employee
 * </ol>
 * </ol>
 */
public class DataGenerator {

	private static Map<Long, Department> departmentMap;

	private static AtomicLong departmentIdSequence;

	private static AtomicLong employeeIdSequence;

	/**
	 * Creates data for departments with employees.
	 * 
	 * @param departmentMapParam        the department map
	 * @param departmentIdSequenceParam the department id sequence
	 * @param employeeIdSequenceParam   the employee id sequence
	 */
	public static void generateDepartments(Map<Long, Department> departmentMapParam,
			AtomicLong departmentIdSequenceParam, AtomicLong employeeIdSequenceParam) {

		departmentMap = departmentMapParam;
		departmentIdSequence = departmentIdSequenceParam;
		employeeIdSequence = employeeIdSequenceParam;

		departmentMap.clear();
		departmentIdSequence.set(1);
		employeeIdSequence.set(1);
		for (int i = 1; i <= 2; i++) {
			generateDepartment();
		}
	}

	/**
	 * Generates department.
	 * 
	 */
	private static void generateDepartment() {

		final long departmentId = departmentIdSequence.getAndIncrement();
		final Department department = new Department();
		department.setId(departmentId);
		department.setName(String.format("N-a-m-e-%02d", departmentId));
		for (int i = 1; i <= 3; i++) {
			generateEmployee(department);
		}
		departmentMap.put(departmentId, department);
	}

	/**
	 * Generates employee.
	 * 
	 * @param department the department
	 */
	private static void generateEmployee(Department department) {

		final long employeeId = employeeIdSequence.getAndIncrement();
		final Employee employee = new Employee();
		employee.setId(employeeId);
		employee.setFirstName(String.format("F-N-a-m-e-%02d", employeeId));
		employee.setLastName(String.format("L-N-a-m-e-%02d", employeeId));
		final List<Title> titleList = CompanyService.getTitleList();
		final int index = (Long.valueOf(employeeId).intValue() - 1) % 3;
		employee.setTitle(titleList.get(index));
		employee.setDepartmentId(department.getId());
		department.putEmployee(employee);
	}
}