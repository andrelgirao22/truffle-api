package br.com.alg.trufflesapi.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alg.trufflesapi.model.State;
import br.com.alg.trufflesapi.services.StateService;

@RestController
@RequestMapping(value="/state")
public class StateResouce {
	
	@Autowired
	private StateService service;
	
	@GetMapping
	public ResponseEntity<List<State>> listItem() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
	}
}
