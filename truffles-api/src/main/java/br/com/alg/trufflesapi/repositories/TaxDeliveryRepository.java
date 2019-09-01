package br.com.alg.trufflesapi.repositories;

import br.com.alg.trufflesapi.model.TaxDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxDeliveryRepository extends JpaRepository<TaxDelivery, Long> {


    Optional<List<TaxDelivery>> findByDistanceBetween(Double minDistance, Double maxDistance);

    @Query(nativeQuery = true, value = "SELECT a.* FROM tb_tax_delivery a WHERE ?1 BETWEEN a.vl_distance AND a.vl_max_distance;")
    Optional<List<TaxDelivery>> findByDistanceGreaterThanAndMaxDistanceLessThanEqual(Double distance);
}
