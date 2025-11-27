package com.badminton.app.badmintonreservation.dao;

import com.badminton.app.badmintonreservation.model.Lapangan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LapanganDAO {

    // Ambil semua data lapangan
    public List<Lapangan> getAll() {
        List<Lapangan> list = new ArrayList<>();
        String sql = "SELECT * FROM lapangan";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Lapangan l = new Lapangan(
                        rs.getInt("id"),
                        rs.getString("nama_lapangan"),
                        rs.getInt("harga"),
                        rs.getString("foto"),
                        rs.getString("status"),
                        rs.getString("created_at") // ambil dari DB
                );
                list.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tambah lapangan baru
    public boolean insert(Lapangan l) {
        String sql = "INSERT INTO lapangan (nama_lapangan, harga, foto, status) VALUES (?,?,?,?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getNamaLapangan());
            stmt.setInt(2, l.getHargaPerJam());
            stmt.setString(3, l.getFoto());
            stmt.setString(4, l.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update data lapangan
    public boolean update(Lapangan l) {
        String sql = "UPDATE lapangan SET nama_lapangan=?, harga=?, foto=?, status=? WHERE id=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getNamaLapangan());
            stmt.setInt(2, l.getHargaPerJam());
            stmt.setString(3, l.getFoto());
            stmt.setString(4, l.getStatus());
            stmt.setInt(5, l.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Hapus lapangan
    public boolean delete(int id) {
        String sql = "DELETE FROM lapangan WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hitung total lapangan
    public int countAll() {
        String sql = "SELECT COUNT(*) AS total FROM lapangan";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // default jika error
    }
}
