package kp.company.repository;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import kp.company.repository.DepartmentRepository;
import kp.company.repository.EmployeeRepository;
import kp.utils.Utils;

/**
 * The repository tests base.
 *
 */
public abstract class RepositoryTestsBase {

	@Autowired
	protected DepartmentRepository departmentRepository;

	@Autowired
	protected EmployeeRepository employeeRepository;

	/**
	 * Tests setup.
	 * 
	 */
	@Before
	public void setup() {
		Utils.generateDepartmentsWithEmployees(departmentRepository, employeeRepository);
	}
}