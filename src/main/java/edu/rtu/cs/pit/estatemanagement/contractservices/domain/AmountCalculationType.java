package edu.rtu.cs.pit.estatemanagement.contractservices.domain;

public enum AmountCalculationType {
    AREA("Platība"),
    INHABITANTS("Iedzīvotāju skaits"),
    READINGS("Rādījumi");

    private final String description;

    AmountCalculationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
