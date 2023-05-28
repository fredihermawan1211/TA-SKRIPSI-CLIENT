package org.vaadin.RequestDto;

import javax.validation.constraints.NotNull;

import org.apache.catalina.User;
import org.vaadin.model.komunitas.Komunitas;
import org.vaadin.model.komunitas.StatusAnggota;

public class AnggotaRequest {

    private Long id;
    
    private Komunitas komunitas;
    
    private User user;
    
    private StatusAnggota statusAnggota;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Komunitas getKomunitas() {
        return komunitas;
    }

    public void setKomunitas(Komunitas komunitas) {
        this.komunitas = komunitas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StatusAnggota getStatusAnggota() {
        return statusAnggota;
    }


    public void setStatusAnggota(StatusAnggota statusAnggota) {
        this.statusAnggota = statusAnggota;
    }


    
    
}
