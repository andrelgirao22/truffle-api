package br.com.alg.trufflesapi.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.alg.trufflesapi.model.City;
import br.com.alg.trufflesapi.model.State;
import br.com.alg.trufflesapi.services.StateService;

@RestController
public class StateResouce {
	
	@Autowired
	private StateService service;
	
	@GetMapping("/state")
	public ResponseEntity<List<State>> listItem() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
	}
	
	@GetMapping("/city/{uf}")
	public ResponseEntity<List<City>> listCities(@PathVariable("uf") String uf) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.findCitiesByUf(uf));
	}
}
