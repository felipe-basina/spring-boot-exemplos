package br.com.integration.tests.sample.services;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.com.integration.tests.sample.exceptions.LocalClientResponseException;
import br.com.integration.tests.sample.handlers.LocalResponseErrorHandler;
import br.com.integration.tests.sample.types.ArithOperations;
import br.com.integration.tests.sample.vo.ArithParams;
import br.com.integration.tests.sample.vo.ArithResponseVO;
import br.com.integration.tests.sample.vo.ResultVO;

@Service
public class ClientArithService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private RestTemplate restTemplate;
	
	private ResponseErrorHandler responseErrorHandler;

	@Value(value = "${external.service.baseurl}")
	private String baseUrl;

	private Gson gson;
	
	private Type resultVOType = new TypeToken<ResultVO>() {}.getType();

	@Autowired
	public ClientArithService(
			@Qualifier(value = LocalResponseErrorHandler.QUALIFIER_VALUE) ResponseErrorHandler responseErrorHandler) {
		this.responseErrorHandler = responseErrorHandler;
		this.restTemplate = new RestTemplate();
		this.restTemplate.setErrorHandler(this.responseErrorHandler);
		this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}

	public ArithResponseVO<?> doArithOperation(ArithParams arithParams) {
		try {
			final String url = this.getFullUrl(arithParams.arithOperation());
			HttpEntity<String> entity = this.createHttpEntity(url, arithParams);
			
			ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(url, entity, String.class);
			ResultVO resultVO = this.convertResponse(responseEntity.getBody());
			
			return new ArithResponseVO<>(responseEntity.getStatusCode().value(),
					new BigDecimal(resultVO.getResult()));
		} catch (Exception e) {
			LocalClientResponseException localClientResponseException = (LocalClientResponseException) e.getCause().getCause();
			ResultVO resultVO = this.convertResponse(localClientResponseException.getErrorMessage());
			return new ArithResponseVO<>(localClientResponseException.getStatusCode(), resultVO.getResult());
		}
	}
	
	private HttpEntity<String> createHttpEntity(String url, ArithParams arithParams) {
		final String jsonBody = this.createJsonBody(arithParams);
		logger.info("Calling {} with {}", url, jsonBody);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		return new HttpEntity<>(jsonBody, headers);
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

}
