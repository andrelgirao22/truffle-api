package br.com.alg.trufflesapi.model.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.alg.trufflesapi.model.Account;
import br.com.alg.trufflesapi.model.Address;

public class AccountDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	@Email(message="email inválido")
	private String email;
	
	@JsonIgnore
	private String password;
	
	private String addressName;
	
	private String addressNumber;
	
	private String neighborhood;
	
	private String city;
	
	private String state;
	
	private String complement;

	private String register;

	private String postalCode;
	
	public AccountDTO() {
		
	}
	
	public AccountDTO(Account account) {
		this.id = account.getId();
		this.name = account.getName();
		this.email = account.getEmail();
		this.password = account.getPassword();
		if(account.getAddresses().size() > 0) {
			Address address = account.getAddresses().get(0);
			this.addressName = address.getAddressName();
			this.addressNumber = address.getAddressNumber();
			this.neighborhood = address.getNeighborhood();
			this.postalCode = address.getPostalCode();
			this.complement = address.getComplement();
			this.state = address.getState();
		}
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


	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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
