package br.com.alg.trufflesapi.model;

public enum StatusItem {

	PENDENTE("Pendente"),
	PUBLICADO("Publicado");
	
	private String description;
	
	StatusItem(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
