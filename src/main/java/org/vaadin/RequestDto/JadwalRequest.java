package org.vaadin.RequestDto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.vaadin.model.kolam.Kolam;
import org.vaadin.model.komunitas.Anggota;

public class JadwalRequest {
    private Long id;
    
    private Date dateToDo;
    
    private Kolam kolam;
    
    private Anggota anggota;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateToDo() {
        return dateToDo;
    }

    public void setDateToDo(Date dateToDo) {
        this.dateToDo = dateToDo;
    }

    public Kolam getKolam() {
        return kolam;
    }

    public void setKolam(Kolam kolam) {
        this.kolam = kolam;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }

    

}
