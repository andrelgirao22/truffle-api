package br.com.alg.trufflesapi.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.com.alg.trufflesapi.model.Account;

public class AccountDTO {

	
	private String name;
	
	@NotEmpty
	@Email(message="email inválido")
	private String email;
	
	private String password;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
