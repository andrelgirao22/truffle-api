package br.com.alg.trufflesapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alg.trufflesapi.exceptions.CityNotFoundException;
import br.com.alg.trufflesapi.exceptions.StateNotFoundException;
import br.com.alg.trufflesapi.model.City;
import br.com.alg.trufflesapi.model.State;
import br.com.alg.trufflesapi.repositories.CityRepository;
import br.com.alg.trufflesapi.repositories.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository repository;
	
	@Autowired
	private CityRepository cityRepository;
	
	public List<State> listAll() {
		return repository.findAll();
	}
	
	public City findCityByName(String name) {		
		return 
			this.cityRepository.findByName(name).orElseThrow(new CityNotFoundException("Cidade não encontrada"));
	}

	public City findCity(String city, String state) {
		State s = 
				repository.findByInitial(state).orElseThrow(new StateNotFoundException("Estado não encontrado."));
		City c = new City(null, city, s);
		return c;
	}
	
	
}
