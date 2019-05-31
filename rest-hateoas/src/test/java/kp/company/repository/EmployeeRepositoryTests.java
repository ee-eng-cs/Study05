package kp.company.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import kp.company.TestConstants;
import kp.company.domain.Department;
import kp.company.domain.Employee;

/**
 * The employee repository tests.
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTests extends RepositoryTestsBase {

	/**
	 * Should create and find the employee.
	 * 
	 */
	@Test
	public void shouldCreateAndFindEmployee() {

		// GIVEN
		Employee employee = new Employee();
		employee.setFirstName(TestConstants.EMPLOYEE_FIRST_NAME_CHANGED);
		employee.setLastName(TestConstants.EMPLOYEE_LAST_NAME_CHANGED);
		// WHEN
		employee = employeeRepository.save(employee);
		final long id = employee.getId();

		final Optional<Employee> employeeOptional = employeeRepository.findById(id);
		// THEN
		assertThat(employeeOptional.isPresent());
		assertThat(employeeOptional.get().getFirstName()).isEqualTo(TestConstants.EMPLOYEE_FIRST_NAME_CHANGED);
		assertThat(employeeOptional.get().getLastName()).isEqualTo(TestConstants.EMPLOYEE_LAST_NAME_CHANGED);
	}

	/**
	 * Should find employees by last name.
	 * 
	 */
	@Test
	public void shouldFindEmployeesByLastName() {

		// GIVEN
		// WHEN
		final List<Employee> employeeList = employeeRepository.findByLastName(TestConstants.EMPLOYEE_LAST_NAME);
		// THEN
		assertThat(employeeList.size()).isEqualTo(1);
		final Employee employee = employeeList.get(0);
		assertThat(employee.getFirstName()).isEqualTo(TestConstants.EMPLOYEE_FIRST_NAME);
		assertThat(employee.getLastName()).isEqualTo(TestConstants.EMPLOYEE_LAST_NAME);
	}

	/**
	 * Should find employees by first name and last name.
	 */
	@Test
	public void shouldFindEmployeesByFirstNameAndLastName() {

		// GIVEN
		// WHEN
		final List<Employee> employeeList = employeeRepository
				.findByFirstNameAndLastName(TestConstants.EMPLOYEE_FIRST_NAME, TestConstants.EMPLOYEE_LAST_NAME);
		// THEN
		assertThat(employeeList.size()).isEqualTo(1);
		final Employee employee = employeeList.get(0);
		assertThat(employee.getFirstName()).isEqualTo(TestConstants.EMPLOYEE_FIRST_NAME);
		assertThat(employee.getLastName()).isEqualTo(TestConstants.EMPLOYEE_LAST_NAME);
	}

	/**
	 * Should find employees by first name and last name on first slice.
	 * 
	 */
	@Test
	public void shouldFindEmployeesByFirstNameAndLastNameOnFirstSlice() {

		// GIVEN
		// WHEN
		final Slice<Employee> employeeSlice = employeeRepository.findDistinctByFirstNameAndLastNameOrderByLastNameAsc(
				TestConstants.EMPLOYEE_FIRST_NAME, TestConstants.EMPLOYEE_LAST_NAME, PageRequest.of(0, 20));
		// THEN
		assertThat(employeeSlice.hasContent());
		final Employee employee = employeeSlice.getContent().get(0);
		assertThat(employee.getFirstName()).isEqualTo(TestConstants.EMPLOYEE_FIRST_NAME);
		assertThat(employee.getLastName()).isEqualTo(TestConstants.EMPLOYEE_LAST_NAME);
	}

	/**
	 * Should find top 3 employees by last name.
	 */
	@Test
	public void shouldFindTop3EmployeesByLastName() {

		// GIVEN
		// WHEN
		List<Employee> employeeList = employeeRepository.findDistinctTop3ByOrderByLastNameAsc();
		// THEN
		assertThat(employeeList.size()).isEqualTo(3);
		final Employee employee = employeeList.get(0);
		assertThat(employee.getFirstName()).isEqualTo(TestConstants.EMPLOYEE_FIRST_NAME);
		assertThat(employee.getLastName()).isEqualTo(TestConstants.EMPLOYEE_LAST_NAME);
	}

	/**
	 * 
	 */
	@Test
	public void shouldFindEmployeesByCustomQuery() {

		// GIVEN
		// WHEN
		final Stream<Employee> stream = employeeRepository.findAllByCustomQueryAndStream(TestConstants.NAME_END,
				TestConstants.NAME_END, Sort.by("lastName", "firstName"));
		final List<Employee> employeeList;
		try (stream) {
			employeeList = stream.collect(Collectors.toList());
		}
		// THEN
		assertThat(employeeList.get(0).getFirstName()).isEqualTo(TestConstants.EMPLOYEE_FIRST_NAME);
		assertThat(employeeList.get(0).getLastName()).isEqualTo(TestConstants.EMPLOYEE_LAST_NAME);
	}

	/**
	 * Should find one employee using query by example.
	 * 
	 */
	@Test
	public void shouldFindOneEmployeeByExample() {

		// GIVEN
		final Employee employeeProbe = new Employee();
		employeeProbe.setFirstName(TestConstants.NAME_END);
		employeeProbe.setLastName(TestConstants.NAME_END);
		final ExampleMatcher exampleMatcher = ExampleMatcher.matching()/*-*/
				.withIgnorePaths("id")/*-*/
				.withMatcher("firstName", match -> match.endsWith())/*-*/
				.withMatcher("lastName", match -> match.endsWith());
		final Example<Employee> employeeExample = Example.of(employeeProbe, exampleMatcher);
		// WHEN
		final Optional<Employee> employeeOptional = employeeRepository.findOne(employeeExample);
		// THEN
		assertThat(employeeOptional.isPresent());
		final Employee employee = employeeOptional.get();
		assertThat(employee.getFirstName()).isEqualTo(TestConstants.EMPLOYEE_FIRST_NAME);
		assertThat(employee.getLastName()).isEqualTo(TestConstants.EMPLOYEE_LAST_NAME);
	}

	/**
	 * Should find the employees of given department.
	 * 
	 */
	@Test
	public void shouldFindEmployeesByDepartment() {

		// GIVEN
		// WHEN
		final List<Employee> employeeList = employeeRepository
				.findDistinctByDepartmentName(TestConstants.DEPARTMENT_NAME, Sort.by("lastName", "firstName"));
		// THEN
		assertThat(employeeList.size()).isEqualTo(3);
		final Employee employee = employeeList.get(0);
		assertThat(employee.getFirstName()).isEqualTo(TestConstants.EMPLOYEE_FIRST_NAME);
		assertThat(employee.getLastName()).isEqualTo(TestConstants.EMPLOYEE_LAST_NAME);

		final Department department = employee.getDepartment();
		assertThat(department.getName()).isEqualTo(TestConstants.DEPARTMENT_NAME);
	}
}
