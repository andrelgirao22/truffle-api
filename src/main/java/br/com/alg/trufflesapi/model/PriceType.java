package br.com.alg.trufflesapi.model;

public enum PriceType {

	NORMAL("Normal"), PROMOCIONAL("Promocional"), FRETE("Frete");
	
	private String description;
	
	private PriceType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}
