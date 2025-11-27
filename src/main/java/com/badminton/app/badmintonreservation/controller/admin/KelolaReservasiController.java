package com.badminton.app.badmintonreservation.controller.admin;

import com.badminton.app.badmintonreservation.dao.ReservasiDAO;
import com.badminton.app.badmintonreservation.model.Reservasi;
import com.badminton.app.badmintonreservation.util.SceneLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.converter.LocalTimeStringConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class KelolaReservasiController {

    @FXML private TableView<Reservasi> tblReservasi;
    @FXML private TableColumn<Reservasi, Integer> colId;
    @FXML private TableColumn<Reservasi, Integer> colUserId;
    @FXML private TableColumn<Reservasi, Integer> colLapanganId;
    @FXML private TableColumn<Reservasi, LocalDate> colTanggal;
    @FXML private TableColumn<Reservasi, LocalTime> colJamMulai;
    @FXML private TableColumn<Reservasi, LocalTime> colJamSelesai;
    @FXML private TableColumn<Reservasi, Integer> colTotalHarga;
    @FXML private TableColumn<Reservasi, String> colStatus;

    @FXML private Button btnEdit;
    @FXML private Button btnHapus;
    @FXML private Button btnBack;

    private ReservasiDAO reservasiDAO = new ReservasiDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()).asObject());
        colUserId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getUserId()).asObject());
        colLapanganId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getLapanganId()).asObject());
        colTanggal.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getTanggal()));
        colJamMulai.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getJamMulai()));
        colJamSelesai.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getJamSelesai()));
        colTotalHarga.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getTotalHarga()).asObject());
        colStatus.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));

        loadData();

        btnBack.setOnAction(e -> SceneLoader.load("admin/DashboardAdminView.fxml"));
        btnEdit.setOnAction(e -> editReservasi());
        btnHapus.setOnAction(e -> hapusReservasi());
    }

    private void loadData() {
        tblReservasi.setItems(FXCollections.observableArrayList(reservasiDAO.getAll()));
    }

    private void editReservasi() {
        Reservasi selected = tblReservasi.getSelectionModel().getSelectedItem();
        if (selected == null) { showAlert("Pilih reservasi terlebih dahulu!"); return; }

        Reservasi updated = showReservasiDialog(selected);
        if (updated != null) {
            boolean ok = reservasiDAO.update(updated);
            if (ok) loadData();
            else showAlert("Gagal mengupdate reservasi!");
        }
    }

    private void hapusReservasi() {
        Reservasi selected = tblReservasi.getSelectionModel().getSelectedItem();
        if (selected == null) { showAlert("Pilih reservasi terlebih dahulu!"); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Apakah yakin ingin menghapus reservasi ini?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean ok = reservasiDAO.delete(selected.getId());
            if (ok) loadData();
            else showAlert("Gagal menghapus reservasi!");
        }
    }

    private Reservasi showReservasiDialog(Reservasi r) {
        Dialog<Reservasi> dialog = new Dialog<>();
        dialog.setTitle("Edit Reservasi");

        ButtonType okButtonType = new ButtonType("Simpan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        DatePicker dpTanggal = new DatePicker(r.getTanggal());
        TextField tfJamMulai = new TextField(r.getJamMulai().toString());
        TextField tfJamSelesai = new TextField(r.getJamSelesai().toString());
        TextField tfTotalHarga = new TextField(String.valueOf(r.getTotalHarga()));
        TextField tfStatus = new TextField(r.getStatus());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.addRow(0, new Label("Tanggal:"), dpTanggal);
        grid.addRow(1, new Label("Jam Mulai (HH:MM):"), tfJamMulai);
        grid.addRow(2, new Label("Jam Selesai (HH:MM):"), tfJamSelesai);
        grid.addRow(3, new Label("Total Harga:"), tfTotalHarga);
        grid.addRow(4, new Label("Status:"), tfStatus);

        GridPane.setHgrow(dpTanggal, javafx.scene.layout.Priority.ALWAYS);
        GridPane.setHgrow(tfJamMulai, Priority.ALWAYS);
        GridPane.setHgrow(tfJamSelesai, Priority.ALWAYS);
        GridPane.setHgrow(tfTotalHarga, Priority.ALWAYS);
        GridPane.setHgrow(tfStatus, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == okButtonType) {
                r.setTanggal(dpTanggal.getValue());
                r.setJamMulai(LocalTime.parse(tfJamMulai.getText()));
                r.setJamSelesai(LocalTime.parse(tfJamSelesai.getText()));
                r.setTotalHarga(Integer.parseInt(tfTotalHarga.getText()));
                r.setStatus(tfStatus.getText());
                return r;
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }
}
