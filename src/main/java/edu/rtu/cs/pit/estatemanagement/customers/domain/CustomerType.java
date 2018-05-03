package edu.rtu.cs.pit.estatemanagement.customers.domain;

public enum CustomerType {
    PERSON("Fiziska persona"),
    COMPANY("Juridiska persona");

    private final String description;

    CustomerType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
