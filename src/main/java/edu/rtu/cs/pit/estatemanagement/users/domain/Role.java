package edu.rtu.cs.pit.estatemanagement.users.domain;

public enum Role {
    ADMIN("ROLE_ADMIN"),
    OPERATOR("ROLE_OPERATOR"),
    CUSTOMER("ROLE_CUSTOMER");

    private final String fullName;

    Role(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return this.fullName;
    }
}
