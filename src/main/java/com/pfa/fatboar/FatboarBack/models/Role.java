package com.pfa.fatboar.FatboarBack.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public enum Role {
    ROLE_SUPER_ADMIN(0, "SUPER_ADMIN"), ROLE_ADMIN(1, "ADMIN"), ROLE_MANAGER(2, "EMPLOYEE"), ROLE_EMPLOYE(3, "CLIENT"), ROLE_CLIENT(4, "MANAGER");

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
