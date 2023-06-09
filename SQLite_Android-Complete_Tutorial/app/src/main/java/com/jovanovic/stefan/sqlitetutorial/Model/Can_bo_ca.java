package com.jovanovic.stefan.sqlitetutorial.Model;

public class Can_bo_ca {
    private int id;
    private String username;
    private String password;
    private String cccd;

    private String BacTK;

    private String ten_can_bo;

    private String chuc_vu;


    public Can_bo_ca() {
    }
    public Can_bo_ca(int id, String username, String password, String cccd, String BacTK) {
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

    public Can_bo_ca(String username, String password, String cccd, String BacTK) {
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
