package kp.company.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Company web controller.
 * 
 */
@Controller
public class CompanyController {
	private static final Log logger = LogFactory.getLog(CompanyController.class);

	/**
	 * Gets company.
	 * 
	 * @return the view name
	 */
	@GetMapping("/company")
	public String company() {

		logger.info("company():");
		return "company/home";
	}
}