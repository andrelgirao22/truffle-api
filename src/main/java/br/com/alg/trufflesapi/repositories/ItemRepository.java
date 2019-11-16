package br.com.alg.trufflesapi.repositories;

import br.com.alg.trufflesapi.services.business.ProductBoughtMost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.alg.trufflesapi.model.Category;
import br.com.alg.trufflesapi.model.Item;

import java.util.Date;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

	Page<Item> findByCategory(Category category, Pageable pageable);

	
	@Query(	value="select i from Item i where i.name like %:search%",
			countName = "select count(i) from Item i where i.name like %:search%",
			nativeQuery = false)
	Page<Item> findByName(String search, Pageable pageRequest);


	@Query(nativeQuery = true, value =
			"select b.id_item as id, b.tx_name as name, sum(a.vl_amount) as quantity " +
					"  from tb_order_item a " +
					" inner join tb_item b on a.id_item = b.id_item " +
					" inner join tb_order c on a.id_order = c.id_order " +
					" where a.dt_item_order between ?1 and ?2  and b.id_item <> 99999 " +
					"   and c.tx_status = 'CONCLUIDO'" +
					" group by a.id_item " +
					" order by a.dt_item_order desc")
    List<ProductBoughtMost> findProductsBoughtMost(Date dtInit, Date dtFinal);
}
