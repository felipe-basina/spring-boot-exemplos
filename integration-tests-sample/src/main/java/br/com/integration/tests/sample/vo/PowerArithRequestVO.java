package br.com.integration.tests.sample.vo;

import java.math.BigDecimal;

import com.google.gson.annotations.Expose;

import br.com.integration.tests.sample.types.ArithOperations;

public class PowerArithRequestVO implements PowerArithParams {

	@Expose
	private BigDecimal number;
	
	@Expose
	private int power;
	
	@Override
	public ArithOperations arithOperation() {
		return ArithOperations.POWER;
	}

	@Override
	public BigDecimal value() {
		return this.number;
	}

	@Override
	public int power() {
		return this.power;
	}

	public PowerArithRequestVO(BigDecimal number, int power) {
		super();
		this.number = number;
		this.power = power;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PowerArithRequestVO [number=").append(number).append(", power=").append(power).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + power;
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
		PowerArithRequestVO other = (PowerArithRequestVO) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (power != other.power)
			return false;
		return true;
	}

}
