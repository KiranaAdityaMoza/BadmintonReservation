package com.badminton.app.badmintonreservation.controller.admin;

import com.badminton.app.badmintonreservation.dao.RequestPerubahanDAO;
import com.badminton.app.badmintonreservation.dao.ReservasiDAO;
import com.badminton.app.badmintonreservation.model.RequestPerubahan;
import com.badminton.app.badmintonreservation.util.SceneLoader;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LihatRequestController {

    @FXML private TableView<RequestPerubahan> tblRequest;

    @FXML private TableColumn<RequestPerubahan, Integer> colId;
    @FXML private TableColumn<RequestPerubahan, Integer> colReservasiId;
    @FXML private TableColumn<RequestPerubahan, String> colJenis;
    @FXML private TableColumn<RequestPerubahan, String> colAlasan;
    @FXML private TableColumn<RequestPerubahan, String> colStatus;

    @FXML private TableColumn<RequestPerubahan, Object> colTanggalBaru;
    @FXML private TableColumn<RequestPerubahan, Object> colJamMulai;
    @FXML private TableColumn<RequestPerubahan, Object> colJamSelesai;

    @FXML private Button btnApprove;
    @FXML private Button btnReject;
    @FXML private Button btnBack;

    private RequestPerubahanDAO requestDAO = new RequestPerubahanDAO();
    private ReservasiDAO reservasiDAO = new ReservasiDAO();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()).asObject());
        colReservasiId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getIdReservasi()).asObject());
        colJenis.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getJenis()));
        colAlasan.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAlasan()));
        colStatus.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));

        colTanggalBaru.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getTanggalBaru()));
        colJamMulai.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getJamMulaiBaru()));
        colJamSelesai.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getJamSelesaiBaru()));

        loadTable();

        btnApprove.setOnAction(e -> approveRequest());
        btnReject.setOnAction(e -> rejectRequest());
        btnBack.setOnAction(e -> SceneLoader.load("admin/DashboardAdminView.fxml"));

    }

    private void loadTable() {
        tblRequest.setItems(FXCollections.observableArrayList(requestDAO.getPendingRequests()));
    }

    private void approveRequest() {
        RequestPerubahan req = tblRequest.getSelectionModel().getSelectedItem();

        if (req == null) {
            show("Pilih request dulu!");
            return;
        }

        // Jika jenisnya reschedule → update tanggal & jam reservasi
        if (req.getJenis().equals("reschedule")) {
            reservasiDAO.updateDateTime(
                    req.getIdReservasi(),
                    req.getTanggalBaru(),
                    req.getJamMulaiBaru(),
                    req.getJamSelesaiBaru()
            );
        }

        // Jika cancel → update status reservasi
        if (req.getJenis().equals("cancel")) {
            reservasiDAO.updateStatus(req.getIdReservasi(), "cancelled");
        }

        requestDAO.approveRequest(req.getId());

        show("Request disetujui!");
        loadTable();
    }

    private void rejectRequest() {
        RequestPerubahan req = tblRequest.getSelectionModel().getSelectedItem();

        if (req == null) {
            show("Pilih request dulu!");
            return;
        }

        requestDAO.rejectRequest(req.getId());

        show("Request ditolak!");
        loadTable();
    }
    
    private void show(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.show();
    }
}
