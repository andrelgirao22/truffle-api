package br.com.alg.trufflesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alg.trufflesapi.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long>{

}
