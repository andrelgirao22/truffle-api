package br.com.alg.trufflesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alg.trufflesapi.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long>{

	Group findByName(String name);
	
}
