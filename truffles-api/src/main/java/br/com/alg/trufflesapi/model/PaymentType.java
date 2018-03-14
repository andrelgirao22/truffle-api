package br.com.alg.trufflesapi.model;

public enum PaymentType {

	CREDIT_CARD("CRE"),
	CASH("CAS");
	
	private String description;
	
	PaymentType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
