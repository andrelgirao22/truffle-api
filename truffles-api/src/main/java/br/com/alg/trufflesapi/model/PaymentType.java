package br.com.alg.trufflesapi.model;

public enum PaymentType {

	CARTAO_CREDITO("Cartao"),
	DINHEIRO("Dinheiro");
	
	private String description;
	
	PaymentType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
