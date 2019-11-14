package br.com.integration.tests.sample.component;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.integration.tests.sample.services.ClientArithService;
import br.com.integration.tests.sample.types.ArithOperations;
import br.com.integration.tests.sample.vo.ArithParams;
import br.com.integration.tests.sample.vo.ArithResponseVO;
import br.com.integration.tests.sample.vo.DefaultArithRequestVO;
import br.com.integration.tests.sample.vo.PowerArithRequestVO;

@Component
public class ArithComponent {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ClientArithService clientArithService;

	@Autowired
	public ArithComponent(ClientArithService clientArithService) {
		super();
		this.clientArithService = clientArithService;
	}
	
	public ArithResponseVO<?> sum(List<BigDecimal> numbers) {
		ArithParams defaultArithParams = this.createDefaultArithRequestVO(numbers, ArithOperations.SUM);
		logger.info("Calling sum for {}", defaultArithParams);
		return this.clientArithService.doArithOperation(defaultArithParams);
	}
	
	public ArithResponseVO<?> subtract(List<BigDecimal> numbers) {
		ArithParams defaultArithParams = this.createDefaultArithRequestVO(numbers, ArithOperations.SUBTRACT);
		logger.info("Calling subtract for {}", defaultArithParams);
		return this.clientArithService.doArithOperation(defaultArithParams);
	}
	
	public ArithResponseVO<?> multiply(List<BigDecimal> numbers) {
		ArithParams defaultArithParams = this.createDefaultArithRequestVO(numbers, ArithOperations.MULTIPLY);
		logger.info("Calling multiply for {}", defaultArithParams);
		return this.clientArithService.doArithOperation(defaultArithParams);
	}
	
	public ArithResponseVO<?> divide(List<BigDecimal> numbers) {
		ArithParams defaultArithParams = this.createDefaultArithRequestVO(numbers, ArithOperations.DIVISION);
		logger.info("Calling divide for {}", defaultArithParams);
		return this.clientArithService.doArithOperation(defaultArithParams);
	}
	
	public ArithResponseVO<?> power(BigDecimal number, int power) {
		ArithParams powerArithParams = new PowerArithRequestVO(number, power);
		logger.info("Calling power for {}", powerArithParams);
		return this.clientArithService.doArithOperation(powerArithParams);
	}
	
	private ArithParams createDefaultArithRequestVO(List<BigDecimal> numbers, ArithOperations arithOperations) {
		return new DefaultArithRequestVO(numbers, arithOperations);
	}
	
}
