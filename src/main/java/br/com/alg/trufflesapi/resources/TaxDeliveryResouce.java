package br.com.alg.trufflesapi.resources;

import br.com.alg.trufflesapi.model.TaxDelivery;
import br.com.alg.trufflesapi.services.TaxDeliveryService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/taxDelivery")
public class TaxDeliveryResouce {
	
	@Autowired
	private TaxDeliveryService service;
	
	@GetMapping
	public ResponseEntity<List<TaxDelivery>> listItem() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
	}

	@GetMapping("/valueBetween")
	public ResponseEntity<List<TaxDelivery>> getTaxDelivery(@RequestParam(value = "minDistance") Double minDistance,
													  @RequestParam(value = "maxDistance") Double maxDistance) {
		return ResponseEntity.status(HttpStatus.OK).body(service.findByDistance(minDistance, maxDistance));
	}

	@GetMapping("/distance")
	public ResponseEntity<List<TaxDelivery>> getTaxDeliveryByDistance(@RequestParam(value = "distance") Double distance) {
		return ResponseEntity.status(HttpStatus.OK).body(service.findByDistance(distance));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> save(@Valid @RequestBody TaxDelivery taxDelivery) {
		taxDelivery = service.save(taxDelivery);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(taxDelivery.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> update(@RequestBody TaxDelivery taxDelivery, @PathVariable("id") Long id) {
		taxDelivery.setId(id);
		service.update(taxDelivery);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value= "/{id}")
	@PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	

}
