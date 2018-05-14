package br.com.alg.trufflesapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_group")
public class Group implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	/*public static final String ADMIN = "ROLE_ADMIN";
	public static final String USER = "ROLE_USER";
	public static final String MASTER = "ROLE_MASTER";
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_group")
	private Long id;
	
	@NotEmpty
	@Column(name="tx_name")
	private String name;

	@ManyToMany(mappedBy="groups")
	@JsonIgnore
	private List<Account> accounts;
	
	@ManyToMany
	@JoinTable(name="tb_groups_permissions",
		joinColumns= @JoinColumn(name= "id_group", referencedColumnName = "id_group"),
		inverseJoinColumns= @JoinColumn(name="id_permission", referencedColumnName="id_permission"))
	private List<Permission> permissions;
	
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

	public String getDescription() {
		return this.name;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public void addAccounts(Account account) {
		if(accounts == null) accounts = new ArrayList<>();
		this.accounts.add(account);
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	public void addPermission(Permission permission) {
		if(permissions == null) permissions = new ArrayList<>();
		permissions.add(permission);
	}

	@Override
	public String getAuthority() {
		return this.name;
	}
}