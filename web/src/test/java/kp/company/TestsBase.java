package kp.company;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

import kp.company.service.CompanyServiceImpl;

/**
 * The base class for tests.
 *
 */
public abstract class TestsBase {

	@SpyBean
	private CompanyServiceImpl companyService;

	@Autowired
	private MessageSource messageSource;

	protected MessageSourceAccessor accessor;

	@Before
	public void init() {
	}

	/**
	 * Initializes the company service data before every test.
	 * 
	 */
	@Before
	public void initialize() {
		MockitoAnnotations.initMocks(this);
		companyService.reloadData();
		accessor = new MessageSourceAccessor(messageSource);
	}
}
