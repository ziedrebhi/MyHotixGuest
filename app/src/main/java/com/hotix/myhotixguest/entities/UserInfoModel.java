package com.hotix.myhotixguest.entities;

/**
 * Created by ziedrebhi on 01/02/2017.
 */

public class UserInfoModel {
    private static final UserInfoModel holder = new UserInfoModel();
    private String Room;
    private String URL;
    private String SERVER;
    private LoginModel Users;

    public static UserInfoModel getInstance() {
        return holder;
    }

    public String getSERVER() {
        return SERVER;
    }

    public void setSERVER(String SERVER) {
        this.SERVER = SERVER;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public LoginModel getUsers() {
        return Users;
    }

    public void setUsers(LoginModel users) {
        Users = users;
    }
}
