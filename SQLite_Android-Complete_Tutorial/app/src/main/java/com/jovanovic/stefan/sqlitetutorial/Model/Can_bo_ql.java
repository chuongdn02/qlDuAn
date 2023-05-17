package com.jovanovic.stefan.sqlitetutorial.Model;

public class Can_bo_ql {
    private int id;
    private String name;
    private String chucvu;
    private String cccd;

    public Can_bo_ql(final int id, final String name, final String chucvu, final String cccd) {
        this.id = id;
        this.name = name;
        this.chucvu = chucvu;
        this.cccd = cccd;
    }

    public Can_bo_ql(final String name, final String chucvu, final String cccd) {
        this.name = name;
        this.chucvu = chucvu;
        this.cccd = cccd;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getChucvu() {
        return this.chucvu;
    }

    public void setChucvu(final String chucvu) {
        this.chucvu = chucvu;
    }

    public String getCccd() {
        return this.cccd;
    }

    public void setCccd(final String cccd) {
        this.cccd = cccd;
    }
}
