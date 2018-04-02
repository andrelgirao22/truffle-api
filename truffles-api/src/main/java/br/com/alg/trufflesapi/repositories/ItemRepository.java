package br.com.alg.trufflesapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alg.trufflesapi.model.Category;
import br.com.alg.trufflesapi.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

	List<Item> findByCategory(Category category);
	
}
