package com.example.sinon.bursakerjakhusussmk.Model;

public class RiwayatLowongan {
    private String Nama;
    private String Asal_Sekolah;
    private String Jurusan;
    private String Tahun_kelulusan;
    private String Email;
    private String Nomor_ponsel;
    private String Nama_lowongan;
    private String Perusahaan;
    private String Berlaku;
    private String Status;
    private String Pesan;

    public RiwayatLowongan() {
    }

    public RiwayatLowongan(String nama, String asal_Sekolah, String jurusan, String tahun_kelulusan, String email, String nomor_ponsel, String nama_lowongan, String perusahaan, String berlaku, String status, String pesan) {
        Nama = nama;
        Asal_Sekolah = asal_Sekolah;
        Jurusan = jurusan;
        Tahun_kelulusan = tahun_kelulusan;
        Email = email;
        Nomor_ponsel = nomor_ponsel;
        Nama_lowongan = nama_lowongan;
        Perusahaan = perusahaan;
        Berlaku = berlaku;
        Status = status;
        Pesan = pesan;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPesan() {
        return Pesan;
    }

    public void setPesan(String pesan) {
        Pesan = pesan;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getAsal_Sekolah() {
        return Asal_Sekolah;
    }

    public void setAsal_Sekolah(String asal_Sekolah) {
        Asal_Sekolah = asal_Sekolah;
    }

    public String getJurusan() {
        return Jurusan;
    }

    public void setJurusan(String jurusan) {
        Jurusan = jurusan;
    }

    public String getTahun_kelulusan() {
        return Tahun_kelulusan;
    }

    public void setTahun_kelulusan(String tahun_kelulusan) {
        Tahun_kelulusan = tahun_kelulusan;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNomor_ponsel() {
        return Nomor_ponsel;
    }

    public void setNomor_ponsel(String nomor_ponsel) {
        Nomor_ponsel = nomor_ponsel;
    }

    public String getNama_lowongan() {
        return Nama_lowongan;
    }

    public void setNama_lowongan(String nama_lowongan) {
        Nama_lowongan = nama_lowongan;
    }

    public String getPerusahaan() {
        return Perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        Perusahaan = perusahaan;
    }

    public String getBerlaku() {
        return Berlaku;
    }

    public void setBerlaku(String berlaku) {
        Berlaku = berlaku;
    }
}
