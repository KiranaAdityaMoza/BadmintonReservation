package com.badminton.app.badmintonreservation.dao;

import com.badminton.app.badmintonreservation.model.RequestPerubahan;
import com.badminton.app.badmintonreservation.model.Reservasi;
import com.badminton.app.badmintonreservation.model.User;
import com.badminton.app.badmintonreservation.model.Lapangan;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RequestPerubahanDAO {

    private Connection conn;

    public RequestPerubahanDAO() {
        conn = com.badminton.app.badmintonreservation.dao.Database.getConnection();
    }

    // === GET ALL PENDING ===
    public List<RequestPerubahan> getPendingRequests() {
        List<RequestPerubahan> list = new ArrayList<>();

        String sql = "SELECT * FROM request_perubahan WHERE status = 'pending' ORDER BY created_at DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RequestPerubahan r = new RequestPerubahan(
                        rs.getInt("id"),
                        rs.getInt("reservasi_id"),
                        rs.getString("jenis_request"),
                        rs.getDate("tanggal_baru") != null ? rs.getDate("tanggal_baru").toLocalDate() : null,
                        rs.getTime("jam_mulai_baru") != null ? rs.getTime("jam_mulai_baru").toLocalTime() : null,
                        rs.getTime("jam_selesai_baru") != null ? rs.getTime("jam_selesai_baru").toLocalTime() : null,
                        rs.getString("alasan"),
                        rs.getString("status")
                );

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // === APPROVE ===
    public boolean approveRequest(int id) {
        String sql = "UPDATE request_perubahan SET status = 'approved' WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // === REJECT ===
    public boolean rejectRequest(int id) {
        String sql = "UPDATE request_perubahan SET status = 'rejected' WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public int countPending() {
    String sql = "SELECT COUNT(*) AS total FROM request_perubahan WHERE status = 'pending'";
    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) return rs.getInt("total");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}
    
    public boolean insert(RequestPerubahan req) {
    String sql = "INSERT INTO request_perubahan " +
                 "(reservasi_id, jenis_request, tanggal_baru, jam_mulai_baru, jam_selesai_baru, alasan, status, created_at) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, req.getIdReservasi());
        stmt.setString(2, req.getJenis());

        // nullable fields
        if (req.getTanggalBaru() != null) {
            stmt.setDate(3, Date.valueOf(req.getTanggalBaru()));
        } else {
            stmt.setNull(3, Types.DATE);
        }

        if (req.getJamMulaiBaru() != null) {
            stmt.setTime(4, Time.valueOf(req.getJamMulaiBaru()));
        } else {
            stmt.setNull(4, Types.TIME);
        }

        if (req.getJamSelesaiBaru() != null) {
            stmt.setTime(5, Time.valueOf(req.getJamSelesaiBaru()));
        } else {
            stmt.setNull(5, Types.TIME);
        }

        stmt.setString(6, req.getAlasan());
        stmt.setString(7, req.getStatus());

        return stmt.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}



}
