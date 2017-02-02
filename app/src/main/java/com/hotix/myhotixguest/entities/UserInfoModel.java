package com.hotix.myhotixguest.entities;

/**
 * Created by ziedrebhi on 01/02/2017.
 */

public class UserInfoModel {
    private static final UserInfoModel holder = new UserInfoModel();
    private String Room;
    private LoginModel Users;

    public static UserInfoModel getInstance() {
        return holder;
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
