package com.badminton.app.badmintonreservation.controller;

import com.badminton.app.badmintonreservation.dao.RequestPerubahanDAO;
import com.badminton.app.badmintonreservation.model.RequestPerubahan;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class RequestPerubahanController {

    private RequestPerubahanDAO requestDAO;

    @FXML private DatePicker dateTanggal;
    @FXML private TextField txtMulai;
    @FXML private TextField txtSelesai;
    @FXML private TextField txtAlasan;

    @FXML private Button btnKirim;

    public RequestPerubahanController() {
        requestDAO = new RequestPerubahanDAO();
    }

    @FXML
    public void initialize() {
        btnKirim.setOnAction(e -> kirim());
    }

    private void kirim() {

        LocalDate tanggal = dateTanggal.getValue();
        LocalTime mulai = LocalTime.parse(txtMulai.getText());
        LocalTime selesai = LocalTime.parse(txtSelesai.getText());

        RequestPerubahan r = new RequestPerubahan(
                0,
                1, // dummy id reservasi
                "UBAH_JADWAL",
                tanggal,
                mulai,
                selesai,
                txtAlasan.getText(),
                "PENDING"
        );

        requestDAO.insert(r);
    }
}
