package kp.company;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import kp.company.client.side.CompanyClientSideTests;
import kp.company.client.side.DepartmentClientSideTests;
import kp.company.client.side.EmployeeClientSideTests;
import kp.company.mvc.CompanyMVCTests;
import kp.company.mvc.DepartmentMVCTests;
import kp.company.mvc.EmployeeMVCTests;

/**
 * The test suite for the web application.
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ /*-*/
		CompanyClientSideTests.class, DepartmentClientSideTests.class, EmployeeClientSideTests.class, /*-*/
		CompanyMVCTests.class, DepartmentMVCTests.class, EmployeeMVCTests.class })
public class TestSuite {
}