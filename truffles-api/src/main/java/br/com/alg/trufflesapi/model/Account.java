package br.com.alg.trufflesapi.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="tb_account")
public class Account {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_account")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="id_user")
	private User user;
	
	@Column(name="dt_start")
	@DateTimeFormat(pattern	="dd/MM/yyyy") 
	private Date dtStart;
	
	@Column(name="dt_end")
	@DateTimeFormat(pattern	="dd/MM/yyyy") 
	private Date dtEnd;
	
	@Column(name="bl_status")
	private boolean status;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_infor_account")
	private AccountInfo infoAccount;

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public AccountInfo getInfoAccount() {
		return infoAccount;
	}

	public void setInfoAccount(AccountInfo infoAccount) {
		this.infoAccount = infoAccount;
	}
}
