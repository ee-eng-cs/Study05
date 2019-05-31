package kp.company.service;

import java.util.Collection;
import java.util.List;

import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.domain.Title;
import kp.company.exception.CompanyException;

/**
 * The company service.
 *
 */
public interface CompanyService {

	/**
	 * Gets the list of titles.
	 * 
	 * @return the unmodifiable list of titles
	 */
	static List<Title> getTitleList() {
		return List.of(Title.ANALYST, Title.DEVELOPER, Title.MANAGER);
	}

	/**
	 * Reloads company service data.
	 * 
	 */
	void reloadData();

	/**
	 * Gets the collection of departments.
	 * 
	 * @return the collection of departments
	 */
	Collection<Department> getDepartmentCollection();

	/**
	 * Finds the department.
	 * 
	 * @param departmentId the department id
	 * @return the department
	 * @throws CompanyException the exception
	 */
	Department findDepartment(long departmentId) throws CompanyException;

	/**
	 * Creates the department.
	 * 
	 * @return the department
	 */
	Department createDepartment();

	/**
	 * Saves the department.
	 * 
	 * @param department the department
	 */
	void saveDepartment(Department department);

	/**
	 * Deletes the department.
	 * 
	 * @param departmentId the department id
	 * @throws CompanyException the exception
	 */
	void deleteDepartment(long departmentId) throws CompanyException;

	/**
	 * Finds the employee.
	 * 
	 * @param departmentId the department id
	 * @param employeeId   the employee id
	 * @return the employee
	 * @throws CompanyException the exception
	 */
	Employee findEmployee(long departmentId, long employeeId) throws CompanyException;

	/**
	 * Creates the employee.
	 * 
	 * @param departmentId the department id
	 * @return the new employee
	 * @throws CompanyException the exception
	 */
	Employee createEmployee(long departmentId) throws CompanyException;

	/**
	 * Saves the employee.
	 * 
	 * @param employee the employee
	 * @throws CompanyException the exception
	 */
	void saveEmployee(Employee employee) throws CompanyException;

	/**
	 * Deletes the employee.
	 * 
	 * @param departmentId the department id
	 * @param employeeId   the employee id
	 * @throws CompanyException the exception
	 */
	void deleteEmployee(long departmentId, long employeeId) throws CompanyException;
}