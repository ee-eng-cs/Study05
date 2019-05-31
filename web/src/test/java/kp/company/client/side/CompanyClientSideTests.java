package kp.company.client.side;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Client side tests for company.<br>
 * The server is <b>started</b>.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CompanyClientSideTests extends ClientSideTestsBase {

	/**
	 * Should forward from root to index page.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldForwardFromRootToIndexPage() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/", port);
		// WHEN
		final String result = restTemplate.getForObject(requestUrl, String.class);
		// THEN
		Assertions.assertThat(result).contains("<meta http-equiv=\"Refresh\" content=\"0; URL=/company\">");
	}

	/**
	 * Should get company.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void shouldGetHomePage() throws Exception {
		// GIVEN
		final String requestUrl = String.format("http://localhost:%s/company", port);
		// WHEN
		final String result = restTemplate.getForObject(requestUrl, String.class);
		// THEN
		Assertions.assertThat(result).contains(accessor.getMessage("company"));
		Assertions.assertThat(result).contains(accessor.getMessage("departments"));
	}
}