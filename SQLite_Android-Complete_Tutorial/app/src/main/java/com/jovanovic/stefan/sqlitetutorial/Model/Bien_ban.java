package com.jovanovic.stefan.sqlitetutorial.Model;

public class Bien_ban {
    private int id;
    private String cccd;
    private String vtri;
    private String loaiXe;
    private String bienso;
    private String loaiViPham,soQuyetDinh;


    private String soTienPhat;
    private int id_can_bo;

    public Bien_ban() {
    }



    public Bien_ban(final String cccd, final String vtri, final String loaiXe, final String bienso, final String loaiViPham, final String soQuyetDinh, final String soTienPhat, final int id_can_bo) {
        this.cccd = cccd;
        this.vtri = vtri;
        this.loaiXe = loaiXe;
        this.bienso = bienso;
        this.loaiViPham = loaiViPham;
        this.soQuyetDinh = soQuyetDinh;
        this.soTienPhat = soTienPhat;
        this.id_can_bo = id_can_bo;
    }

    public Bien_ban(final int id, final String cccd, final String vtri, final String loaiXe, final String bienso, final String loaiViPham, final String soQuyetDinh, final String soTienPhat, final int id_can_bo) {
        this.id = id;
        this.cccd = cccd;
        this.vtri = vtri;
        this.loaiXe = loaiXe;
        this.bienso = bienso;
        this.loaiViPham = loaiViPham;
        this.soQuyetDinh = soQuyetDinh;
        this.soTienPhat = soTienPhat;
        this.id_can_bo = id_can_bo;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getCccd() {
        return this.cccd;
    }

    public void setCccd(final String cccd) {
        this.cccd = cccd;
    }

    public String getVtri() {
        return this.vtri;
    }

    public void setVtri(final String vtri) {
        this.vtri = vtri;
    }

    public String getLoaiXe() {
        return this.loaiXe;
    }

    public void setLoaiXe(final String loaiXe) {
        this.loaiXe = loaiXe;
    }

    public String getBienso() {
        return this.bienso;
    }

    public void setBienso(final String bienso) {
        this.bienso = bienso;
    }

    public String getLoaiViPham() {
        return this.loaiViPham;
    }

    public void setLoaiViPham(final String loaiViPham) {
        this.loaiViPham = loaiViPham;
    }

    public String getSoQuyetDinh() {
        return this.soQuyetDinh;
    }

    public void setSoQuyetDinh(final String soQuyetDinh) {
        this.soQuyetDinh = soQuyetDinh;
    }

    public String getSoTienPhat() {
        return this.soTienPhat;
    }

    public void setSoTienPhat(final String soTienPhat) {
        this.soTienPhat = soTienPhat;
    }

    public int getId_can_bo() {
        return this.id_can_bo;
    }

    public void setId_can_bo(final int id_can_bo) {
        this.id_can_bo = id_can_bo;
    }
}