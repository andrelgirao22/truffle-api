package br.com.alg.trufflesapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alg.trufflesapi.model.Order;
import br.com.alg.trufflesapi.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	public List<OrderItem> findByOrder(Order order);
	
}
