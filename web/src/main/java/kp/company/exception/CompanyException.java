package kp.company.exception;

/**
 * Customized exception.
 *
 */
public class CompanyException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public CompanyException() {
		super();
	}

	/**
	 * Constructor with message parameter.
	 * 
	 * @param message the message
	 */
	public CompanyException(String message) {
		super(message);
	}
}
