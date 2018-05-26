package br.com.alg.trufflesapi.model.dto;

import br.com.alg.trufflesapi.model.Account;

public class AccountDTO {

	
	private String name;
	private String email;
	
	public AccountDTO() {
		
	}
	
	public AccountDTO(Account account) {
		this.name = account.getName();
		this.email = account.getEmail();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
