package br.com.alg.trufflesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alg.trufflesapi.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	
	
}
