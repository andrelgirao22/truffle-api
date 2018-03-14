package br.com.alg.trufflesapi.resources;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alg.trufflesapi.model.Group;
import br.com.alg.trufflesapi.services.GroupService;

@RestController
@RequestMapping(value="/group")
public class GroupResource {

	@Autowired
	private GroupService service;
	
	@GetMapping
	public ResponseEntity<List<Group>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.listAll());
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Group> findById(@PathVariable Long id) {
		Group group = this.service.findById(id);
		
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(group);
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody Group group) {
		
		group = this.service.save(group);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(group.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody Group group, @PathVariable("id") Long id) {
		group.setId(id);
		this.service.update(group);
		return ResponseEntity.noContent().build();
	}
	
}
