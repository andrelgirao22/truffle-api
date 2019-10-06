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
	
	public State findByUf(String uf) {
		return this.repository.findByUf(uf).orElseThrow(new StateNotFoundException("Estado não encontrado"));
	}
	
	public List<City> findCitiesByUf(String uf) {
		State state = this.findByUf(uf);
		List<City> cities =  
				cityRepository.findByState(state).orElseThrow(new CityNotFoundException("Estado não encontrado."));
		return cities;
	}
	
	
}
