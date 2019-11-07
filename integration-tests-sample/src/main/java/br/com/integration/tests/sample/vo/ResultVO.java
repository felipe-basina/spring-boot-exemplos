package br.com.integration.tests.sample.vo;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class ResultVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2924683056480215786L;
	
	@Expose
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
