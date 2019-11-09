package br.com.alg.trufflesapi.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.com.alg.trufflesapi.model.Group;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.alg.trufflesapi.model.Account;
import br.com.alg.trufflesapi.model.Address;

public class AccountDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<Group> groups;

	private Long id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	@Email(message="email inválido")
	private String email;
	
	private List<Address> addresses;
	
	private String password;
	
	private String imageUrl;

	private String register;

	public AccountDTO() {
		
	}
	
	public AccountDTO(Account account) {
		this.id = account.getId();
		this.name = account.getName();
		this.email = account.getEmail();
		this.password = account.getPassword();
		this.imageUrl = account.getImageUrl();
		this.addresses = account.getAddresses();
		this.groups = account.getGroups();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public void setRegister(String register) {
		this.register = register;
	}

	public String getRegister() {
		return this.register;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
}
