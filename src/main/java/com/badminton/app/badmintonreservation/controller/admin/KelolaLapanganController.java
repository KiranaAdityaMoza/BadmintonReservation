package com.badminton.app.badmintonreservation.controller.admin;

import com.badminton.app.badmintonreservation.dao.LapanganDAO;
import com.badminton.app.badmintonreservation.model.Lapangan;
import com.badminton.app.badmintonreservation.util.SceneLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import java.util.Optional;

public class KelolaLapanganController {

    @FXML private TableView<Lapangan> tblLapangan;
    @FXML private TableColumn<Lapangan, Integer> colId;
    @FXML private TableColumn<Lapangan, String> colNama;
    @FXML private TableColumn<Lapangan, Integer> colHarga;
    @FXML private TableColumn<Lapangan, String> colStatus;
    @FXML private TableColumn<Lapangan, String> colCreatedAt;

    @FXML private Button btnTambah;
    @FXML private Button btnEdit;
    @FXML private Button btnHapus;
    @FXML private Button btnBack;
    @FXML private HBox topHBox;

    private LapanganDAO lapanganDAO = new LapanganDAO();

    @FXML
    public void initialize() {
        // HBox padding
        topHBox.setPadding(new Insets(10,10,10,10));

        // Bind TableColumns ke property model
        colId.setCellValueFactory(c -> c.getValue().idProperty().asObject());
        colNama.setCellValueFactory(c -> c.getValue().namaProperty());
        colHarga.setCellValueFactory(c -> c.getValue().hargaPerJamProperty().asObject());
        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());
        colCreatedAt.setCellValueFactory(c -> c.getValue().createdAtProperty());

        // Load data dari DB
        loadData();

        // Tombol
        btnBack.setOnAction(e -> SceneLoader.load("admin/DashboardAdminView.fxml"));
        btnTambah.setOnAction(e -> tambahLapangan());
        btnEdit.setOnAction(e -> editLapangan());
        btnHapus.setOnAction(e -> hapusLapangan());
    }

    private void loadData() {
        tblLapangan.setItems(FXCollections.observableArrayList(lapanganDAO.getAll()));
    }

    private void tambahLapangan() {
        Lapangan temp = showLapanganDialog(new Lapangan());
        if (temp != null) {
            boolean ok = lapanganDAO.insert(temp);
            if (ok) loadData();
            else showAlert("Gagal menambahkan lapangan!");
        }
    }

    private void editLapangan() {
        Lapangan selected = tblLapangan.getSelectionModel().getSelectedItem();
        if (selected == null) { showAlert("Pilih lapangan terlebih dahulu!"); return; }

        Lapangan updated = showLapanganDialog(selected);
        if (updated != null) {
            boolean ok = lapanganDAO.update(updated);
            if (ok) loadData();
            else showAlert("Gagal mengupdate lapangan!");
        }
    }

    private void hapusLapangan() {
        Lapangan selected = tblLapangan.getSelectionModel().getSelectedItem();
        if (selected == null) { showAlert("Pilih lapangan terlebih dahulu!"); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Apakah yakin ingin menghapus lapangan ini?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean ok = lapanganDAO.delete(selected.getId());
            if (ok) loadData();
            else showAlert("Gagal menghapus lapangan!");
        }
    }

    private Lapangan showLapanganDialog(Lapangan l) {
        Dialog<Lapangan> dialog = new Dialog<>();
        dialog.setTitle("Form Lapangan");

        ButtonType okButtonType = new ButtonType("Simpan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        TextField tfNama = new TextField(l.getNamaLapangan());
        TextField tfHarga = new TextField(String.valueOf(l.getHargaPerJam()));
        TextField tfStatus = new TextField(l.getStatus());
        TextField tfFoto = new TextField(l.getFoto());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.addRow(0, new Label("Nama Lapangan:"), tfNama);
        grid.addRow(1, new Label("Harga per Jam:"), tfHarga);
        grid.addRow(2, new Label("Status:"), tfStatus);
        grid.addRow(3, new Label("Foto URL:"), tfFoto);

        GridPane.setHgrow(tfNama, Priority.ALWAYS);
        GridPane.setHgrow(tfHarga, Priority.ALWAYS);
        GridPane.setHgrow(tfStatus, Priority.ALWAYS);
        GridPane.setHgrow(tfFoto, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == okButtonType) {
                l.setNamaLapangan(tfNama.getText());
                l.setHargaPerJam(Integer.parseInt(tfHarga.getText()));
                l.setStatus(tfStatus.getText());
                l.setFoto(tfFoto.getText());
                return l;
            }
            return null;
        });

        Optional<Lapangan> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }
}
