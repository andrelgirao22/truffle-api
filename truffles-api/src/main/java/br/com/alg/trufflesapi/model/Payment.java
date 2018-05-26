package br.com.alg.trufflesapi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_payment")
public class Payment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_payment")
	private Long id;
	
	@Column(name="cc_payment")
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_order")
	private Order order;
	
	@Column(name="tx_auth_code")
	private String authorizationCode;
	
	@Column(name="vl_value")
	@NumberFormat(pattern="#,##0.00")
	private Double value;
	
	@Column(name="tx_currency")
	private String currency;
	
	@Column(name="dt_payment")
	@DateTimeFormat(pattern	="dd/MM/yyyy") 
	private Date dataPayment;
	
	@Column(name="dt_expires")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dataExpires;

	@Column(name="tx_status")
	private String status;

	public Payment() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataPayment() {
		return dataPayment;
	}

	public void setDataPayment(Date dataPayment) {
		this.dataPayment = dataPayment;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Date getDataExpires() {
		return dataExpires;
	}

	public void setDataExpires(Date dataExpires) {
		this.dataExpires = dataExpires;
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
		Payment other = (Payment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
