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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alg.trufflesapi.model.Order;
import br.com.alg.trufflesapi.model.OrderItem;
import br.com.alg.trufflesapi.model.Payment;
import br.com.alg.trufflesapi.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderResource {

	@Autowired
	private OrderService service;
	
	@GetMapping
	public ResponseEntity<List<Order>> listItem() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody Order order) {
		
		order = service.save(order);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(order.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping(value="/{id}/orderItem")
	public ResponseEntity<Void> addOrderItem(@Valid @RequestBody OrderItem orderItem, @PathVariable("id") Long id) {
		orderItem = this.service.save(orderItem, id);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/{id}").buildAndExpand(orderItem.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping(value="/{id}/payment")
	public ResponseEntity<Void> addPayment(@Valid @RequestBody Payment payment, @PathVariable("id") Long id) {
		payment = this.service.save(payment, id);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/{id}").buildAndExpand(payment.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody Order order, @PathVariable("id") Long id) {
		order.setId(id);
		service.update(order);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value= "/{id}")
	public ResponseEntity<?> busca(@PathVariable("id") Long id) {
		
		Order order = service.find(id);
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(order);
	}
	
	@GetMapping(value= "/{id}/orderItem")
	public ResponseEntity<?> findItensByOrder(@PathVariable("id") Long id) {
		Order order = this.service.find(id);
		List<OrderItem> list = this.service.findItensByOrder(order);
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(list);
	}
	
	@GetMapping(value= "/{id}/payment")
	public ResponseEntity<?> findPaymentsByOrder(@PathVariable("id") Long id) {
		Order order = this.service.find(id);
		List<Payment> list = this.service.findPaymentByOrder(order);
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(list);
	}
	
	@DeleteMapping(value= "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id, @RequestParam("token") String token) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}