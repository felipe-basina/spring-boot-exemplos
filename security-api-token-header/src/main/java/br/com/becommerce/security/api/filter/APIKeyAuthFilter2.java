package br.com.becommerce.security.api.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import br.com.becommerce.security.api.model.TokenCredential;

public class APIKeyAuthFilter2 extends AbstractPreAuthenticatedProcessingFilter {

	private String principalRequestHeader;
	
	private String principalRequestValue;

	public APIKeyAuthFilter2(String principalRequestHeader, String principalRequestValue) {
		this.principalRequestHeader = principalRequestHeader;
		this.principalRequestValue = principalRequestValue;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return new TokenCredential(request.getHeader(this.principalRequestHeader), 
				request.getHeader(this.principalRequestValue));
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "N/A";
	}

}
