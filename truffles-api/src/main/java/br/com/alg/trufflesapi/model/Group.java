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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_group")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_group")
	private Long id;
	
	@NotEmpty
	@Column(name="tx_name")
	private String name;

	@ManyToMany(mappedBy="groups")
	@JsonIgnore
	private List<User> users;
	
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void addUsers(User user) {
		if(users == null) users = new ArrayList<>();
		this.users.add(user);
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


	/*@Override
	public String getAuthority() {
		return this.name;
	}*/
}