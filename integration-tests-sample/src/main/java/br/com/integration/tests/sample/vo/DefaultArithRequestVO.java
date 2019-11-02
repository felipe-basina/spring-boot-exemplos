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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DefaultArithRequestVO [numbers=").append(numbers).append(", arithOperations=")
				.append(arithOperations).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arithOperations == null) ? 0 : arithOperations.hashCode());
		result = prime * result + ((numbers == null) ? 0 : numbers.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultArithRequestVO other = (DefaultArithRequestVO) obj;
		if (arithOperations != other.arithOperations)
			return false;
		if (numbers == null) {
			if (other.numbers != null)
				return false;
		} else if (!numbers.equals(other.numbers))
			return false;
		return true;
	}

}
