package br.com.alg.trufflesapi.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.com.alg.trufflesapi.services.business.ClientBoughtMost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.alg.trufflesapi.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Optional<Account> findByEmail(String email);

	@Query(nativeQuery = true, value =
            "select a.id_account, a.id_account as id , a.tx_email as email , sum(vl_value) as value " +
            "  from tb_order o " +
            " inner join tb_account a on a.id_account = o.id_account" +
            " where o.dt_order between ?1 and ?2 " +
            " group by id_account , a.tx_email" +
            " order by o.id_account desc")
    List<ClientBoughtMost> findClientsBoughtMost(Date dtInit, Date dtFinal);
}
