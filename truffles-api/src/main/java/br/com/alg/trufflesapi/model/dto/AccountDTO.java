package br.com.alg.trufflesapi.model.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.com.alg.trufflesapi.model.Account;

public class AccountDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String name;
	
	@NotEmpty
	@Email(message="email inválido")
	private String email;
	
	private String password;
	
	private String addressName;
	
	private String addressNumber;
	
	private String neighborhood;
	
	private Long cityId;
	
	private String state;
	
	private String complement;

	private String register;

	private String postalCode;
	
	public AccountDTO() {
		
	}
	
	public AccountDTO(Account account) {
		this.name = account.getName();
		this.email = account.getEmail();
		this.password = account.getPassword();
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

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}


	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
}
