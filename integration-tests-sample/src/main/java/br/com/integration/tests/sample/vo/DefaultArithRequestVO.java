package br.com.integration.tests.sample.vo;

import java.math.BigDecimal;
import java.util.List;

import com.google.gson.annotations.Expose;

import br.com.integration.tests.sample.types.ArithOperations;

public class DefaultArithRequestVO implements DefaultArithParams {

	@Expose
	private List<BigDecimal> numbers;
	
	private ArithOperations arithOperations;
	
	@Override
	public ArithOperations arithOperation() {
		return this.arithOperations;
	}

	@Override
	public List<BigDecimal> values() {
		return this.numbers;
	}

	public DefaultArithRequestVO(List<BigDecimal> numbers, ArithOperations arithOperations) {
		super();
		this.numbers = numbers;
		this.arithOperations = arithOperations;
	}

}
