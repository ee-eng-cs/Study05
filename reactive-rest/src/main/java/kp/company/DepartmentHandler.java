package kp.company;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/*-
Publishers:
 - Flux (it represents a stream of asynchronous values)
 - Mono (it represents a single asynchronous value)
 
The Flux type is a publisher of a sequence of events.
The Mono type represents a single valued or empty Flux.
 */
/**
 * The WebFlux handler.
 *
 */
@Component
public class DepartmentHandler {
	private static final Log logger = LogFactory.getLog(DepartmentHandler.class);

	private static final String DEPARTMENT_NAME = "D-e-p-a-r-t-m-e-n-t-01";
	private static final boolean VERBOSE = true;

	/**
	 * Handles the department.
	 * 
	 * @param request the request
	 * @return the Mono object that holds a ServerResponse body.
	 */
	public Mono<ServerResponse> handleDepartment(ServerRequest request) {

		Mono<ServerResponse> mono = ServerResponse/*-*/
				.ok()/*-*/
				.contentType(MediaType.TEXT_PLAIN)/*-*/
				.body(BodyInserters.fromObject(DEPARTMENT_NAME));
		if (VERBOSE) {
			mono = mono.log();
		}
		logger.info(String.format("handleDepartment(): department[%s]", DEPARTMENT_NAME));
		return mono;
	}
}