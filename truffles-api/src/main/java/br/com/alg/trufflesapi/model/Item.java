package br.com.alg.trufflesapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

@Entity
@Table(name="tb_item")
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_item")
	private Long id;
	
	@Column(name="tx_name")
	private String name;
	
	@Column(name="tx_description")
	private String description;
	
	@Column(name="tx_image")
	private String image;
	
	@Column(name="tx_status")
	@Enumerated(EnumType.STRING)
	private StatusItem status;
	
	@Column(name="dt_item")
	@DateTimeFormat(pattern	="dd/MM/yyyy") 
	private Date date;
	
	@OneToMany(mappedBy="item", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Price> prices = new ArrayList<>();
	
	@OneToMany(mappedBy="item")
	private List<Note> notes = new ArrayList<>();
	
	@OneToOne
	@JoinColumn(name="id_category")
	private Category category;
	
	public Item() {
	}
	
	public Item(Long id, String name, String description, String image, StatusItem status, Date date,
			Category category) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.status = status;
		this.date = date;
		this.category = category;
	}

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
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
