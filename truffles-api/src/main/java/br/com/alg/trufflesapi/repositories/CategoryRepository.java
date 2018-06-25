package br.com.alg.trufflesapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alg.trufflesapi.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	@Query(	value="select c from Category c where c.name like %:name%",
			countQuery = "select count(c) from Category c where c.name like %:name%",
			nativeQuery= false)
	Page<Category> findByName(@Param("name") String name, Pageable pageable);
	
}
