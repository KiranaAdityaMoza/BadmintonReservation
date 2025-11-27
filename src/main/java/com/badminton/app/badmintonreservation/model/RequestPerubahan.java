package com.badminton.app.badmintonreservation.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class RequestPerubahan {
    private int id;
    private int idReservasi;
    private String jenis;
    private LocalDate tanggalBaru;
    private LocalTime jamMulaiBaru;
    private LocalTime jamSelesaiBaru;
    private String alasan;
    private String status;

    public RequestPerubahan() {}

    public RequestPerubahan(int id, int idReservasi, String jenis, 
                            LocalDate tanggalBaru, LocalTime jamMulaiBaru, 
                            LocalTime jamSelesaiBaru, String alasan, String status) {
        this.id = id;
        this.idReservasi = idReservasi;
        this.jenis = jenis;
        this.tanggalBaru = tanggalBaru;
        this.jamMulaiBaru = jamMulaiBaru;
        this.jamSelesaiBaru = jamSelesaiBaru;
        this.alasan = alasan;
        this.status = status;
    }

    public int getId() { return id; }
    public int getIdReservasi() { return idReservasi; }
    public String getJenis() { return jenis; }
    public LocalDate getTanggalBaru() { return tanggalBaru; }
    public LocalTime getJamMulaiBaru() { return jamMulaiBaru; }
    public LocalTime getJamSelesaiBaru() { return jamSelesaiBaru; }
    public String getAlasan() { return alasan; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setIdReservasi(int idReservasi) { this.idReservasi = idReservasi; }
    public void setJenis(String jenis) { this.jenis = jenis; }
    public void setTanggalBaru(LocalDate tanggalBaru) { this.tanggalBaru = tanggalBaru; }
    public void setJamMulaiBaru(LocalTime jamMulaiBaru) { this.jamMulaiBaru = jamMulaiBaru; }
    public void setJamSelesaiBaru(LocalTime jamSelesaiBaru) { this.jamSelesaiBaru = jamSelesaiBaru; }
    public void setAlasan(String alasan) { this.alasan = alasan; }
    public void setStatus(String status) { this.status = status; }
}
