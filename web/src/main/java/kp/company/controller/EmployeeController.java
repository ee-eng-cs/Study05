package kp.company.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.domain.Title;
import kp.company.exception.CompanyException;
import kp.company.service.CompanyService;

/**
 * Employee web controller.
 * 
 */
@Controller
public class EmployeeController {
	private static final Log logger = LogFactory.getLog(EmployeeController.class);

	private final CompanyService companyService;

	/**
	 * Constructor.
	 * 
	 * @param companyService the company service
	 */
	public EmployeeController(CompanyService companyService) {
		super();
		this.companyService = companyService;
	}

	/**
	 * Gets the list of titles.
	 * 
	 * @return the list of titles
	 */
	@ModelAttribute("titles")
	public List<Title> listTitles() {

		return CompanyService.getTitleList();
	}

	/**
	 * Gets the list of employees.
	 * 
	 * @param departmentId the department id
	 * @param model        the model
	 * @return the view name
	 */
	@GetMapping("/listEmployees")
	public String listEmployees(long departmentId, Model model) {

		Department department = null;
		try {
			department = companyService.findDepartment(departmentId);
		} catch (CompanyException ex) {
			logger.error(String.format("listEmployees(): CompanyException[%s], department id[%d]", ex.getMessage(),
					departmentId));
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", ex);
		}
		model.addAttribute("department", department);
		logger.info(String.format("listEmployees(): department id[%d]", department.getId()));
		return "employees/list";
	}

	/**
	 * Start employee adding.
	 * 
	 * @param departmentId the department id
	 * @param model        the model
	 * @return the view name
	 */
	@GetMapping("/startEmployeeAdding")
	public String startEmployeeAdding(long departmentId, Model model) {

		Employee employee = null;
		try {
			employee = companyService.createEmployee(departmentId);
		} catch (CompanyException ex) {
			logger.error(String.format("startEmployeeAdding(): CompanyException[%s], department id[%d]",
					ex.getMessage(), departmentId));
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", ex);
		}
		model.addAttribute("employee", employee);
		logger.info(String.format("startEmployeeAdding(): department id[%d]", departmentId));
		return "employees/edit";
	}

	/**
	 * Start employee editing.
	 * 
	 * @param departmentId the department id
	 * @param employeeId   the employee id
	 * @param model        the model
	 * @return the view name
	 */
	@GetMapping("/startEmployeeEditing")
	public String startEmployeeEditing(long departmentId, long employeeId, Model model) {

		Employee employee = null;
		try {
			employee = companyService.findEmployee(departmentId, employeeId);
		} catch (CompanyException ex) {
			logger.error(String.format("startEmployeeEditing(): CompanyException[%s], department id[%d]",
					ex.getMessage(), departmentId));
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department or employee not found", ex);
		}
		model.addAttribute("employee", employee);
		logger.info(
				String.format("startEmployeeEditing(): department id[%d], employee id[%d]", departmentId, employeeId));
		return "employees/edit";
	}

	/**
	 * Save employee.
	 * 
	 * @param employee      the employee
	 * @param bindingResult the binding result
	 * @return the view name
	 */
	@PostMapping(value = "/finishEmployeeEditing", params = { "save" })
	public String saveEmployee(@Valid Employee employee, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			final List<String> messagesList = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			logger.info(String.format("saveEmployee(): error messages%s", messagesList));
			return "employees/edit";
		}
		try {
			companyService.saveEmployee(employee);
		} catch (CompanyException ex) {
			logger.error(String.format("saveEmployee(): CompanyException[%s]", ex.getMessage()));
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", ex);
		}
		logger.info(String.format("saveEmployee(): department id[%d], employee id[%d], title name[%s]",
				employee.getDepartmentId(), employee.getId(), employee.getTitle().getName()));
		return String.format("redirect:/listEmployees?departmentId=%s", employee.getDepartmentId());
	}

	/**
	 * Cancel employee editing.
	 * 
	 * @param departmentId the department id
	 * @param employeeId   the employee id
	 * @return the view name
	 */
	@PostMapping(value = "/finishEmployeeEditing", params = { "cancel" })
	public String cancelEmployeeEditing(long departmentId, @RequestParam("id") long employeeId) {

		logger.info(
				String.format("cancelEmployeeEditing(): department id[%d], employee id[%d]", departmentId, employeeId));
		return String.format("redirect:/listEmployees?departmentId=%s", departmentId);
	}

	/**
	 * Start employee deleting.
	 * 
	 * @param departmentId the department id
	 * @param employeeId   the employee id
	 * @param model        the model
	 * @return the view name
	 */
	@GetMapping("/startEmployeeDeleting")
	public String startEmployeeDeleting(long departmentId, long employeeId, Model model) {

		Employee employee = null;
		try {
			employee = companyService.findEmployee(departmentId, employeeId);
		} catch (CompanyException ex) {
			logger.error(String.format("startEmployeeDeleting(): CompanyException[%s], department id[%d]",
					ex.getMessage(), departmentId));
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department or employee not found", ex);
		}
		model.addAttribute("employee", employee);
		logger.info(
				String.format("startEmployeeDeleting(): department id[%d], employee id[%d]", departmentId, employeeId));
		return "employees/confirmDelete";
	}

	/**
	 * Delete employee.
	 * 
	 * @param departmentId the department id
	 * @param employeeId   the employee id
	 * @return the view name
	 */
	@PostMapping(value = "/finishEmployeeDeleting", params = { "delete" })
	public String deleteEmployee(long departmentId, @RequestParam("id") long employeeId) {

		try {
			companyService.deleteEmployee(departmentId, employeeId);
		} catch (CompanyException ex) {
			logger.error(String.format("deleteEmployee(): CompanyException[%s], department id[%d]", ex.getMessage(),
					departmentId));
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department or employee not found", ex);
		}
		logger.info(String.format("deleteEmployee(): department id[%d], employee id[%d]", departmentId, employeeId));
		return String.format("redirect:/listEmployees?departmentId=%s", departmentId);
	}

	/**
	 * Cancel employee deleting.
	 * 
	 * @param departmentId the department id
	 * @param employeeId   the employee id
	 * @return the view name
	 */
	@PostMapping(value = "/finishEmployeeDeleting", params = { "cancel" })
	public String cancelEmployeeDeleting(long departmentId, @RequestParam("id") long employeeId) {

		logger.info(String.format("cancelEmployeeDeleting(): department id[%d], employee id[%d]", departmentId,
				employeeId));
		return String.format("redirect:/listEmployees?departmentId=%s", departmentId);
	}
}
