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

import kp.company.domain.Employee;
import kp.company.repository.EmployeeRepository;

/**
 * The HATEOAS RESTful web service controller for the employee.
 *
 */
@RestController
public class EmployeeController {

	private static final Log logger = LogFactory.getLog(EmployeeController.class);

	private final EmployeeRepository repository;

	private final EmployeeResourceAssembler resourceAssembler;

	/**
	 * Constructor.
	 * 
	 * @param repository        the repository
	 * @param resourceAssembler the resource assembler
	 */
	EmployeeController(EmployeeRepository repository, EmployeeResourceAssembler resourceAssembler) {
		super();
		this.repository = repository;
		this.resourceAssembler = resourceAssembler;
	}

	/**
	 * Creates a new employee.
	 * 
	 * @param employee the employee
	 * @return the response entity with the employee
	 * @throws URISyntaxException the URI syntax exception
	 */
	@PostMapping("/company/employees")
	ResponseEntity<?> saveEmployee(@RequestBody Employee employee) throws URISyntaxException {

		employee = repository.save(employee);
		final Resource<Employee> resource = resourceAssembler.toResource(employee);
		final URI uri = new URI(resource.getId().expand().getHref());
		final ResponseEntity<?> responseEntity = ResponseEntity.created(uri).body(resource);
		logger.info(String.format("saveEmployee(): employee id[%d]", employee.getId()));
		return responseEntity;
	}

	/**
	 * Reads a single employee with given id.
	 * 
	 * @param id the employee id
	 * @return the resource with the employee
	 */
	@GetMapping("/company/employees/{id}")
	Resource<Employee> findEmployeeById(@PathVariable Long id) {

		final RuntimeException exception = new RuntimeException(String.format("Employee with id[%d] not found", id));
		final Employee employee = repository.findById(id).orElseThrow(() -> exception);
		final Resource<Employee> resource = resourceAssembler.toResource(employee);
		logger.info(String.format("findEmployeeById(): employee id[%d]", employee.getId()));
		return resource;
	}

	/**
	 * Reads all employees.
	 * 
	 * @return the list of resources with employee
	 */
	@GetMapping("/company/employees")
	Resources<Resource<Employee>> findAllEmployees() {

		final List<Resource<Employee>> employeeList = repository.findAll().stream().map(resourceAssembler::toResource)
				.collect(Collectors.toList());
		final Resources<Resource<Employee>> resourcesList = new Resources<>(employeeList,
				linkTo(methodOn(EmployeeController.class).findAllEmployees()).withSelfRel());
		logger.info(String.format("findAllEmployees(): employee list size[%d]", employeeList.size()));
		return resourcesList;
	}

	/**
	 * 
	 * Replaces the employee with given id.
	 * 
	 * @param sourceEmployee the source employee
	 * @param id             the employee id
	 * @return the response entity with employee
	 * @throws URISyntaxException the URI syntax exception
	 */
	@PutMapping("/company/employees/{id}")
	ResponseEntity<?> replaceEmployee(@RequestBody Employee sourceEmployee, @PathVariable Long id)
			throws URISyntaxException {

		final Employee targetEmployee = repository.findById(id).map(dep -> {
			dep.setFirstName(sourceEmployee.getFirstName());
			dep.setLastName(sourceEmployee.getLastName());
			return repository.save(dep);
		}).orElseGet(() -> {
			sourceEmployee.setId(id);
			return repository.save(sourceEmployee);
		});
		final Resource<Employee> resource = resourceAssembler.toResource(targetEmployee);
		final URI uri = new URI(resource.getId().expand().getHref());
		final ResponseEntity<?> responseEntity = ResponseEntity.created(uri).body(resource);
		logger.info(String.format("replaceEmployee(): employee id[%d]", targetEmployee.getId()));
		return responseEntity;
	}

	/**
	 * Deletes the employee with given id.
	 * 
	 * @param id the employee id
	 * @return the response entity
	 */
	@DeleteMapping("/company/employees/{id}")
	ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

		repository.deleteById(id);
		final ResponseEntity<?> responseEntity = ResponseEntity.noContent().build();
		logger.info(String.format("deleteEmployee(): employee id[%d]", id));
		return responseEntity;
	}
}
