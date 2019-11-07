package br.com.integration.tests.sample.services;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.com.integration.tests.sample.types.ArithOperations;
import br.com.integration.tests.sample.vo.ArithParams;
import br.com.integration.tests.sample.vo.ArithResponseVO;
import br.com.integration.tests.sample.vo.ResultVO;

@Service
public class ClientArithService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private RestTemplate restTemplate;

	@Value(value = "${external.service.baseurl}")
	private String baseUrl;

	private Gson gson;
	
	private Type resultVOType = new TypeToken<ResultVO>() {}.getType();

	public ClientArithService() {
		this.restTemplate = new RestTemplate();
		this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}

	public ArithResponseVO<?> doArithOperation(ArithParams arithParams) {
		try {
			final String url = this.getFullUrl(arithParams.arithOperation());
			HttpEntity<String> entity = this.createHttpEntity(url, arithParams);
			
			ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(url, entity, String.class);
			ResultVO resultVO = this.convertResponse(responseEntity.getBody());
			
			if (this.successResponse(responseEntity)) {
				return new ArithResponseVO<BigDecimal>(responseEntity.getStatusCode().value(),
						new BigDecimal(resultVO.getResult()));
			}
			return new ArithResponseVO<String>(responseEntity.getStatusCode().value(), resultVO.getResult());
		} catch (RestClientException e) {
			logger.error("Error calling external service: {}", e.getMessage(), e);
			return new ArithResponseVO<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}
	
	private HttpEntity<String> createHttpEntity(String url, ArithParams arithParams) {
		final String jsonBody = this.createJsonBody(arithParams);
		logger.info("Calling {} with {}", url, jsonBody);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return new HttpEntity<String>(jsonBody ,headers);
	}
	
	private ResultVO convertResponse(final String responseBody) {
		return this.gson.fromJson(responseBody, this.resultVOType);
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
