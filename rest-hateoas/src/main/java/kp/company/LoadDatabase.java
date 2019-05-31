package kp.company;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kp.company.repository.DepartmentRepository;
import kp.company.repository.EmployeeRepository;
import kp.utils.Utils;

/**
 * Loads database.
 *
 */
@Configuration
public class LoadDatabase {
	private static final Log logger = LogFactory.getLog(LoadDatabase.class);

	/**
	 * Initializes the database.
	 * 
	 * @param departmentRepository the department repository
	 * @param employeeRepository   the employee repository
	 * @return the command line runner
	 */
	@Bean
	CommandLineRunner initDatabase(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
		logger.info("initDatabase():");
		return arg -> Utils.generateDepartmentsWithEmployees(departmentRepository, employeeRepository);

	}

}
