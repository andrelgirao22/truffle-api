package br.com.alg.trufflesapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alg.trufflesapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByIdOrEmail(Long id, String email);
	Optional<User> findByEmail(String email);

}
