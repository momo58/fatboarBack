package com.pfa.fatboar.FatboarBack.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name ="gains")
public class Gain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 1)
    private String type;

    @NotBlank
    @Size(max = 40)
    private String label;

    public Gain() {
    }

    public Gain(@NotBlank @Size(max = 1) String type, @NotBlank @Size(max = 40) String label) {
        this.type = type;
        this.label = label;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
