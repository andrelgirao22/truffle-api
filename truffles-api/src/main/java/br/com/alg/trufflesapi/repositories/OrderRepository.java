package br.com.alg.trufflesapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alg.trufflesapi.model.Account;
import br.com.alg.trufflesapi.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Page<Order> findByAccount(Account account, Pageable pageRequest);
	
}
