package br.com.alg.trufflesapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.alg.trufflesapi.model.Category;
import br.com.alg.trufflesapi.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

	Page<Item> findByCategory(Category category, Pageable pageable);

	
	@Query(	value="select i from Item i where i.name like %:search%",
			countName = "select count(i) from Item i where i.cname like %:search%",
			nativeQuery = false)
	Page<Item> findByName(String search, Pageable pageRequest);
	
}
