package br.com.alg.trufflesapi.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import br.com.alg.trufflesapi.exceptions.FileException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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


	@GetMapping(value="/page")
	public ResponseEntity<Page<AccountDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderby", defaultValue="name") String orderby,
			@RequestParam(value="direction", defaultValue="ASC") String direction,
			@RequestParam(value = "search", defaultValue="") String search) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(service.findPage(page, linesPerPage, orderby, direction, search));
	}

	@PostMapping
	public ResponseEntity<Void> saveAccount(@RequestBody AccountDTO accountDto) {
		
		Account account = this.service.fromDto(accountDto);
		
		account = this.service.save(account);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(account.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}


	@GetMapping(value = "/picture/{id}/index/{index}")
	public ResponseEntity<byte[]> getImage(
			@PathVariable(name="id", required= true) Long id,
			@PathVariable(name = "index", required = true)Integer index) throws IOException {
		InputStream in = this.service.getImageFromId(id, index);

		CacheControl cacheControl = CacheControl.maxAge(3600, TimeUnit.SECONDS);

		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(IOUtils.toByteArray(in));
	}

	@DeleteMapping(value="/picture/{id}/index/{index}")
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> deletePicture(@Valid @PathVariable(name="id", required= true) Long id, @PathVariable(name = "index", required = true) Integer index) {
		service.deletePicture(id, index);;
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value="/picture/{id}")
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> saveImage(@Valid @RequestParam(name="file", required=true) MultipartFile file,  @PathVariable(name="id", required= true) Long id) {

		if(file == null) throw new FileException("Imagem não enviada.");

		URI uri = this.service.uploadPicture(file, id);
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
