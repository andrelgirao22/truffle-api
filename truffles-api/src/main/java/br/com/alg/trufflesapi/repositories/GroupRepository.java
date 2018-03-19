package br.com.alg.trufflesapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alg.trufflesapi.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long>{

	Optional<Group> findByIdOrName(Long id, String name);
	
	Optional<Group> findByName(String name);
	
}
