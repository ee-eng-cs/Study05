package kp.company.domain;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Simple JavaBean domain object representing a department.
 *
 */
public class Department {

	private long id;

	@NotBlank
	@Size(min = 1, max = 20)
	private String name;

	private final Map<Long, Employee> employeeMap = new TreeMap<>();

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets employee.
	 * 
	 * @param employeeId the employee id
	 * @return the employee
	 */
	public Employee getEmployee(long employeeId) {
		return employeeMap.get(employeeId);
	}

	/**
	 * Puts employee.
	 * 
	 * @param employee the employee to put
	 */
	public void putEmployee(Employee employee) {
		employeeMap.put(employee.getId(), employee);
	}

	/**
	 * Removes employee.
	 * 
	 * @param employeeId the employee id
	 */
	public void removeEmployee(long employeeId) {
		employeeMap.remove(employeeId);
	}

	/**
	 * Gets the collection of employees.
	 * 
	 * @return the collection of employees
	 */
	public Collection<Employee> getEmployeeCollection() {
		return employeeMap.values();
	}
}