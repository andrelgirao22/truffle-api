package br.com.alg.trufflesapi.resources;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alg.trufflesapi.model.Delivery;
import br.com.alg.trufflesapi.model.Order;
import br.com.alg.trufflesapi.model.OrderItem;
import br.com.alg.trufflesapi.model.Payment;
import br.com.alg.trufflesapi.services.DeliveryService;
import br.com.alg.trufflesapi.services.OrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin("${origin-permited}")
public class OrderResource {

	@Autowired
	private OrderService service;
	
	@Autowired
	private DeliveryService deliveryService;
	
	@GetMapping
	public ResponseEntity<List<Order>> listItem() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
	}
	
	@GetMapping(value="/account/{email}")
	public ResponseEntity<Page<Order>> findPageByAccount(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderby", defaultValue="id") String orderby, 
			@RequestParam(value="direction", defaultValue="DESC") String direction,
			@PathVariable(value = "email") String email) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(service.findByAccount(page, linesPerPage, orderby, direction, email));
	}
	
	@GetMapping(value="/page")
	public ResponseEntity<Page<Order>> findPageByName(
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
	
	@GetMapping(value="/{id}/delivery")
	public ResponseEntity<?> findDeliveryByOrder(@PathVariable("id") Long id) {
		Order order = this.service.find(id);
		Delivery delivery = this.deliveryService.findDeliveryByOrder(order);
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(delivery);
		
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