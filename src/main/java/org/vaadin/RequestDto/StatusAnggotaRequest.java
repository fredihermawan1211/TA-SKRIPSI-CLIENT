package org.vaadin.RequestDto;

import javax.validation.constraints.NotNull;

import org.vaadin.model.komunitas.Anggota;
import org.vaadin.model.komunitas.StatusAnggota;

public class StatusAnggotaRequest {
    
    private Anggota anggota;
    
    private StatusAnggota statusAnggota;

    
    public StatusAnggota getStatusAnggota() {
        return statusAnggota;
    }


    public void setStatusAnggota(StatusAnggota statusAnggota) {
        this.statusAnggota = statusAnggota;
    }


    public Anggota getAnggota() {
        return anggota;
    }


    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }

    
    
}
