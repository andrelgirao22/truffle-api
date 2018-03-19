package br.com.alg.trufflesapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alg.trufflesapi.model.Item;
import br.com.alg.trufflesapi.model.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {
	
	public Optional<List<Price>> findByItem(Item item);

}
