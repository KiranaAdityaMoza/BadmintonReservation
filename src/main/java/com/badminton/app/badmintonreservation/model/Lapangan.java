package com.badminton.app.badmintonreservation.model;

import javafx.beans.property.*;

public class Lapangan {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final IntegerProperty hargaPerJam = new SimpleIntegerProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty foto = new SimpleStringProperty();
    private final StringProperty createdAt = new SimpleStringProperty();

    // Konstruktor default
    public Lapangan() {}

    // Konstruktor lengkap
    public Lapangan(int id, String nama, int hargaPerJam, String foto, String status, String createdAt) {
        this.id.set(id);
        this.nama.set(nama);
        this.hargaPerJam.set(hargaPerJam);
        this.foto.set(foto);
        this.status.set(status);
        this.createdAt.set(createdAt);
    }

    // Getter dan Setter
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public String getNamaLapangan() { return nama.get(); }
    public void setNamaLapangan(String nama) { this.nama.set(nama); }
    public StringProperty namaProperty() { return nama; }

    public int getHargaPerJam() { return hargaPerJam.get(); }
    public void setHargaPerJam(int harga) { this.hargaPerJam.set(harga); }
    public IntegerProperty hargaPerJamProperty() { return hargaPerJam; }

    public String getStatus() { return status.get(); }
    public void setStatus(String status) { this.status.set(status); }
    public StringProperty statusProperty() { return status; }

    public String getFoto() { return foto.get(); }
    public void setFoto(String foto) { this.foto.set(foto); }
    public StringProperty fotoProperty() { return foto; }

    public String getCreatedAt() { return createdAt.get(); }
    public void setCreatedAt(String createdAt) { this.createdAt.set(createdAt); }
    public StringProperty createdAtProperty() { return createdAt; }
}
