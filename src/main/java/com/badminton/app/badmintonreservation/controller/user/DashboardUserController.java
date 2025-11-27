package com.badminton.app.badmintonreservation.controller.user;

import com.badminton.app.badmintonreservation.util.Session;
import com.badminton.app.badmintonreservation.util.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DashboardUserController {

    @FXML private Button btnViewLapangan;
    @FXML private Button btnBuatReservasi;
    @FXML private Button btnReservasiSaya;
    @FXML private Button btnAjukanRequest;
    @FXML private Button btnLogout;

    @FXML private Label lblJumlahReservasiAktif;
    @FXML private TableView<?> tblMyReservasi;

    @FXML
    public void initialize() {
        btnViewLapangan.setOnAction(e -> SceneLoader.load("LapanganUserView.fxml"));
        btnBuatReservasi.setOnAction(e -> SceneLoader.load("BuatReservasiView.fxml"));
        btnReservasiSaya.setOnAction(e -> SceneLoader.load("ReservasiSayaView.fxml"));
        btnAjukanRequest.setOnAction(e -> SceneLoader.load("RequestUserView.fxml"));
        btnLogout.setOnAction(e -> {
            Session.logout();
            SceneLoader.load("LoginView.fxml");
        });

        // TODO: ambil jumlah reservasi aktif dan data reservasi user
        lblJumlahReservasiAktif.setText("2"); // contoh
    }
}
