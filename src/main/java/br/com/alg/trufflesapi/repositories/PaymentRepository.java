package br.com.alg.trufflesapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alg.trufflesapi.model.Order;
import br.com.alg.trufflesapi.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByOrder(Order order);
	
}
