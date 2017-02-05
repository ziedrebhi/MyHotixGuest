package com.hotix.myhotixguest.other;

import com.hotix.myhotixguest.entities.UserInfoModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ziedrebhi on 05/02/2017.
 */

public class Utils {

    public int YEAR;
    public int MONTH;
    public int DAY;

    public int HOUR;
    public int MINUTE;

    public int YEARarr;
    public int MONTHarr;
    public int DAYarr;

    public int YEARdep;
    public int MONTHdep;
    public int DAYdep;

    public Utils() throws ParseException {

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        YEAR = cal.get(Calendar.YEAR);
        MONTH = cal.get(Calendar.MONTH);
        DAY = cal.get(Calendar.DAY_OF_MONTH);
        HOUR = cal.get(Calendar.HOUR);
        MINUTE = cal.get(Calendar.MINUTE);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        String datearr = UserInfoModel.getInstance().getUsers().getData().get(0).getDateArrivee();
        String datedep = UserInfoModel.getInstance().getUsers().getData().get(0).getDateDepart();

        Date dateArr = formatter.parse(datearr);
        Date dateDep = formatter.parse(datedep);

        Calendar calArr = Calendar.getInstance();
        cal.setTime(dateArr);

        Calendar calDep = Calendar.getInstance();
        cal.setTime(dateDep);

        YEARarr = calArr.get(Calendar.YEAR);
        YEARdep = calDep.get(Calendar.YEAR);

        MONTHarr = calArr.get(Calendar.MONTH);
        MONTHdep = calDep.get(Calendar.MONTH);

        DAYarr = calArr.get(Calendar.DAY_OF_MONTH);
        DAYdep = calDep.get(Calendar.DAY_OF_MONTH);
    }

    public int getYEARarr() {
        return YEARarr;
    }

    public void setYEARarr(int YEARarr) {
        this.YEARarr = YEARarr;
    }

    public int getMONTHarr() {
        return MONTHarr;
    }

    public void setMONTHarr(int MONTHarr) {
        this.MONTHarr = MONTHarr;
    }

    public int getDAYarr() {
        return DAYarr;
    }

    public void setDAYarr(int DAYarr) {
        this.DAYarr = DAYarr;
    }

    public int getYEARdep() {
        return YEARdep;
    }

    public void setYEARdep(int YEARdep) {
        this.YEARdep = YEARdep;
    }

    public int getMONTHdep() {
        return MONTHdep;
    }

    public void setMONTHdep(int MONTHdep) {
        this.MONTHdep = MONTHdep;
    }

    public int getDAYdep() {
        return DAYdep;
    }

    public void setDAYdep(int DAYdep) {
        this.DAYdep = DAYdep;
    }

    public int getHOUR() {
        return HOUR;
    }

    public void setHOUR(int HOUR) {
        this.HOUR = HOUR;
    }

    public int getMINUTE() {
        return MINUTE;
    }

    public void setMINUTE(int MINUTE) {
        this.MINUTE = MINUTE;
    }

    public int getYEAR() {
        return YEAR;
    }

    public void setYEAR(int YEAR) {
        this.YEAR = YEAR;
    }

    public int getMONTH() {
        return MONTH;
    }

    public void setMONTH(int MONTH) {
        this.MONTH = MONTH;
    }

    public int getDAY() {
        return DAY;
    }

    public void setDAY(int DAY) {
        this.DAY = DAY;
    }
}
