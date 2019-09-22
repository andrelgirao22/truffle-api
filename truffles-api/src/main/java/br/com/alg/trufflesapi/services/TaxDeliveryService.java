package br.com.alg.trufflesapi.services;

import br.com.alg.trufflesapi.exceptions.TaxDeliveryNotFoundException;
import br.com.alg.trufflesapi.model.Price;
import br.com.alg.trufflesapi.model.TaxDelivery;
import br.com.alg.trufflesapi.repositories.TaxDeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaxDeliveryService {

	@Autowired
	private TaxDeliveryRepository repository;


	public List<TaxDelivery> listAll() {
		return repository.findAll();
	}

	public TaxDelivery save(TaxDelivery entity) {
		return repository.save(entity);
	}

	public void update(TaxDelivery entity) {
		TaxDelivery oldEntity = find(entity.getId());
		repository.save(entity);
	}

	public void delete(Long id) {
		find(id);
		repository.deleteById(id);
	}

	public TaxDelivery find(Long id) {
		return repository.findById(id).orElseThrow(new TaxDeliveryNotFoundException("Taxa não encontrada."));
	}

	public List<TaxDelivery> findByDistance(Double minDistance, Double maxDistance) {
		return repository.findByDistanceBetween(minDistance, maxDistance).orElseThrow(new TaxDeliveryNotFoundException("Valor não encontrado"));
	}

	public List<TaxDelivery> findByDistance(Double distance) {
		return repository.findByDistanceGreaterThanAndMaxDistanceLessThanEqual(distance).orElseThrow(new TaxDeliveryNotFoundException("Valor não encontrado"));
	}

    public Optional<TaxDelivery> findByValue(Double value) {
		return this.repository.findByValue(value);
    }
}
