package br.com.alg.trufflesapi.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alg.trufflesapi.exceptions.FileException;
import br.com.alg.trufflesapi.model.Category;
import br.com.alg.trufflesapi.services.CategoryService;

@RestController
@CrossOrigin(origins="${origin-permited}")
@RequestMapping("/category")
public class CategoryResource {

	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
	}
	
	@GetMapping(value="/page")
	public ResponseEntity<Page<Category>> findPageByName(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderby", defaultValue="name") String orderby, 
			@RequestParam(value="direction", defaultValue="ASC") String direction,
			@RequestParam(value = "name", defaultValue="") String name) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(service.findPageByName(page, linesPerPage, orderby, direction, name));
	}
		
	@PostMapping
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> save(@Valid @RequestBody Category category, HttpServletResponse response) {
		
		category = service.save(category);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(category.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping(value="/picture/{id}")
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> saveImage(@Valid @RequestParam(name="file", required=true) MultipartFile file,  @PathVariable(name="id", required= true) Long id) {
		
		if(file == null) throw new FileException("Imagem não enviada.");
		
		URI uri = this.service.uploadPicture(file, id);
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value="/picture/{id}/index/{index}")
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> deletePicture(@Valid @PathVariable(name="id", required= true) Long id, @PathVariable(name = "index", required = true) Integer index) {
		service.deletePicture(id, index);;
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value="/{id}")
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> update(@RequestBody Category category, @PathVariable("id") Long id) {
		category.setId(id);
		service.update(category);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value= "/{id}")
	@PreAuthorize("hasAnyRole('DEV','USER','ADMIN')")
	public ResponseEntity<?> busca(@PathVariable("id") Long id) {
		
		Category category = service.find(id);
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(category);
	}
	
	@GetMapping(value = "/picture/{id}/index/{index}")
	public ResponseEntity<byte[]> getImage(
			@PathVariable(name="id", required= true) Long id,
			@PathVariable(name = "index", required = true)Integer index) throws IOException {
		InputStream in = this.service.getImageFromId(id, index);
		
		CacheControl cacheControl = CacheControl.maxAge(120, TimeUnit.SECONDS);
		
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(IOUtils.toByteArray(in));
	}
	
	
	@DeleteMapping(value= "/{id}")
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}