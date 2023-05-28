package org.vaadin.model.kolam;

import java.io.Serializable;

import org.vaadin.model.komunitas.Komunitas;

public class Kolam {

    private Long id;

    private String name;

    private Komunitas komunitas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Komunitas getKomunitas() {
        return komunitas;
    }

    public void setKomunitas(Komunitas komunitas) {
        this.komunitas = komunitas;
    }

    
    
}
