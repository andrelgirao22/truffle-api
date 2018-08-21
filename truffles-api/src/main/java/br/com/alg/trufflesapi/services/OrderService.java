package br.com.alg.trufflesapi.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.alg.trufflesapi.exceptions.OrderNotFoundException;
import br.com.alg.trufflesapi.model.Account;
import br.com.alg.trufflesapi.model.Order;
import br.com.alg.trufflesapi.model.OrderItem;
import br.com.alg.trufflesapi.model.Payment;
import br.com.alg.trufflesapi.repositories.OrderItemRepository;
import br.com.alg.trufflesapi.repositories.OrderRepository;
import br.com.alg.trufflesapi.repositories.PaymentRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private AccountService accountService;
	/*
	@Autowired
	private ItemService itemService;*/

	public List<Order> listAll() {
		return orderRepository.findAll();
	}

	public Order save(Order order) {
		
		List<Payment> payments = order.getPayments();
		List<OrderItem> orderItens = order.getOrderItens();		
		
		order.setId(null);
		order.setDate(new Date());
		
		Account account = this.accountService.find(order.getAccount().getId());
		order.setAccount(account);
		
		for(Payment payment: payments) {
			payment.setOrder(order);
			payment.setDataPayment(new Date());			
		}
		
		for(OrderItem item: orderItens) {
			item.setDate(new Date());
			item.setOrder(order);
		}
		
		order = orderRepository.save(order);
		this.orderItemRepository.saveAll(orderItens);
		this.paymentRepository.saveAll(payments);
		
		/*orderItens.forEach(orderItem ->  {			
			this.save(orderItem, newOrder.getId());
		});
		
		payments.forEach(payment -> {
			this.save(payment, newOrder.getId());
		});*/
		
		return order;
	}
	
	public OrderItem save(OrderItem orderItem, Long id) {
		Order order =  find(id);
		orderItem.setOrder(order);
		orderItem.setDate(new Date());
		orderItem.setValue(orderItem.getValue());
		
		orderItem = this.orderItemRepository.save(orderItem);
		
		order.addOrderItem(orderItem);
		order.setOrderValue(getTotalOrder(order));
		
		this.orderRepository.save(order);
		
		return orderItem;
	}

	public Payment save(Payment payment, Long id) {
		Order order =  find(id);
		//checkPayment(payment, order);
		payment.setOrder(order);
		payment.setDataPayment(new Date());
		return paymentRepository.save(payment);
	}

	public void update(Order order) {
		find(order.getId());
		orderRepository.save(order);
	}

	public void delete(Long id) {
		find(id);
		orderRepository.deleteById(id);
	}
	
	public Double getTotalOrder(Order order) {
		return order.getOrderItens().stream().mapToDouble(oi -> oi.getValue()).sum();
	}
	
	public Double getTotalPaymentOrder(Order order) {
		return 
			order.getPayments()
				.stream()
				.filter(payment -> payment.getValue() != null)
				.mapToDouble(oi -> oi.getValue()).sum();
	}
	
	public Order find(Long id) {
		return orderRepository.findById(id).orElseThrow(new OrderNotFoundException("Pedido não encontrado"));
	}
	
	/*private void checkPayment(Payment payment, Order order) {
		
		Double totalPayment = getTotalPaymentOrder(order);
		Double totalOrder = order.getOrderValue();
		Double value = payment.getValue();
		if(value > (totalOrder - totalPayment)) {
			throw new PaymentInvalidException("Valor Pagamento superior ao valor do pedido");
		}
	}*/
	
	public List<OrderItem> findItensByOrder(Order order) {
		return this.orderItemRepository.findByOrder(order);
	}
	
	public List<Payment> findPaymentByOrder(Order order) {
		return this.paymentRepository.findByOrder(order);
	}

	public Page<Order> findPageByName(Integer page, Integer linesPerPage, String orderby, String direction, String name) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
		return this.orderRepository.findAll(pageRequest);
	}
}
