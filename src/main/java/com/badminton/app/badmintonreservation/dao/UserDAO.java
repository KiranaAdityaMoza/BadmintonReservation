package com.badminton.app.badmintonreservation.dao;

import com.badminton.app.badmintonreservation.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class UserDAO {

    // Login dengan role check
    public User login(String username, String password, String role) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("nama_lengkap")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // login gagal
    }

    // Insert user baru (register)
    public boolean insert(User user) {
        try (Connection conn = Database.getConnection()) {
            // Cek username
            String checkQuery = "SELECT id FROM users WHERE username = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, user.getUsername());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    return false; // username sudah ada
                }
            }

            // Insert user, role tetap sesuai input
            String query = "INSERT INTO users (username, password, role, nama_lengkap) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getRole());
                ps.setString(4, user.getNamaLengkap());
                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // Tambahan untuk dashboard
    // =========================

    // Hitung total semua user
    public int countAll() {
    String sql = "SELECT COUNT(*) AS total FROM users";
    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            return rs.getInt("total");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}
    
    // ============================================================
//   TAMBAHAN METHOD UNTUK KELOLA USER (AMAN, TIDAK MENGGANGGU LOGIN)
// ============================================================

public List<User> getAllUsers() {
    List<User> list = new ArrayList<>();

    String sql = "SELECT * FROM users ORDER BY id ASC";

    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));  // tetap dipakai login
            u.setRole(rs.getString("role"));
            u.setNamaLengkap(rs.getString("nama_lengkap"));

            list.add(u);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

// ============================================================
//  UPDATE USER (EDIT NAMA LENGKAP + ROLE)
// ============================================================
public boolean updateUser(User user) {

    String sql = "UPDATE users SET nama_lengkap = ?, role = ? WHERE id = ?";

    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, user.getNamaLengkap());
        stmt.setString(2, user.getRole());
        stmt.setInt(3, user.getId());

        return stmt.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

// ============================================================
//  RESET PASSWORD USER (default: 123456)
// ============================================================
public boolean resetPassword(int id, String newPassword) {

    String sql = "UPDATE users SET password = ? WHERE id = ?";

    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, newPassword);
        stmt.setInt(2, id);

        return stmt.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

// ============================================================
//  HAPUS USER
// ============================================================
public boolean deleteUser(int id) {

    String sql = "DELETE FROM users WHERE id = ?";

    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);

        return stmt.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


}
