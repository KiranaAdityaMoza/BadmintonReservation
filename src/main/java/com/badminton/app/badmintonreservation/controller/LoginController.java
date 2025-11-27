package com.badminton.app.badmintonreservation.controller;

import com.badminton.app.badmintonreservation.dao.UserDAO;
import com.badminton.app.badmintonreservation.model.User;
import com.badminton.app.badmintonreservation.util.Session;
import com.badminton.app.badmintonreservation.util.SceneLoader;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtNamaLengkap;
    @FXML private ComboBox<String> cbRole;
    @FXML private Button btnLogin;
    @FXML private Button btnRegister;
    @FXML private Label lblMessage;

    private UserDAO userDAO;

    public LoginController() {
        this.userDAO = new UserDAO();
    }

    @FXML
    public void initialize() {
        // ComboBox role
        cbRole.getItems().addAll("user", "admin");
        cbRole.getSelectionModel().select("user"); // default

        btnLogin.setOnAction(e -> handleLogin());
        btnRegister.setOnAction(e -> handleRegister());
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String role = cbRole.getValue();

        if(username.isEmpty() || password.isEmpty() || role == null) {
            lblMessage.setStyle("-fx-text-fill:red;");
            lblMessage.setText("Username, Password, dan Role wajib diisi!");
            return;
        }

        // login dengan role check
        User user = userDAO.login(username, password, role);

        if (user != null) {
            Session.login(user);

            // Load dashboard sesuai role
            if ("admin".equals(user.getRole())) {
    SceneLoader.load("admin/DashboardAdminView.fxml");
} else {
    SceneLoader.load("user/DashboardUserView.fxml");
}


        } else {
            lblMessage.setStyle("-fx-text-fill:red;");
            lblMessage.setText("Username, password, atau role salah!");
        }
    }

    private void handleRegister() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String namaLengkap = txtNamaLengkap.getText().trim();

        // Role otomatis user untuk registrasi
        String role = "user";

        if(username.isEmpty() || password.isEmpty() || namaLengkap.isEmpty()) {
            lblMessage.setStyle("-fx-text-fill:red;");
            lblMessage.setText("Semua field harus diisi!");
            return;
        }

        User newUser = new User(0, username, password, role, namaLengkap);
        boolean success = userDAO.insert(newUser);

        if(success) {
            lblMessage.setStyle("-fx-text-fill:green;");
            lblMessage.setText("Registrasi berhasil! Silakan login.");
            txtUsername.clear();
            txtPassword.clear();
            txtNamaLengkap.clear();
            cbRole.getSelectionModel().select("user");
        } else {
            lblMessage.setStyle("-fx-text-fill:red;");
            lblMessage.setText("Username sudah terpakai, coba yang lain.");
        }
    }
}
