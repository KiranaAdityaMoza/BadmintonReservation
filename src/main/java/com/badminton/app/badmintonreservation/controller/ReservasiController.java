package com.badminton.app.badmintonreservation.controller;

import com.badminton.app.badmintonreservation.dao.ReservasiDAO;
import com.badminton.app.badmintonreservation.dao.LapanganDAO;
import com.badminton.app.badmintonreservation.model.Reservasi;
import com.badminton.app.badmintonreservation.model.Lapangan;
import com.badminton.app.badmintonreservation.util.Session;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.util.List;

public class ReservasiController {

    private ReservasiDAO reservasiDAO;
    private LapanganDAO lapanganDAO;

    @FXML private DatePicker dateTanggal;
    @FXML private TextField txtMulai;
    @FXML private TextField txtSelesai;
    @FXML private ComboBox<Lapangan> comboLapangan;
    @FXML private Button btnSubmit;

    public ReservasiController() {
        reservasiDAO = new ReservasiDAO();
        lapanganDAO = new LapanganDAO();
    }

    @FXML
    public void initialize() {
        loadLapangan();
        btnSubmit.setOnAction(e -> buatReservasi());
    }

    private void loadLapangan() {
        List<Lapangan> list = lapanganDAO.getAll();
        comboLapangan.getItems().setAll(list);
    }

    private void buatReservasi() {

        Lapangan lp = comboLapangan.getValue();
        if (lp == null) {
            System.out.println("Pilih lapangan dulu!");
            return;
        }

        LocalTime mulai = LocalTime.parse(txtMulai.getText());
        LocalTime selesai = LocalTime.parse(txtSelesai.getText());

        int durasi = (int) Duration.between(mulai, selesai).toHours();
        int total = durasi * lp.getHargaPerJam();

        Reservasi r = new Reservasi(
                0,
                Session.getCurrentUser().getId(),
                lp.getId(),
                dateTanggal.getValue(),
                mulai,
                selesai,
                total,
                "AKTIF"
        );

        reservasiDAO.insert(r);
    }
}
