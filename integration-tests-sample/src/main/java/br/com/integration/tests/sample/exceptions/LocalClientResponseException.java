package br.com.integration.tests.sample.exceptions;

import java.io.IOException;

public class LocalClientResponseException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2111694531783189780L;
	
	private int statusCode;
	
	private String errorMessage;
	
	public LocalClientResponseException(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LocalClientResponseException [statusCode=").append(statusCode).append(", errorMessage=")
				.append(errorMessage).append("]");
		return builder.toString();
	}

}
