package com.pfa.fatboar.FatboarBack.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * table qui contient 6 lignes avec tous les gains
 * @author lourteau
 *
 */
@Entity
@Table(name = "gains")
public class Gain {
	
	public Gain() {
		
	}

    public Gain(@NotBlank @Size(max = 40) String label) {
        this.label = label;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String label;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
