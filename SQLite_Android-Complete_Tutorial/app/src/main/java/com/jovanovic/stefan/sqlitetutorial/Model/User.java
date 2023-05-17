package com.jovanovic.stefan.sqlitetutorial.Model;

public class User {
    private int id;
    private String cccd;
    private String Name;
    private String Sex;
    private String Nation;

    public User(){

    }

    public User(final String cccd, final String name, final String sex, final String nation) {
        this.cccd = cccd;
        this.Name = name;
        this.Sex = sex;
        this.Nation = nation;
    }

    public User(final int id, final String cccd, final String name, final String sex, final String nation) {
        this.id = id;
        this.cccd = cccd;
        this.Name = name;
        this.Sex = sex;
        this.Nation = nation;
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

    public String getName() {
        return this.Name;
    }

    public void setName(final String name) {
        this.Name = name;
    }

    public String getSex() {
        return this.Sex;
    }

    public void setSex(final String sex) {
        this.Sex = sex;
    }

    public String getNation() {
        return this.Nation;
    }

    public void setNation(final String nation) {
        this.Nation = nation;
    }
}
