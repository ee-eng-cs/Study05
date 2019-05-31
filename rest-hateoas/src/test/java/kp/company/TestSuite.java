package kp.company;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import kp.company.client.side.DepartmentClientSideTests;
import kp.company.client.side.EmployeeClientSideTests;
import kp.company.mvc.DepartmentMvcTests;
import kp.company.mvc.EmployeeMvcTests;
import kp.company.repository.DepartmentRepositoryTests;
import kp.company.repository.EmployeeRepositoryTests;

/**
 * The test suite for the departments and the employees.
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ DepartmentClientSideTests.class, EmployeeClientSideTests.class, /*-*/
		DepartmentMvcTests.class, EmployeeMvcTests.class, /*-*/
		DepartmentRepositoryTests.class, EmployeeRepositoryTests.class })
public class TestSuite {
}