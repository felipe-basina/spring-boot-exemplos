package br.com.integration.tests.sample.types;

public enum ArithOperations {

	SUM("/sum"), 
	SUBTRACT("/subtract"), 
	MULTIPLY("/multiply"), 
	DIVISION("/divide"), 
	POWER("/power");
	
	private String remoteOperation;
	
	private ArithOperations(String remoteOperation) {
		this.remoteOperation = remoteOperation;
	}
	
	public String getRemoteOperation() {
		return this.remoteOperation;
	}
	
}
