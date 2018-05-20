package br.com.alg.trufflesapi.resources;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import br.com.alg.trufflesapi.model.Category;
import br.com.alg.trufflesapi.services.CategoryService;

@RestController
@CrossOrigin(origins="${origin-permited}")
@RequestMapping("/category")
public class CategoryResource {

	@Autowired
	private CategoryService service;
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER','DEV')")
	public ResponseEntity<List<Category>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
	}
	
	@GetMapping(value="/page")
	@PreAuthorize("hasAnyRole('ADMIN','USER','DEV')")
	public ResponseEntity<Page<Category>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderby", defaultValue="name") String orderby, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(service.findPage(page, linesPerPage, orderby, direction));
	}
		
	@PostMapping
	@PreAuthorize("hasAnyRole('DEV')")
	public ResponseEntity<Void> save(@Valid @RequestBody Category category) {
		
		category = service.save(category);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(category.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping(value="/image/{id}")
	@PreAuthorize("hasAnyRole('DEV')")
	public ResponseEntity<Void> saveImage(@Valid @RequestParam(value="file", required=false) MultipartFile file,  @PathVariable(value="id", required= false) String id) {
		
		if(file == null) return null;
		
		service.saveImage(id, file);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand("Sucesso").toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value="/{id}")
	@PreAuthorize("hasAnyRole('DEV')")
	public ResponseEntity<Void> update(@RequestBody Category category, @PathVariable("id") Long id) {
		category.setId(id);
		service.update(category);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{image}/image")
	@PreAuthorize("hasAnyRole('DEV','USER')")
	public ResponseEntity<Map<String, String>> getImageByItem(@PathVariable("image") String image) {
		Map<String, String> jsonMap = this.service.getImage(image);
		return ResponseEntity.status(HttpStatus.OK).body(jsonMap);
	}
	
	@GetMapping(value= "/{id}")
	@PreAuthorize("hasAnyRole('DEV','USER')")
	public ResponseEntity<?> busca(@PathVariable("id") Long id) {
		
		Category category = service.find(id);
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(category);
	}
	
	
	@DeleteMapping(value= "/{id}")
	@PreAuthorize("hasAnyRole('DEV')")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}