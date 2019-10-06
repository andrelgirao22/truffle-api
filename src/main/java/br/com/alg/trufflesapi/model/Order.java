package br.com.alg.trufflesapi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name="tb_order")
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_order")
	private Long id;
	
	@Column(name="dt_order")
	@DateTimeFormat(pattern	="dd/MM/yyyy") 
	private Date date;
	
	@Column(name="vl_value")
	@NumberFormat(pattern="#,##0.00")
	private Double orderValue;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tx_status")
	private OrderStatusType status;
	
	@OneToMany(mappedBy="order")
	private List<OrderItem> orderItens = new ArrayList<>();
	
	@OneToMany(mappedBy="order")
	private List<Payment> payments = new ArrayList<>();

	@OneToOne
	@JoinColumn(name="id_account")
	private Account account;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<OrderItem> getOrderItens() {
		return orderItens;
	}

	public void setOrderItens(List<OrderItem> orderItens) {
		this.orderItens = orderItens;
	}
	
	public void addOrderItem(OrderItem orderItem) {
		if(this.orderItens == null) this.orderItens = new ArrayList<OrderItem>();
		this.orderItens.add(orderItem);
	}

	public Double getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Double orderValue) {
		this.orderValue = orderValue;
	}

	public OrderStatusType getStatus() {
		return status;
	}

	public void setStatus(OrderStatusType status) {
		this.status = status;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}		
}
