package kp.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.repository.DepartmentRepository;
import kp.company.repository.EmployeeRepository;

/**
 * Utilities.
 *
 */
public class Utils {

	/**
	 * Generates data for departments with employees and saves it in the
	 * database.<br>
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
	 * 
	 * @param departmentRepository the department repository
	 * @param employeeRepository   the employee repository
	 */
	public static void generateDepartmentsWithEmployees(DepartmentRepository departmentRepository,
			EmployeeRepository employeeRepository) {

		final List<Department> departmentList = IntStream.rangeClosed(1, 2).boxed().map(i -> {
			final Department department = new Department();
			department.setName(String.format("N-a-m-e-%02d", i));
			return department;
		}).collect(Collectors.toList());
		final List<Department> departmentListSaved = departmentRepository.saveAll(departmentList);

		final List<Employee> employeeList = IntStream.rangeClosed(1, 6).boxed().map(i -> {
			final Employee employee = new Employee();
			employee.setFirstName(String.format("F-N-a-m-e-%02d", i));
			employee.setLastName(String.format("L-N-a-m-e-%02d", i));
			return employee;
		}).collect(Collectors.toList());
		final List<Employee> employeeListSaved = employeeRepository.saveAll(employeeList);

		for (int i = 0; i < 2; i++) {
			final Department department = departmentListSaved.get(i);
			for (int j = 0; j < 3; j++) {
				final Employee employee = employeeListSaved.get(3 * i + j);
				employee.setDepartment(department);
				department.getEmployees().add(employee);
			}
		}
	}
}
