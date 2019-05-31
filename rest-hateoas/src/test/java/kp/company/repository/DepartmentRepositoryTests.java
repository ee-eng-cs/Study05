package kp.company.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import kp.company.TestConstants;
import kp.company.domain.Department;
import kp.company.domain.Employee;

/**
 * The department repository tests.
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DepartmentRepositoryTests extends RepositoryTestsBase {

	/**
	 * Should create and find the department.
	 * 
	 */
	@Test
	public void shouldCreateAndFindDepartment() {

		// GIVEN
		Department department = new Department();
		department.setName(TestConstants.DEPARTMENT_NAME_CHANGED);
		// WHEN
		department = departmentRepository.save(department);
		final long id = department.getId();

		final Optional<Department> departmentOptional = departmentRepository.findById(id);
		// THEN
		assertThat(departmentOptional.get().getName()).isEqualTo(TestConstants.DEPARTMENT_NAME_CHANGED);
	}

	/**
	 * Should find all departments.
	 * 
	 */
	@Test
	public void shouldFindAllDepartments() {

		// GIVEN
		// WHEN
		final List<Department> departmentList = departmentRepository.findAll();
		// THEN
		assertThat(departmentList.size()).isEqualTo(2);
	}

	/**
	 * Should find all departments on first page.
	 * 
	 */
	@Test
	public void shouldFindAllDepartmentsOnFirstPage() {

		// GIVEN
		// WHEN
		final Page<Department> page = departmentRepository.findAll(PageRequest.of(0, 20));
		// THEN
		assertThat(page.getContent().size()).isEqualTo(2);
	}

	/**
	 * Should find one department using query by example.
	 * 
	 */
	@Test
	public void shouldFindOneDepartmentByExample() {

		// GIVEN
		final Department departmentProbe = new Department();
		departmentProbe.setName(TestConstants.DEPARTMENT_NAME);
		final Example<Department> departmentExample = Example.of(departmentProbe, ExampleMatcher.matchingAny());
		// WHEN
		final Optional<Department> departmentOptional = departmentRepository.findOne(departmentExample);
		// THEN
		assertThat(departmentOptional.isPresent());
		final Department department = departmentOptional.get();
		assertThat(department.getName()).isEqualTo(TestConstants.DEPARTMENT_NAME);
	}

	/**
	 * Should find departments with its employees.
	 * 
	 */
	@Test
	public void shouldFindDepartmentsWithEmployees() {

		// GIVEN
		// WHEN
		final List<Department> departmentList = departmentRepository.findByName(TestConstants.DEPARTMENT_NAME);
		// THEN
		assertThat(departmentList.size()).isEqualTo(1);
		final Department department = departmentList.get(0);
		assertThat(department.getName()).isEqualTo(TestConstants.DEPARTMENT_NAME);

		final List<Employee> employeeList = department.getEmployees();
		assertThat(employeeList.size()).isEqualTo(3);
		final Employee employee = employeeList.get(0);
		assertThat(employee.getFirstName()).isEqualTo(TestConstants.EMPLOYEE_FIRST_NAME);
		assertThat(employee.getLastName()).isEqualTo(TestConstants.EMPLOYEE_LAST_NAME);
	}

	/**
	 * Should count departments by given name.
	 * 
	 */
	@Test
	public void shouldCountDepartmentsByName() {

		// GIVEN
		// WHEN
		long count = departmentRepository.countByName(TestConstants.DEPARTMENT_NAME);
		// THEN
		assertThat(count).isEqualTo(1);
	}

	/**
	 * Should delete departments by given name.
	 * 
	 */
	@Test
	public void shouldDeleteDepartmentsByName() {

		// GIVEN
		// WHEN
		long count = departmentRepository.deleteByName(TestConstants.DEPARTMENT_NAME);
		// THEN
		assertThat(count).isEqualTo(1);
		final List<Department> departmentList = departmentRepository.findByName(TestConstants.DEPARTMENT_NAME);
		assertThat(departmentList.isEmpty());
	}
}
