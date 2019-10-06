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
@Table(name="tb_price")
public class Price {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_price")
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_item")
	private Item item;
	
	@Column(name="cc_price")
	@Enumerated(EnumType.STRING)
	private PriceType typePrice;
	
	@Column(name="vl_price")
	@NumberFormat(pattern="#,##0.00")
	private Double price;

	@Column(name="dt_start")
	@DateTimeFormat(pattern	="dd/MM/yyyy") 
	private Date dtStart;
	
	@Column(name="dt_end")
	@DateTimeFormat(pattern	="dd/MM/yyyy") 
	private Date dtEnd;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public PriceType getTypePrice() {
		return typePrice;
	}

	public void setTypePrice(PriceType typePrice) {
		this.typePrice = typePrice;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
}
