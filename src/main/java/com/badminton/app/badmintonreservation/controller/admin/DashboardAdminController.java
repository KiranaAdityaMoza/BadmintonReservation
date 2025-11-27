package com.badminton.app.badmintonreservation.controller.admin;

import com.badminton.app.badmintonreservation.dao.LapanganDAO;
import com.badminton.app.badmintonreservation.dao.UserDAO;
import com.badminton.app.badmintonreservation.dao.ReservasiDAO;
import com.badminton.app.badmintonreservation.dao.RequestPerubahanDAO;
import com.badminton.app.badmintonreservation.model.Reservasi;
import com.badminton.app.badmintonreservation.util.Session;
import com.badminton.app.badmintonreservation.util.SceneLoader;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DashboardAdminController {

    // BUTTON
    @FXML private Button btnManageLapangan;
    @FXML private Button btnManageReservasi;
    @FXML private Button btnViewRequest;
    @FXML private Button btnManageUser;
    @FXML private Button btnLogout;

    // LABEL STATISTIK
    @FXML private Label lblTotalLapangan;
    @FXML private Label lblTotalUser;
    @FXML private Label lblTotalReservasi;
    @FXML private Label lblTotalRequestPending;

    // TABLE
    @FXML private TableView<Reservasi> tblRecentReservasi;

    @FXML private TableColumn<Reservasi, Integer> colId;
    @FXML private TableColumn<Reservasi, Integer> colUserId;
    @FXML private TableColumn<Reservasi, Integer> colLapanganId;
    @FXML private TableColumn<Reservasi, LocalDate> colTanggal;
    @FXML private TableColumn<Reservasi, LocalTime> colJamMulai;
    @FXML private TableColumn<Reservasi, LocalTime> colJamSelesai;
    @FXML private TableColumn<Reservasi, Integer> colTotalHarga;
    @FXML private TableColumn<Reservasi, String> colStatus;
    @FXML private TableColumn<Reservasi, LocalDateTime> colCreatedAt;

    private LapanganDAO lapanganDAO;
    private UserDAO userDAO;
    private ReservasiDAO reservasiDAO;
    private RequestPerubahanDAO requestDAO;

    @FXML
    public void initialize() {

        // Matikan tombol biru otomatis
        btnManageLapangan.setFocusTraversable(false);
        btnManageReservasi.setFocusTraversable(false);
        btnViewRequest.setFocusTraversable(false);
        btnManageUser.setFocusTraversable(false);
        btnLogout.setFocusTraversable(false);

        lapanganDAO = new LapanganDAO();
        userDAO = new UserDAO();
        reservasiDAO = new ReservasiDAO();
        requestDAO = new RequestPerubahanDAO();

        // BUTTON ACTION DENGAN PATH YANG BENAR
        btnManageLapangan.setOnAction(e ->
            SceneLoader.load("admin/KelolaLapanganView.fxml")
        );

        btnManageReservasi.setOnAction(e ->
            SceneLoader.load("admin/KelolaReservasiView.fxml")
        );

        btnViewRequest.setOnAction(e ->
            SceneLoader.load("admin/LihatRequestView.fxml")
        );

        btnManageUser.setOnAction(e ->
            SceneLoader.load("admin/KelolaUserView.fxml")
        );

        btnLogout.setOnAction(e -> {
            Session.logout();
            SceneLoader.load("LoginView.fxml");
        });

        loadDashboardStats();
        loadRecentReservations();
    }

    private void loadDashboardStats() {
        lblTotalLapangan.setText(String.valueOf(lapanganDAO.countAll()));
        lblTotalUser.setText(String.valueOf(userDAO.countAll()));
        lblTotalReservasi.setText(String.valueOf(reservasiDAO.countUpcoming()));
        lblTotalRequestPending.setText(String.valueOf(requestDAO.countPending()));
    }

    private void loadRecentReservations() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colLapanganId.setCellValueFactory(new PropertyValueFactory<>("lapanganId"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colJamMulai.setCellValueFactory(new PropertyValueFactory<>("jamMulai"));
        colJamSelesai.setCellValueFactory(new PropertyValueFactory<>("jamSelesai"));
        colTotalHarga.setCellValueFactory(new PropertyValueFactory<>("totalHarga"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        tblRecentReservasi.setItems(
                FXCollections.observableArrayList(
                        reservasiDAO.getUpcoming(10)
                )
        );
    }
}
