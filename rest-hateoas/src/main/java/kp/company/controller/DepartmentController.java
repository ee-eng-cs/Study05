package kp.company.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kp.company.domain.Department;
import kp.company.repository.DepartmentRepository;

/**
 * The HATEOAS RESTful web service controller for the department.
 *
 */
@RestController
public class DepartmentController {

	private static final Log logger = LogFactory.getLog(DepartmentController.class);

	private final DepartmentRepository repository;

	private final DepartmentResourceAssembler resourceAssembler;

	/**
	 * Constructor.
	 * 
	 * @param repository        the repository
	 * @param resourceAssembler the resource assembler
	 */
	DepartmentController(DepartmentRepository repository, DepartmentResourceAssembler resourceAssembler) {
		super();
		this.repository = repository;
		this.resourceAssembler = resourceAssembler;
	}

	/**
	 * Creates a new department.
	 * 
	 * @param department the department
	 * @return the response entity with the department
	 * @throws URISyntaxException the URI syntax exception
	 */
	@PostMapping("/company/departments")
	ResponseEntity<?> saveDepartment(@RequestBody Department department) throws URISyntaxException {

		department = repository.save(department);
		final Resource<Department> resource = resourceAssembler.toResource(department);
		final URI uri = new URI(resource.getId().expand().getHref());
		final ResponseEntity<?> responseEntity = ResponseEntity.created(uri).body(resource);
		logger.info(String.format("saveDepartment(): department id[%d]", department.getId()));
		return responseEntity;
	}

	/**
	 * Reads a single department with given id.
	 * 
	 * @param id the department id
	 * @return the resource with the department
	 */
	@GetMapping("/company/departments/{id}")
	Resource<Department> findDepartmentById(@PathVariable Long id) {

		final RuntimeException exception = new RuntimeException(String.format("Department with id[%d] not found", id));
		final Department department = repository.findById(id).orElseThrow(() -> exception);
		final Resource<Department> resource = resourceAssembler.toResource(department);
		logger.info(String.format("findDepartmentById(): department id[%d]", department.getId()));
		return resource;
	}

	/**
	 * Reads all departments.
	 * 
	 * @return the list of resources with department
	 */
	@GetMapping("/company/departments")
	Resources<Resource<Department>> findAllDepartments() {

		final List<Resource<Department>> departmentList = repository.findAll().stream()
				.map(resourceAssembler::toResource).collect(Collectors.toList());
		final Resources<Resource<Department>> resourcesList = new Resources<>(departmentList,
				linkTo(methodOn(DepartmentController.class).findAllDepartments()).withSelfRel());
		logger.info(String.format("findAllDepartments(): department list size[%d]", departmentList.size()));
		return resourcesList;
	}

	/**
	 * 
	 * Replaces the department with given id.
	 * 
	 * @param sourceDepartment the source department
	 * @param id               the department id
	 * @return the response entity with department
	 * @throws URISyntaxException the URI syntax exception
	 */
	@PutMapping("/company/departments/{id}")
	ResponseEntity<?> replaceDepartment(@RequestBody Department sourceDepartment, @PathVariable Long id)
			throws URISyntaxException {

		final Department targetDepartment = repository.findById(id).map(dep -> {
			dep.setName(sourceDepartment.getName());
			return repository.save(dep);
		}).orElseGet(() -> {
			sourceDepartment.setId(id);
			return repository.save(sourceDepartment);
		});
		final Resource<Department> resource = resourceAssembler.toResource(targetDepartment);
		final URI uri = new URI(resource.getId().expand().getHref());
		final ResponseEntity<?> responseEntity = ResponseEntity.created(uri).body(resource);
		logger.info(String.format("replaceDepartment(): department id[%d]", targetDepartment.getId()));
		return responseEntity;
	}

	/**
	 * Deletes the department with given id.
	 * 
	 * @param id the department id
	 * @return the response entity
	 */
	@DeleteMapping("/company/departments/{id}")
	ResponseEntity<?> deleteDepartment(@PathVariable Long id) {

		repository.deleteById(id);
		final ResponseEntity<?> responseEntity = ResponseEntity.noContent().build();
		logger.info(String.format("deleteDepartment(): department id[%d]", id));
		return responseEntity;
	}
}
