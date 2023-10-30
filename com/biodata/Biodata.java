package com.biodata;

import java.util.ArrayList;

/**
 *
 * @author Ilham
 */

public class Biodata {
    private String nama;
    private String jenisKelamin;
    private String nomorHP;
    private String alamat;

    private String[] columnNames = {"Nama","Nomor","Jenis Kelamin", "Alamat"};
    private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
    
    public Biodata(String nama, String jenisKelamin, String nomorHP, String alamat) {
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.nomorHP = nomorHP;
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNomorHP() {
        return nomorHP;
    }

    public void setNomorHP(String nomorHP) {
        this.nomorHP = nomorHP;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}