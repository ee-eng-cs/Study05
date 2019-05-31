package kp.company.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kp.company.domain.Employee;

/**
 * The employee repository.
 *
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	/**
	 * Finds employee by last name.
	 * 
	 * @param lastName the employee last name
	 * @return the list of employees
	 */
	List<Employee> findByLastName(@Param("lastName") String lastName);

	/**
	 * Finds distinct top 3 employees.<br>
	 * The query is derived from the method name.<br>
	 * Ordering by last name with an ascending order.
	 * 
	 * @return the list of employees
	 */
	List<Employee> findDistinctTop3ByOrderByLastNameAsc();

	/**
	 * Finds employees by first name and last name.
	 * 
	 * @param firstName the employee first name
	 * @param lastName  the employee last name
	 * @return the list of employees
	 */
	List<Employee> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

	/**
	 * Finds distinct employees by first name and last name.<br>
	 * The query is derived from the method name.<br>
	 * Ordering by last name with an ascending order.
	 * 
	 * @param firstName the employee first name
	 * @param lastName  the employee last name
	 * @param pageable  the pagination information
	 * @return the list of employees
	 */
	Slice<Employee> findDistinctByFirstNameAndLastNameOrderByLastNameAsc(@Param("firstName") String firstName,
			@Param("lastName") String lastName, Pageable pageable);

	/**
	 * Finds all employees by custom query and stream the result.
	 * 
	 * @param firstName the employee first name
	 * @param lastName  the employee last name
	 * @param sort      the sort option
	 * @return the result stream
	 */
	@Query("select distinct e from Employee e "/*-*/
			+ "where e.firstName like %:firstName and e.lastName like %:lastName")
	Stream<Employee> findAllByCustomQueryAndStream(@Param("firstName") String firstName,
			@Param("lastName") String lastName, Sort sort);

	/**
	 * Finds distinct employees by department name.
	 * 
	 * @param name the department name
	 * @param sort the sort option
	 * @return the list of employees
	 */
	@Query("select distinct e from Employee e where e.department.name = :name")
	List<Employee> findDistinctByDepartmentName(@Param("name") String name, Sort sort);
}
