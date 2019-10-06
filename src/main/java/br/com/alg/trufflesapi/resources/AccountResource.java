package br.com.alg.trufflesapi.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alg.trufflesapi.model.Account;
import br.com.alg.trufflesapi.model.dto.AccountDTO;
import br.com.alg.trufflesapi.services.AccountService;

@RestController
@CrossOrigin("${origin-permited}")
@RequestMapping(value="/account")
public class AccountResource {
	
	@Autowired
	private AccountService service;
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<AccountDTO>> list() {
		return ResponseEntity.ok(this.service.listAll());
	}
	
	@PostMapping
	public ResponseEntity<Void> saveAccount(@RequestBody AccountDTO accountDto) {
		
		Account account = this.service.fromDto(accountDto);
		
		account = this.service.save(account);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(account.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping(value="/picture/{id}")
	public ResponseEntity<Void> savePicture(@Valid @RequestParam(name="file", required=true) MultipartFile file, @PathVariable(name="id") Long id) {
		if(file == null) return null;
		
		URI uri = service.uploadPicture(file, id);
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value="/email/{email}")
	public ResponseEntity<AccountDTO> findByEmail(@PathVariable("email") String email) {
		Optional<Account> account = this.service.findByEmail(email);
		AccountDTO accountDTO = new AccountDTO(account.get());
		return ResponseEntity.status(HttpStatus.OK).body(accountDTO);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<AccountDTO> findById(@PathVariable("id") Long id) {
		Account account = this.service.find(id);
		AccountDTO accountDTO = new AccountDTO(account);
		return ResponseEntity.status(HttpStatus.OK).body(accountDTO);
	}
	
}
