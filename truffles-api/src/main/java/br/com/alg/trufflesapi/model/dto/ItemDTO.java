package br.com.alg.trufflesapi.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.com.alg.trufflesapi.model.Category;
import br.com.alg.trufflesapi.model.Item;
import br.com.alg.trufflesapi.model.Price;
import br.com.alg.trufflesapi.model.PriceType;

public class ItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;	
	private String name;
	private String description;
	private String imageUrl; 
	private Date date;
	private Double price;
	private Category category;
	private List<Price> prices;
	
	public ItemDTO() {
	}

	public ItemDTO(Item item) {
		super();
		this.id = item.getId();
		this.name = item.getName();
		this.description = item.getDescription();
		this.imageUrl = item.getImageUrl();
		this.date = item.getDate();
		this.prices = item.getPrices();
		Price p = item.getPrices().get(0);; 
		this.price = p.getPrice();
		this.category = item.getCategory();
	}
	
	public ItemDTO(Item item, PriceType priceType) {
		super();
		this.id = item.getId();
		this.name = item.getName();
		this.description = item.getDescription();
		this.imageUrl = item.getImageUrl();
		this.date = item.getDate();
		Optional<Price> optionalPrice = 
				item.getPrices()
				.stream()
				.filter(pri -> pri.getTypePrice().equals(priceType)).findFirst();
		if(optionalPrice.isPresent()) {
			Price p = optionalPrice.get();;
			this.price = p.getPrice();
		}
	}

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
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public ItemDTO setCategory(Category category) {
		this.category = category;
		return this;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public ItemDTO setPrices(List<Price> prices) {
		this.prices = prices;
		return this;
	}
}
