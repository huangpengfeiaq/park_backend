package com.springboot.framework.controller.request;

public class AdminInsertForParkId {
    private String account;
    private String password;
    private String phone;
    private String name;
    private Integer parkId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParkId() {
        return parkId;
    }

    public void setParkId(Integer parkId) {
        this.parkId = parkId;
    }
}