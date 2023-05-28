package org.vaadin.model.komunitas;

import java.io.Serializable;
import org.vaadin.model.user.User;

public class Anggota {

    private Long id;

    private StatusAnggota statusAnggota;

    private Komunitas komunitas;
    
    private User user;

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
