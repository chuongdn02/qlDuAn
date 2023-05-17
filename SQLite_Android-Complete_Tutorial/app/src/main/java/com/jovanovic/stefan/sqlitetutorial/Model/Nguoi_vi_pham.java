package com.jovanovic.stefan.sqlitetutorial.Model;

public class Nguoi_vi_pham {
    private int id;
    private String ho_ten;
    private String dia_chi;
    private String cccd;
    private String sdt;
    private String email;
    private String sex;
    private String nation;

    public Nguoi_vi_pham(final String ho_ten, final String dia_chi, final String cccd, final String sdt, final String email, final String sex, final String nation) {
        this.ho_ten = ho_ten;
        this.dia_chi = dia_chi;
        this.cccd = cccd;
        this.sdt = sdt;
        this.email = email;
        this.sex = sex;
        this.nation = nation;
    }

    public Nguoi_vi_pham(final int id, final String ho_ten, final String dia_chi, final String cccd, final String sdt, final String email, final String sex, final String nation) {
        this.id = id;
        this.ho_ten = ho_ten;
        this.dia_chi = dia_chi;
        this.cccd = cccd;
        this.sdt = sdt;
        this.email = email;
        this.sex = sex;
        this.nation = nation;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
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

    public String getCccd() {
        return this.cccd;
    }

    public void setCccd(final String cccd) {
        this.cccd = cccd;
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

    public String getSex() {
        return this.sex;
    }

    public void setSex(final String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return this.nation;
    }

    public void setNation(final String nation) {
        this.nation = nation;
    }
}
