package kp.company.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import kp.company.domain.Employee;

/**
 * 
 * The resource assembler for the employee.
 *
 */
@Component
class EmployeeResourceAssembler implements ResourceAssembler<Employee, Resource<Employee>> {

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Resource<Employee> toResource(Employee employee) {

		return new Resource<>(employee,
				linkTo(methodOn(EmployeeController.class).findEmployeeById(employee.getId())).withSelfRel(),
				linkTo(methodOn(EmployeeController.class).findAllEmployees()).withRel("employees"));
	}
}