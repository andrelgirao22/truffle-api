package br.com.alg.trufflesapi.resources;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alg.trufflesapi.model.Group;
import br.com.alg.trufflesapi.model.User;
import br.com.alg.trufflesapi.services.UserService;

@RestController
@RequestMapping("/user")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity<List<User>> listItem() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody User user,
			@RequestParam(value = "urlPhoto", required = false) MultipartFile image) {
		
		user = service.save(user, image);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value="{id}")
	public ResponseEntity<Void> update(@RequestBody User user, @PathVariable("id") Long id) {
		user.setId(id);
		service.update(user);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value= "/{email}/valid")
	public ResponseEntity<?> busca(@PathVariable("email") String email, 
			@RequestParam("token") String token, @RequestParam("password") String password) {
		User user = service.findByEmail(email);
		//service.validateUser(user, password);
		
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(user);
	}
	
	@GetMapping(value= "/{id}")
	public ResponseEntity<?> busca(@PathVariable("id") Long id) {
		User user = service.find(id);
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(user);
	}
	
	@DeleteMapping(value= "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}	
}