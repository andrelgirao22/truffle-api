package br.com.alg.trufflesapi.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_account")
public class Account implements Serializable, UserDetails {

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
	@JsonIgnore
	@Column(name="tx_password")
	private String password;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JsonIgnore
	@JoinTable(name="tb_account_groups",
			joinColumns= @JoinColumn(name="id_account", referencedColumnName = "id_account"),
			inverseJoinColumns= @JoinColumn(name="id_group", referencedColumnName="id_group"))
	private List<Group> groups = new ArrayList<>();
	
	public Account() {
	}
	
	public Account(Long id, String name, String email, String password, String register) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.register = register;
		this.dtStart = new Date();
		this.status = true;
	}

	@Column(name="tx_image_user")
	private String userImage;
	
	@Column(name="tx_register")
	private String register;
	
	@OneToMany(mappedBy="account")
	private List<Address> addresses = new ArrayList<>();

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

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
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

	public List<Address> getAddresses() {
		return addresses;
	}

	public void addAddresses(Address address) {
		this.addresses.add(address);
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
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
