package com.badminton.app.badmintonreservation.dao;

import com.badminton.app.badmintonreservation.model.Reservasi;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservasiDAO {

    // Ambil semua reservasi
    public List<Reservasi> getAll() {
        List<Reservasi> list = new ArrayList<>();
        String sql = "SELECT * FROM reservasi ORDER BY tanggal, jam_mulai";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Reservasi r = new Reservasi();
                r.setId(rs.getInt("id"));
                r.setUserId(rs.getInt("user_id"));
                r.setLapanganId(rs.getInt("lapangan_id"));
                r.setTanggal(rs.getDate("tanggal").toLocalDate());
                r.setJamMulai(rs.getTime("jam_mulai").toLocalTime());
                r.setJamSelesai(rs.getTime("jam_selesai").toLocalTime());
                r.setTotalHarga(rs.getInt("total_harga"));
                r.setStatus(rs.getString("status"));
                r.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Insert reservasi baru
    public boolean insert(Reservasi r) {
        String sql = "INSERT INTO reservasi (user_id, lapangan_id, tanggal, jam_mulai, jam_selesai, total_harga, status, created_at) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, r.getUserId());
            stmt.setInt(2, r.getLapanganId());
            stmt.setDate(3, Date.valueOf(r.getTanggal()));
            stmt.setTime(4, Time.valueOf(r.getJamMulai()));
            stmt.setTime(5, Time.valueOf(r.getJamSelesai()));
            stmt.setInt(6, r.getTotalHarga());
            stmt.setString(7, r.getStatus());
            stmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update reservasi
    public boolean update(Reservasi r) {
        String sql = "UPDATE reservasi SET user_id=?, lapangan_id=?, tanggal=?, jam_mulai=?, jam_selesai=?, total_harga=?, status=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, r.getUserId());
            stmt.setInt(2, r.getLapanganId());
            stmt.setDate(3, Date.valueOf(r.getTanggal()));
            stmt.setTime(4, Time.valueOf(r.getJamMulai()));
            stmt.setTime(5, Time.valueOf(r.getJamSelesai()));
            stmt.setInt(6, r.getTotalHarga());
            stmt.setString(7, r.getStatus());
            stmt.setInt(8, r.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete reservasi
    public boolean delete(int id) {
        String sql = "DELETE FROM reservasi WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Menghitung jumlah reservasi upcoming (hari ini dan seterusnya)
public int countUpcoming() {
    String sql = "SELECT COUNT(*) FROM reservasi WHERE tanggal >= ?";
    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setDate(1, Date.valueOf(LocalDate.now()));

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
    
    
}

    // Ambil reservasi upcoming (hari ini dan seterusnya)
    public List<Reservasi> getUpcoming(int limit) {
        List<Reservasi> list = new ArrayList<>();
        String sql = "SELECT * FROM reservasi WHERE tanggal >= ? ORDER BY tanggal, jam_mulai LIMIT ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservasi r = new Reservasi();
                    r.setId(rs.getInt("id"));
                    r.setUserId(rs.getInt("user_id"));
                    r.setLapanganId(rs.getInt("lapangan_id"));
                    r.setTanggal(rs.getDate("tanggal").toLocalDate());
                    r.setJamMulai(rs.getTime("jam_mulai").toLocalTime());
                    r.setJamSelesai(rs.getTime("jam_selesai").toLocalTime());
                    r.setTotalHarga(rs.getInt("total_harga"));
                    r.setStatus(rs.getString("status"));
                    r.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                    list.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
        // ================================
    // Tambahan untuk Request Perubahan
    // ================================

    // Update tanggal dan jam (reschedule)
    public boolean updateDateTime(int idReservasi, LocalDate tanggalBaru,
                                  LocalTime jamMulaiBaru, LocalTime jamSelesaiBaru) {

        String sql = "UPDATE reservasi SET tanggal = ?, jam_mulai = ?, jam_selesai = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(tanggalBaru));
            stmt.setTime(2, Time.valueOf(jamMulaiBaru));
            stmt.setTime(3, Time.valueOf(jamSelesaiBaru));
            stmt.setInt(4, idReservasi);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update status reservasi (approved / rejected / canceled)
    public boolean updateStatus(int idReservasi, String newStatus) {

        String sql = "UPDATE reservasi SET status = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, idReservasi);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
