package kp.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import reactor.core.publisher.Mono;

/**
 * The non-blocking web client.
 *
 */
public class WebClientLauncher {
	private static final Log logger = LogFactory.getLog(WebClientLauncher.class);

	private static final String BASE_URL = "http://localhost:8080";
	private static final String DEPARTMENT_URI = "/company/department";

	/**
	 * Launches web client.
	 * 
	 */
	public static void launch() {

		final WebClient client = WebClient.create(BASE_URL);
		final RequestHeadersSpec<?> requestHeadersSpec = client.get().uri(DEPARTMENT_URI)/*-*/
				.accept(MediaType.TEXT_PLAIN);
		final Mono<ClientResponse> mono = requestHeadersSpec.exchange();

		final String result = mono/*-*/
				.flatMap(res -> res.bodyToMono(String.class))/*-*/
				.block();
		logger.info(String.format("launch(): result[%s]", result));
	}
}
