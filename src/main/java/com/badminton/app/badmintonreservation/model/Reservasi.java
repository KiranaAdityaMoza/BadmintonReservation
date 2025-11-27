package com.badminton.app.badmintonreservation.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservasi {

    private int id;
    private int userId;         // sesuai SQL (user_id)
    private int lapanganId;     // sesuai SQL (lapangan_id)

    private LocalDate tanggal;
    private LocalTime jamMulai;
    private LocalTime jamSelesai;

    private int totalHarga;
    private String status;

    private LocalDateTime createdAt; // opsional, tidak wajib ada di UI

    // ==========================
    //   CONSTRUCTOR KOSONG
    // ==========================
    public Reservasi() {}

    // ==========================
    //   CONSTRUCTOR LENGKAP
    // ==========================
    public Reservasi(int id, int userId, int lapanganId, LocalDate tanggal,
                     LocalTime jamMulai, LocalTime jamSelesai, int totalHarga,
                     String status) {

        this.id = id;
        this.userId = userId;
        this.lapanganId = lapanganId;
        this.tanggal = tanggal;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.totalHarga = totalHarga;
        this.status = status;
    }

    // ==========================
    //   GETTER & SETTER
    // ==========================

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLapanganId() {
        return lapanganId;
    }
    public void setLapanganId(int lapanganId) {
        this.lapanganId = lapanganId;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }
    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public LocalTime getJamMulai() {
        return jamMulai;
    }
    public void setJamMulai(LocalTime jamMulai) {
        this.jamMulai = jamMulai;
    }

    public LocalTime getJamSelesai() {
        return jamSelesai;
    }
    public void setJamSelesai(LocalTime jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public int getTotalHarga() {
        return totalHarga;
    }
    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
