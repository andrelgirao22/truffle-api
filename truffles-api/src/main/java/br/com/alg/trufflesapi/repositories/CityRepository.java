package br.com.alg.trufflesapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alg.trufflesapi.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

	public Optional<City> findByName(String name);
	
}
