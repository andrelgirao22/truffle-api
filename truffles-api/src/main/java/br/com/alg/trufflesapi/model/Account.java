package br.com.alg.trufflesapi.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name="tb_account")
public class Account implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_account")
	private Long id;
	
	@Column(name="tx_name")
	private String name;
	
	@NotEmpty
	@Column(name="tx_email", unique=true)
	private String email;
	
	@NotEmpty
	@Column(name="tx_password")
	@JsonIgnore
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="tb_account_groups",
			joinColumns= @JoinColumn(name= "id_account", referencedColumnName = "id_account"),
			inverseJoinColumns= @JoinColumn(name="id_group", referencedColumnName="id_group"))
	private List<Group> groups;
	
	@Column(name="tx_image_user")
	private String userImage;
	
	@Column(name="tx_address_name")
	private String addressName;
	
	@Column(name="tx_address_number")
	private String addressNumber;
	
	@Column(name="tx_neighborhood")
	private String neighborhood;
	
	@Column(name="tx_city")
	private String city;
	
	@Column(name="tx_state")
	private String state;
	
	@Column(name="tx_compl")
	private String complement;

	@Column(name="dt_start")
	@DateTimeFormat(pattern	="dd/MM/yyyy") 
	private Date dtStart;
	
	@Column(name="dt_end")
	@DateTimeFormat(pattern	="dd/MM/yyyy") 
	private Date dtEnd;
	
	@Column(name="bl_status")
	private boolean status;

	@Column(name = "dt_last_password_reset")
	private Timestamp lastPasswordResetDate;
	
	public Long getId() {
		return id;
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

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDtStart() {
		return dtStart;
	}

	public void setDtStart(Date dtStart) {
		this.dtStart = dtStart;
	}

	public Date getDtEnd() {
		return dtEnd;
	}

	public void setDtEnd(Date dtEnd) {
		this.dtEnd = dtEnd;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.groups;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Timestamp getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}
}
