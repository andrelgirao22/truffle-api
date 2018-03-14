package br.com.alg.trufflesapi.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alg.trufflesapi.model.Account;
import br.com.alg.trufflesapi.services.AccountService;

@RestController
@RequestMapping(value="/account")
public class AccountResource {
	
	@Autowired
	private AccountService service;
	
	@GetMapping
	public ResponseEntity<List<Account>> list() {
		return ResponseEntity.ok(this.service.listAll());
	}
	
	@PostMapping
	public ResponseEntity<Void> saveAccount(@RequestBody Account account) {
		account = this.service.save(account);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(account.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Account> findById(@PathVariable("id") Long id) {
		Account account = this.service.find(id);
		return ResponseEntity.status(HttpStatus.OK).body(account);
	}
	
}
