package org.vaadin.model.komunitas;

import java.io.Serializable;

public class Komunitas {

    private Long id;

    private String nama;

    private String alamat;    

    public Komunitas() {
    }    

    public Komunitas(Long id, String nama, String alamat) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
