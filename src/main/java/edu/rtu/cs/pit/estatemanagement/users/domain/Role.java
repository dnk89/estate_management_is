package edu.rtu.cs.pit.estatemanagement.users.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "system_user_role")
public class Role {

    @Id
    private int id;
    private String name;
    private String displayName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
