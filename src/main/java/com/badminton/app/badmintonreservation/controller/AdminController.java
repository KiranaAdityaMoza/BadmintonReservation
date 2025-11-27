package com.badminton.app.badmintonreservation.controller;

public class AdminController {

    private LapanganController lapanganController;
    private ReservasiController reservasiController;
    private RequestPerubahanController requestController;

    public AdminController() {
        lapanganController = new LapanganController();
        reservasiController = new ReservasiController();
        requestController = new RequestPerubahanController();
    }

    public LapanganController lapangan() { return lapanganController; }
    public ReservasiController reservasi() { return reservasiController; }
    public RequestPerubahanController request() { return requestController; }
}
