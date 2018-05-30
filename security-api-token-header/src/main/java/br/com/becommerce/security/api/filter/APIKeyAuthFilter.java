package br.com.becommerce.security.api.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(APIKeyAuthFilter.class);
	
	private String principalRequestHeader;

	public APIKeyAuthFilter(String principalRequestHeader) {
		LOGGER.info("Principal Request HEADER {}", this.principalRequestHeader);
		this.principalRequestHeader = principalRequestHeader;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return request.getHeader(principalRequestHeader);
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "N/A";
	}

}
