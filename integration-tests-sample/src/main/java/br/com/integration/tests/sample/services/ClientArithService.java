package br.com.integration.tests.sample.services;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.integration.tests.sample.types.ArithOperations;
import br.com.integration.tests.sample.vo.ArithParams;
import br.com.integration.tests.sample.vo.ArithResponseVO;

@Service
public class ClientArithService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private RestTemplate restTemplate;

	@Value(value = "${external.service.baseurl}")
	private String baseUrl;

	private Gson gson;

	public ClientArithService() {
		this.restTemplate = new RestTemplate();
		this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}

	public ArithResponseVO<?> doArithOperation(ArithParams arithParams) {
		final String url = this.getFullUrl(arithParams.arithOperation());
		final String jsonBody = this.createJsonBody(arithParams);
		logger.info("Calling {} with {}", url, jsonBody);

		try {
			ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(url, jsonBody, String.class);
			if (this.successResponse(responseEntity)) {
				return new ArithResponseVO<BigDecimal>(responseEntity.getStatusCode().value(),
						new BigDecimal(responseEntity.getBody()));
			}
			return new ArithResponseVO<String>(responseEntity.getStatusCode().value(), responseEntity.getBody());
		} catch (RestClientException e) {
			logger.error("Error calling external service: {}", e.getMessage(), e);
			return new ArithResponseVO<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	private String getFullUrl(ArithOperations arithOperations) {
		return this.baseUrl.concat(arithOperations.getRemoteOperation());
	}

	private String createJsonBody(ArithParams arithParams) {
		return this.gson.toJson(arithParams);
	}

	private boolean successResponse(ResponseEntity<String> responseEntity) {
		return responseEntity.getStatusCode().is2xxSuccessful();
	}

}
