package br.com.integration.tests.sample.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import br.com.integration.tests.sample.exceptions.LocalClientResponseException;

@Component
@Qualifier(value = LocalResponseErrorHandler.QUALIFIER_VALUE)
public class LocalResponseErrorHandler implements ResponseErrorHandler {
	
	public static final String QUALIFIER_VALUE = "localResponseErrorHandler";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return response.getStatusCode().is4xxClientError() 
				|| response.getStatusCode().is5xxServerError();
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		logger.error("StatusCode from remote http resource: {}", response.getStatusCode());
		logger.error("RawStatusCode from remote http resource: {}", response.getRawStatusCode());
		logger.error("StatusText from remote http resource: {}", response.getStatusText());

		String body = new BufferedReader(new InputStreamReader(response.getBody())).lines()
				.collect(Collectors.joining("\n"));

		logger.error("Error body from remote http resource: {}", body);
		throw new IOException(new LocalClientResponseException(response.getStatusCode().value(), body));
	}

}
