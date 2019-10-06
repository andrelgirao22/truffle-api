package br.com.alg.trufflesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alg.trufflesapi.model.Delivery;
import br.com.alg.trufflesapi.model.Order;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>{

	Delivery findByOrder(Order order);
}
