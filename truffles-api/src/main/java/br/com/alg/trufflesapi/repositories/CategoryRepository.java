package br.com.alg.trufflesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alg.trufflesapi.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
