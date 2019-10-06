package br.com.alg.trufflesapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alg.trufflesapi.model.City;
import br.com.alg.trufflesapi.model.State;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

	public Optional<City> findByName(String name);
	
	public Optional<List<City>> findByState(State state);
	
}
