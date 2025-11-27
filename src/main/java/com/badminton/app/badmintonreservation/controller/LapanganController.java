package com.badminton.app.badmintonreservation.controller;

import com.badminton.app.badmintonreservation.dao.LapanganDAO;
import com.badminton.app.badmintonreservation.model.Lapangan;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

import java.util.List;

public class LapanganController {

    private LapanganDAO lapanganDAO;

    @FXML private TextField txtNama;
    @FXML private TextField txtTipe;
    @FXML private TextField txtHarga;
    @FXML private TextField txtFoto;
    @FXML private TextField txtStatus;
    @FXML private TableView<Lapangan> tableLapangan;

    @FXML private Button btnTambah;
    @FXML private Button btnUpdate;
    @FXML private Button btnHapus;

    public LapanganController() {
        lapanganDAO = new LapanganDAO();
    }

    @FXML
    public void initialize() {
        loadTable();

        btnTambah.setOnAction(e -> tambah());
        btnUpdate.setOnAction(e -> update());
        btnHapus.setOnAction(e -> hapus());
    }

    private void loadTable() {
        List<Lapangan> data = lapanganDAO.getAll();
        tableLapangan.setItems(FXCollections.observableArrayList(data));
    }

    private void tambah() {
        Lapangan l = new Lapangan(
    0, // id, nanti auto-increment dari DB
    txtNama.getText(),
    Integer.parseInt(txtHarga.getText()),
    txtFoto.getText(),
    txtStatus.getText(),
    java.time.LocalDateTime.now().toString() // createdAt
);


        lapanganDAO.insert(l);
        loadTable();
    }

    private void update() {
        Lapangan selected = tableLapangan.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        selected.setNamaLapangan(txtNama.getText());
        selected.setHargaPerJam(Integer.parseInt(txtHarga.getText()));
        selected.setFoto(txtFoto.getText());
        selected.setStatus(txtStatus.getText());

        lapanganDAO.update(selected);
        loadTable();
    }

    private void hapus() {
        Lapangan selected = tableLapangan.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        lapanganDAO.delete(selected.getId());
        loadTable();
    }
}
