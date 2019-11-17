package com.pfa.fatboar.FatboarBack.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public enum Role {
    ROLE_ADMIN(1, "ADMIN"), ROLE_EMPLOYEE(2, "EMPLOYEE"), ROLE_CLIENT(3, "CLIENT");

    @Id
    private int id;

    private String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
