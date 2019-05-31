package kp.company;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/*-
The 'WebFlux.fn' is a lightweight functional programming model.
It is an alternative to the annotation-based programming model.
It separates the routing configuration from the actual handling of the requests.
 */
/**
 * The router.
 *
 */
@Configuration
public class DepartmentRouter {
	private static final Log logger = LogFactory.getLog(DepartmentRouter.class);

	/**
	 * Routes to a handler function.
	 * 
	 * @param departmentHandler the handler
	 * @return the router function
	 */
	@Bean
	public RouterFunction<ServerResponse> route(DepartmentHandler departmentHandler) {

		final String REQUEST_PATH_PATTERN = "/company/department";
		final RequestPredicate requestPredicate = RequestPredicates.GET(REQUEST_PATH_PATTERN)
				.and(RequestPredicates.accept(MediaType.TEXT_PLAIN));
		final RouterFunction<ServerResponse> routerFunction = RouterFunctions.route(requestPredicate,
				departmentHandler::handleDepartment);
		logger.info(String.format("route(): REQUEST_PATH_PATTERN[%s]", REQUEST_PATH_PATTERN));
		return routerFunction;
	}
}