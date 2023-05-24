package com.jovanovic.stefan.sqlitetutorial.Model;


public class Tai_khoan {
    private int id;
    private String username;
    private String password;
    private String cccd,ho_ten,dia_chi,sdt,email,gioi_tinh,quoc_gia,chuc_vu;

    public Tai_khoan(final int id, final String cccd, final String ho_ten, final String dia_chi, final String sdt, final String email, final String gioi_tinh, final String quoc_gia) {
        this.id = id;
        this.cccd = cccd;
        this.ho_ten = ho_ten;
        this.dia_chi = dia_chi;
        this.sdt = sdt;
        this.email = email;
        this.gioi_tinh = gioi_tinh;
        this.quoc_gia = quoc_gia;
    }

    public Tai_khoan(final String username, final String password, final String cccd, final String ho_ten, final String dia_chi, final String sdt, final String email, final String gioi_tinh, final String quoc_gia, final String chuc_vu, final String bacTK) {
        this.username = username;
        this.password = password;
        this.cccd = cccd;
        this.ho_ten = ho_ten;
        this.dia_chi = dia_chi;
        this.sdt = sdt;
        this.email = email;
        this.gioi_tinh = gioi_tinh;
        this.quoc_gia = quoc_gia;
        this.chuc_vu = chuc_vu;
        this.BacTK = bacTK;
    }


    public Tai_khoan(final int id, final String cccd, final String ho_ten, final String dia_chi, final String sdt, final String email, final String gioi_tinh, final String quoc_gia, final String chuc_vu) {
        this.id = id;
        this.cccd = cccd;
        this.ho_ten = ho_ten;
        this.dia_chi = dia_chi;
        this.sdt = sdt;
        this.email = email;
        this.gioi_tinh = gioi_tinh;
        this.quoc_gia = quoc_gia;
        this.chuc_vu = chuc_vu;
    }

    public Tai_khoan(final String cccd, final String ho_ten, final String dia_chi, final String sdt, final String email, final String gioi_tinh) {
        this.cccd = cccd;
        this.ho_ten = ho_ten;
        this.dia_chi = dia_chi;
        this.sdt = sdt;
        this.email = email;
        this.gioi_tinh = gioi_tinh;
    }

    public String getHo_ten() {
        return this.ho_ten;
    }

    public void setHo_ten(final String ho_ten) {
        this.ho_ten = ho_ten;
    }

    public String getDia_chi() {
        return this.dia_chi;
    }

    public void setDia_chi(final String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public String getSdt() {
        return this.sdt;
    }

    public void setSdt(final String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getGioi_tinh() {
        return this.gioi_tinh;
    }

    public void setGioi_tinh(final String gioi_tinh) {
        this.gioi_tinh = gioi_tinh;
    }

    public String getQuoc_gia() {
        return this.quoc_gia;
    }

    public void setQuoc_gia(final String quoc_gia) {
        this.quoc_gia = quoc_gia;
    }

    public String getChuc_vu() {
        return this.chuc_vu;
    }

    public void setChuc_vu(final String chuc_vu) {
        this.chuc_vu = chuc_vu;
    }

    private String BacTK;


    public Tai_khoan() {
    }
    public Tai_khoan(int id, String username, String password, String cccd, String BacTK) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cccd = cccd;
        this.BacTK= BacTK;
    }

    public String getBacTK() {
        return this.BacTK;
    }

    public void setBacTK(final String bacTK) {
        this.BacTK = bacTK;
    }

    public Tai_khoan(String username, String password, String cccd, String BacTK) {
        this.username = username;
        this.password = password;
        this.cccd = cccd;
        this.BacTK= BacTK;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
}
