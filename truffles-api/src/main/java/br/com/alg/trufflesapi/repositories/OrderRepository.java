package br.com.alg.trufflesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alg.trufflesapi.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	
}
