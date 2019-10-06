package br.com.alg.trufflesapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_order_item")
public class OrderItem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_item_order")
	private Long id;
	
	@Column(name="dt_item_order")
	@DateTimeFormat(pattern	="dd/MM/yyyy") 
	private Date date;
	
	@OneToOne
	@JoinColumn(name="id_item")
	private Item item;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_order")
	private Order order;
	
	@Column(name="vl_amount")
	@NumberFormat(pattern="#,##0.00")
	private Double quantity;
	
	@Column(name="vl_value")
	@NumberFormat(pattern="#,##0.00")
	private Double value;
	
	@Column(name="bl_status")
	private boolean status;
	
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
}
