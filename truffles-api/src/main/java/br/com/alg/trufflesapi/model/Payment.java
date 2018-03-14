package br.com.alg.trufflesapi.model;

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
public class Payment {

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

	@Column(name="tx_status")
	private String status;
	
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
}
