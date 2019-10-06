package br.com.alg.trufflesapi.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alg.trufflesapi.exceptions.DeliveryNotFoundException;
import br.com.alg.trufflesapi.model.Delivery;
import br.com.alg.trufflesapi.model.Order;
import br.com.alg.trufflesapi.repositories.DeliveryRepository;

@Service
public class DeliveryService {

	@Autowired
	private DeliveryRepository repository;

	public List<Delivery> listAll() {
		return repository.findAll();
	}

	public Delivery save(Delivery delivery) {
		delivery.setDate(new Date());
		return repository.save(delivery);
	}

	public void update(Delivery delivery) {
		find(delivery.getId());
		repository.save(delivery);
	}

	public void delete(Long id) {
		find(id);
		repository.deleteById(id);
	}
	
	public Delivery find(Long id) {
		return  repository.findById(id).orElseThrow(new DeliveryNotFoundException("Entrega não localizada"));
	}

	public Delivery findDeliveryByOrder(Order order) {
		return repository.findByOrder(order);
	}

}
