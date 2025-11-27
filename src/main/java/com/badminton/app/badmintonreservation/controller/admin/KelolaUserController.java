package com.badminton.app.badmintonreservation.controller.admin;

import com.badminton.app.badmintonreservation.dao.UserDAO;
import com.badminton.app.badmintonreservation.model.User;
import com.badminton.app.badmintonreservation.util.SceneLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class KelolaUserController {

    @FXML private TableView<User> tableUser;
    @FXML private TableColumn<User, Integer> colId;
    @FXML private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colNama;
    @FXML private TableColumn<User, String> colRole;

    @FXML private TextField txtNama;
    @FXML private ComboBox<String> comboRole;
    @FXML private Button btnResetPassword;
    @FXML private Button btnSave;
    @FXML private Button btnDelete;
    @FXML private Button btnBack;

    private User selectedUser;
    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        comboRole.getItems().addAll("admin", "user");

        setupTable();
        loadUsers();

        tableUser.setOnMouseClicked(e -> fillForm());

        btnSave.setOnAction(e -> saveChanges());
        btnResetPassword.setOnAction(e -> resetPassword());
        btnDelete.setOnAction(e -> deleteUser());
        btnBack.setOnAction(e -> SceneLoader.load("admin/DashboardAdminView.fxml"));
    }

    private void setupTable() {
        colId.setCellValueFactory(c -> c.getValue().idProperty().asObject());
        colUsername.setCellValueFactory(c -> c.getValue().usernameProperty());
        colNama.setCellValueFactory(c -> c.getValue().namaLengkapProperty());
        colRole.setCellValueFactory(c -> c.getValue().roleProperty());
    }

    private void loadUsers() {
        tableUser.setItems(FXCollections.observableArrayList(userDAO.getAllUsers()));
    }

    private void fillForm() {
        selectedUser = tableUser.getSelectionModel().getSelectedItem();
        if (selectedUser == null) return;

        txtNama.setText(selectedUser.getNamaLengkap());
        comboRole.setValue(selectedUser.getRole());
    }

    private void saveChanges() {
        if (selectedUser == null) {
            show("Pilih user terlebih dahulu!");
            return;
        }

        selectedUser.setNamaLengkap(txtNama.getText());
        selectedUser.setRole(comboRole.getValue());

        if (userDAO.updateUser(selectedUser)) {
            show("User berhasil diperbarui!");
            loadUsers();
        } else {
            show("Gagal memperbarui user.");
        }
    }

    private void resetPassword() {
        if (selectedUser == null) {
            show("Pilih user dulu!");
            return;
        }

        userDAO.resetPassword(selectedUser.getId(), "123456");
        show("Password berhasil direset ke: 123456");
    }

    private void deleteUser() {
        if (selectedUser == null) {
            show("Pilih user dulu!");
            return;
        }

        if (userDAO.deleteUser(selectedUser.getId())) {
            show("User berhasil dihapus!");
            loadUsers();
            clearForm();
        } else {
            show("Gagal menghapus user.");
        }
    }

    private void clearForm() {
        txtNama.clear();
        comboRole.setValue(null);
        selectedUser = null;
    }

    private void show(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.show();
    }
}
