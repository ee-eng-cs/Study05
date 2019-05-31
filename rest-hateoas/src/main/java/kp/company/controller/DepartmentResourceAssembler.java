package kp.company.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import kp.company.domain.Department;

/**
 * 
 * The resource assembler for the department.
 *
 */
@Component
class DepartmentResourceAssembler implements ResourceAssembler<Department, Resource<Department>> {

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Resource<Department> toResource(Department department) {

		return new Resource<>(department,
				linkTo(methodOn(DepartmentController.class).findDepartmentById(department.getId())).withSelfRel(),
				linkTo(methodOn(DepartmentController.class).findAllDepartments()).withRel("departments"));
	}
}