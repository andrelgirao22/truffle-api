package br.com.alg.trufflesapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alg.trufflesapi.model.State;
import br.com.alg.trufflesapi.repositories.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository repository;
	
	public List<State> listAll() {
		return repository.findAll();
	}

	
	
}
