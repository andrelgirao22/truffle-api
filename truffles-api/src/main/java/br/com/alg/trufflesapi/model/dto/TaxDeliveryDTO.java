package br.com.alg.trufflesapi.model.dto;

import br.com.alg.trufflesapi.model.TaxDelivery;

import java.io.Serializable;

public class TaxDeliveryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Double distance;

    private Double distanceMax;

    private Double value;

    public TaxDeliveryDTO(TaxDelivery taxDelivery) {
        this.id = taxDelivery.getId();
        this.distance = taxDelivery.getDistance();
        this.value = taxDelivery.getValue();
    }

    public Long getId() {
        return id;
    }

    public TaxDeliveryDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Double getDistance() {
        return distance;
    }

    public TaxDeliveryDTO setDistance(Double distance) {
        this.distance = distance;
        return this;
    }

    public Double getDistanceMax() {
        return distanceMax;
    }

    public TaxDeliveryDTO setDistanceMax(Double distanceMax) {
        this.distanceMax = distanceMax;
        return this;
    }

    public Double getValue() {
        return value;
    }

    public TaxDeliveryDTO setValue(Double value) {
        this.value = value;
        return this;
    }
}
