package kp.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import kp.company.domain.Department;

/**
 * The department repository.
 *
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	/**
	 * Finds the department by name.
	 * 
	 * @param name the department name
	 * @return the list of departments
	 */
	List<Department> findByName(@Param("name") String name);

	/**
	 * Counts departments by name.
	 * 
	 * @param name the department name
	 * @return the departments count
	 */
	long countByName(@Param("name") String name);

	/**
	 * Deletes the department with the given name.
	 * 
	 * @param name the department name
	 * @return the count of deleted departments
	 */
	long deleteByName(@Param("name") String name);
}