package br.com.alg.trufflesapi.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import br.com.alg.trufflesapi.model.Item;
import br.com.alg.trufflesapi.model.Price;
import br.com.alg.trufflesapi.model.PriceType;
import br.com.alg.trufflesapi.model.dto.ItemDTO;
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
	
	@GetMapping(value="/page")
	public ResponseEntity<Page<Item>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderby", defaultValue="name") String orderby, 
			@RequestParam(value="direction", defaultValue="ASC") String direction,
			@RequestParam(value = "search", defaultValue="") String search) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(service.findPage(page, linesPerPage, orderby, direction, search));
	}
	
	@GetMapping("/price/{id}")
	public ResponseEntity<List<Price>> listPricesByItem(@Valid @PathVariable("id") Long id) {
		Item item = this.service.find(id);
		return ResponseEntity.status(HttpStatus.OK).body(service.findPriceByItem(item));
	}
	
	@GetMapping("/priceType")
	public ResponseEntity<List<PriceType>> listPriceType() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listPriceType());
	}
	
	
	@GetMapping("/category/{id}")
	public ResponseEntity<Page<ItemDTO>> listByCategory(@Valid @PathVariable("id") Long id,
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderby", defaultValue="name") String orderby, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Item> itemPage = service.findByCategory(id,page, linesPerPage, orderby, direction);
		
		Page<ItemDTO> itemDTOPage = itemPage.map(i -> new ItemDTO(i));
		
		return ResponseEntity.status(HttpStatus.OK).body(itemDTOPage);
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
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
	
	@PostMapping(value="/picture/{id}")
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> saveImage(@Valid @RequestParam(name="file", required=true) MultipartFile file,  @PathVariable(name="id", required= true) Long id) {
		
		if(file == null) throw new FileException("Imagem não enviada.");
		
		URI uri = this.service.uploadPicture(file, id);
		return ResponseEntity.created(uri).build();
	}

	@GetMapping(value = "/picture/{id}/index/{index}")
	public @ResponseBody byte[] getImage(
			@PathVariable(name="id", required= true) Long id,
			@PathVariable(name = "index", required = true)Integer index) throws IOException {
		InputStream in = this.service.getImageFromId(id, index);
		return IOUtils.toByteArray(in);
	}

	@PostMapping(value="/{id}/price")
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> saveItemPrice(@Valid @RequestBody Price price, @PathVariable("id") Long id) {
		price = service.saveItemPrice(price, id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(price.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value="/{id}")
	@PreAuthorize("hasAnyRole('DEV','ADMIN')")
	public ResponseEntity<Void> update(@RequestBody Item item, @PathVariable("id") Long id) {
		item.setId(id);
		service.update(item);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value= "/{id}")
	public ResponseEntity<ItemDTO> busca(@PathVariable("id") Long id) {
		
		ItemDTO item = service.findItemDto(id);
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(item);
	}
	
	
	@DeleteMapping(value= "/{id}")
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value="/picture/{id}/index/{index}")
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> deletePicture(@Valid @PathVariable(name="id", required= true) Long id, @PathVariable(name = "index", required = true) Integer index) {
		
		service.deletePicture(id, index);;
		return ResponseEntity.noContent().build();
	}
}