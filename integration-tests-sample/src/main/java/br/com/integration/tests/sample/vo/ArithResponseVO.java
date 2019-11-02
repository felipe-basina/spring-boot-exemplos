package br.com.integration.tests.sample.vo;

public class ArithResponseVO<T> {

	private int httpStatusCode;
	
	private T result;

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public T getResult() {
		return result;
	}

	public ArithResponseVO(int httpStatusCode, T result) {
		super();
		this.httpStatusCode = httpStatusCode;
		this.result = result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArithResponseVO [httpStatusCode=").append(httpStatusCode).append(", result=").append(result)
				.append("]");
		return builder.toString();
	}
	
}
