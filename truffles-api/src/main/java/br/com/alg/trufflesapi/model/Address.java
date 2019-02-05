package br.com.alg.trufflesapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_address")
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_address")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="account_id")
	@JsonIgnore
	private Account account;
	
	@Column(name="tx_address_name")
	private String addressName;
	
	@Column(name="tx_address_number")
	private String addressNumber;
	
	@Column(name="tx_neighborhood")
	private String neighborhood;
	
	@Column(name="tx_postal_code")
	private String postalCode;
	
	@OneToOne
	@JoinColumn(name="id_city")
	private City city;
	
	@OneToOne
	@JoinColumn(name="id_state")
	private State state;

	@Column(name="tx_compl")
	private String complement;

	public Address() {
	}
	
	public Address(Long id, Account account, Address address) {
		super();
		this.id = id;
		this.account = account;
		this.addressName = address.getAddressName();
		this.addressNumber = address.getAddressNumber();
		this.neighborhood = address.getNeighborhood();
		this.city = address.getCity();
		this.state = address.getState();
		this.city.setState(state);
		this.complement = address.getComplement();
		this.postalCode = address.getPostalCode();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}
	
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
