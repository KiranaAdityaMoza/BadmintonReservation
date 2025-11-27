package com.badminton.app.badmintonreservation.model;

import javafx.beans.property.*;

public class User {

    // ==== PROPERTY untuk TableView ====
    private IntegerProperty idProperty = new SimpleIntegerProperty();
    private StringProperty usernameProperty = new SimpleStringProperty();
    private StringProperty namaLengkapProperty = new SimpleStringProperty();
    private StringProperty roleProperty = new SimpleStringProperty();

    // ==== FIELD biasa (untuk login) ====
    private String password; // password tidak ditampilkan di tabel

    // ==== CONSTRUCTOR ====
    public User() {}

    public User(int id, String username, String password, String role, String namaLengkap) {
        this.idProperty.set(id);
        this.usernameProperty.set(username);
        this.password = password;
        this.roleProperty.set(role);
        this.namaLengkapProperty.set(namaLengkap);
    }

    // ==== GETTER & SETTER BIASA (AMAN untuk LOGIN) ====
    public int getId() { return idProperty.get(); }
    public void setId(int id) { this.idProperty.set(id); }

    public String getUsername() { return usernameProperty.get(); }
    public void setUsername(String username) { this.usernameProperty.set(username); }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return roleProperty.get(); }
    public void setRole(String role) { this.roleProperty.set(role); }

    public String getNamaLengkap() { return namaLengkapProperty.get(); }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkapProperty.set(namaLengkap); }

    // ==== PROPERTY untuk TableView ====
    public IntegerProperty idProperty() { return idProperty; }
    public StringProperty usernameProperty() { return usernameProperty; }
    public StringProperty namaLengkapProperty() { return namaLengkapProperty; }
    public StringProperty roleProperty() { return roleProperty; }
}
