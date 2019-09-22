package br.com.alg.trufflesapi.model;

import org.springframework.format.annotation.NumberFormat;
import javax.persistence.*;

@Entity
@Table(name = "tb_tax_delivery",uniqueConstraints = @UniqueConstraint(columnNames = {"vl_distance", "vl_max_distance"}))
public class TaxDelivery {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_tax_delivery")
    private Long id;

    @Column(name="vl_distance")
    @NumberFormat(pattern="#,##0.00")
    private Double distance;

    @Column(name="vl_max_distance")
    @NumberFormat(pattern="#,##0.00")
    private Double maxDistance;

    @Column(name="vl_tax_delivery")
    @NumberFormat(pattern="#,##0.00")
    private Double value;

    public Long getId() {
        return id;
    }

    public TaxDelivery setId(Long id) {
        this.id = id;
        return this;
    }

    public Double getDistance() {
        return distance;
    }

    public TaxDelivery setDistance(Double distance) {
        this.distance = distance;
        return this;
    }

    public Double getMaxDistance() {
        return maxDistance;
    }

    public TaxDelivery setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
        return this;
    }

    public Double getValue() {
        return value;
    }

    public TaxDelivery setValue(Double value) {
        this.value = value;
        return this;
    }
}
