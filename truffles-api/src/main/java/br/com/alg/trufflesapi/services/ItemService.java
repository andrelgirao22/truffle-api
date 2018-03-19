package br.com.alg.trufflesapi.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alg.trufflesapi.exceptions.ItemNotFoundException;
import br.com.alg.trufflesapi.exceptions.PriceNotFoudException;
import br.com.alg.trufflesapi.model.Item;
import br.com.alg.trufflesapi.model.Price;
import br.com.alg.trufflesapi.repositories.ItemRepository;
import br.com.alg.trufflesapi.repositories.PriceRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository repository;
	
	@Autowired
	private PriceRepository priceRepository;

	public List<Item> listAll() {
		return repository.findAll();
	}

	public Item save(Item item) {
		item.setDate(new Date());
		return repository.save(item);
	}

	public void update(Item item) {
		find(item.getId());
		repository.save(item);
	}

	public void delete(Long id) {
		find(id);
		repository.deleteById(id);
	}
	
	public Item find(Long id) {
		return repository.findById(id).orElseThrow(new ItemNotFoundException("Item não encontrado."));
	}
	
	public List<Price> findPrices(Item item) {
		return 
				priceRepository.findByItem(item).orElseThrow((new PriceNotFoudException("Item " + item.getId() + " " + item.getName()  + " sem preço ")));
	}
	
	public Price saveItemPrice(Price price, Long id) {
		Item item = find(id);
		price.setItem(item);
		price.setDtStart(new Date());
		return priceRepository.save(price);
	}
	
	public List<Price> findPriceByItem(Item item) {
		item = find(item.getId());
		return this.findPrices(item);
	}
	
	
}
