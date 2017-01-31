package com.hotix.myhotixguest.entities;

/**
 * Created by ziedrebhi on 31/01/2017.
 */

public class LoginModel {
    String data;
    Boolean status;

    public LoginModel() {
    }

    public LoginModel(String data, Boolean status) {
        this.data = data;
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "data='" + data + '\'' +
                ", status=" + status +
                '}';
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
