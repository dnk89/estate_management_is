package edu.rtu.cs.pit.estatemanagement.contractservices.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ContractService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;
    private String description;
    private AmountCalculationType calculationType = AmountCalculationType.AREA;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AmountCalculationType getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(AmountCalculationType calculationType) {
        this.calculationType = calculationType;
    }
}
