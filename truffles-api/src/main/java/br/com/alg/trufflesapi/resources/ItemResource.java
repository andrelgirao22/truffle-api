package br.com.alg.trufflesapi.resources;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import br.com.alg.trufflesapi.model.Item;
import br.com.alg.trufflesapi.model.Price;
import br.com.alg.trufflesapi.model.PriceType;
import br.com.alg.trufflesapi.services.ItemService;

@RestController
@RequestMapping("/item")
@CrossOrigin("${origin-permited}")
public class ItemResource {

	@Autowired
	private ItemService service;
	
	@GetMapping
	public ResponseEntity<List<Item>> listItem() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
	}
	
	@GetMapping("/{id}/price")
	public ResponseEntity<List<Price>> listPricesByItem(@Valid @PathVariable("id") Long id) {
		Item item = this.service.find(id);
		return ResponseEntity.status(HttpStatus.OK).body(service.findPriceByItem(item));
	}
	
	@GetMapping("/priceType")
	public ResponseEntity<List<PriceType>> listPriceType() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listPriceType());
	}
	
	@GetMapping("/{image}/image")
	public ResponseEntity<Map<String, String>> getImageByItem(@PathVariable("image") String image) {
		Map<String, String> jsonMap = this.service.getImage(image);
		
		CacheControl cacheControl = CacheControl.maxAge(60, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(jsonMap);
		
	}
	
	@GetMapping("/category/{id}")
	public ResponseEntity<List<Item>> listByCategory(@Valid @PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(service.findByCategory(id));
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody Item item) {
		
		item = service.save(item);
		final Long id = item.getId();
		item.getPrices().forEach(price -> {			
			service.saveItemPrice(price, id);
		});
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(item.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping(value="/image/{id}")
	public ResponseEntity<Void> saveImage(@Valid @RequestParam(value="file", required=false) MultipartFile file,  @PathVariable(value="id", required= false) String id) {
		
		if(file == null) return null;
		
		service.saveImage(id, file);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand("Sucesso").toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping(value="/{id}/price")
	public ResponseEntity<Void> saveItemPrice(@Valid @RequestBody Price price, @PathVariable("id") Long id) {
		price = service.saveItemPrice(price, id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(price.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody Item item, @PathVariable("id") Long id) {
		item.setId(id);
		service.update(item);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value= "/{id}")
	public ResponseEntity<?> busca(@PathVariable("id") Long id) {
		
		Item item = service.find(id);
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(item);
	}
	
	
	@DeleteMapping(value= "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id, @RequestParam("token") String token) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}