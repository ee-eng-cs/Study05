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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import kp.company.domain.Department;
import kp.company.exception.CompanyException;
import kp.company.service.CompanyService;

/**
 * Department web controller.
 * 
 */
@Controller
public class DepartmentController {
	private static final Log logger = LogFactory.getLog(DepartmentController.class);

	private final CompanyService companyService;

	/**
	 * Constructor.
	 * 
	 * @param companyService the company service
	 */
	public DepartmentController(CompanyService companyService) {
		super();
		this.companyService = companyService;
	}

	/**
	 * Gets the list of departments.
	 * 
	 * @param model the model
	 * @return the view name
	 */
	@GetMapping("/listDepartments")
	public String listDepartments(Model model) {

		model.addAttribute("departmentCollection", companyService.getDepartmentCollection());
		logger.info("listDepartments():");
		return "departments/list";
	}

	/**
	 * Start department adding.
	 * 
	 * @param model the model
	 * @return the view name
	 */
	@GetMapping("/startDepartmentAdding")
	public String startDepartmentAdding(Model model) {

		final Department department = companyService.createDepartment();
		model.addAttribute("department", department);
		logger.info("startDepartmentAdding():");
		return "departments/edit";
	}

	/**
	 * Start department editing.
	 * 
	 * @param departmentId the department id
	 * @param model        the model
	 * @return the view name
	 */
	@GetMapping("/startDepartmentEditing")
	public String startDepartmentEditing(long departmentId, Model model) {

		Department department = null;
		try {
			department = companyService.findDepartment(departmentId);
		} catch (CompanyException ex) {
			logger.error(String.format("startDepartmentEditing(): CompanyException[%s], department id[%d]",
					ex.getMessage(), departmentId));
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", ex);
		}
		model.addAttribute("department", department);
		logger.info(String.format("startDepartmentEditing(): department id[%d]", department.getId()));
		return "departments/edit";
	}

	/**
	 * Save department.
	 * 
	 * @param department    the department
	 * @param bindingResult the binding result
	 * @return the view name
	 */
	@PostMapping(value = "/finishDepartmentEditing", params = { "save" })
	public String saveDepartment(@Valid Department department, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			final List<String> messagesList = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			logger.info(String.format("saveDepartment(): error messages%s", messagesList));
			return "departments/edit";
		}
		companyService.saveDepartment(department);
		logger.info(String.format("saveDepartment(): department id[%d]", department.getId()));
		return "redirect:/listDepartments";
	}

	/**
	 * Cancel department editing.
	 * 
	 * @param departmentId the department id
	 * @return the view name
	 */
	@PostMapping(value = "/finishDepartmentEditing", params = { "cancel" })
	public String cancelDepartmentEditing(@RequestParam("id") long departmentId) {

		logger.info(String.format("cancelDepartmentEditing(): department id[%d]", departmentId));
		return "redirect:/listDepartments";
	}

	/**
	 * Start department deleting.
	 * 
	 * @param departmentId the department id
	 * @param model        the model
	 * @return the view name
	 */
	@GetMapping("/startDepartmentDeleting")
	public String startDepartmentDeleting(long departmentId, Model model) {

		Department department = null;
		try {
			department = companyService.findDepartment(departmentId);
		} catch (CompanyException ex) {
			logger.error(String.format("startDepartmentDeleting(): CompanyException[%s], department id[%d]",
					ex.getMessage(), departmentId));
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", ex);
		}
		model.addAttribute("department", department);
		logger.info(String.format("startDepartmentDeleting(): department id[%d]", departmentId));
		return "departments/confirmDelete";
	}

	/**
	 * Delete department.
	 * 
	 * @param departmentId the department id
	 * @return the view name
	 */
	@PostMapping(value = "/finishDepartmentDeleting", params = { "delete" })
	public String deleteDepartment(@RequestParam("id") long departmentId) {

		try {
			companyService.deleteDepartment(departmentId);
		} catch (CompanyException ex) {
			logger.error(String.format("deleteDepartment(): CompanyException[%s], department id[%d]", ex.getMessage(),
					departmentId));
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", ex);
		}
		logger.info(String.format("deleteDepartment(): department id[%d]", departmentId));
		return "redirect:/listDepartments";
	}

	/**
	 * Cancel department deleting.
	 * 
	 * @param departmentId the department id
	 * @return the view name
	 */
	@PostMapping(value = "/finishDepartmentDeleting", params = { "cancel" })
	public String cancelDepartmentDeleting(@RequestParam("id") long departmentId) {

		logger.info(String.format("cancelDepartmentDeleting(): department id[%d]", departmentId));
		return "redirect:/listDepartments";
	}
}
