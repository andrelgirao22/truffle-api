package br.com.alg.trufflesapi.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_item")
public class Item {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_item")
	private Long id;
	
	@Column(name="tx_name")
	private String name;
	
	@Column(name="tx_description")
	private String description;
	
	@JsonIgnore
	@Column(name="tx_url_photo")
	private String urlPhoto;
	
	@Column(name="tx_status")
	@Enumerated(EnumType.STRING)
	private StatusItem status;
	
	@Column(name="dt_item")
	@DateTimeFormat(pattern	="dd/MM/yyyy") 
	private Date date;
	
	@OneToMany(mappedBy="item", fetch=FetchType.LAZY)
	private List<Price> prices;
	
	@OneToMany(mappedBy="item")
	private List<Note> notes;
	
	@OneToOne
	@JoinColumn(name="id_category")
	private Category category;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
	
	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public StatusItem getStatus() {
		return status;
	}

	public void setStatus(StatusItem status) {
		this.status = status;
	}
	
	public boolean isPending() {
		return this.status.equals(StatusItem.PENDENTE);
	}
}
