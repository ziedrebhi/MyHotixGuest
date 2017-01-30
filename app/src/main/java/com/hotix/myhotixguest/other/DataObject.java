package com.hotix.myhotixguest.other;

/**
 * Created by ziedrebhi on 30/01/2017.
 */

public class DataObject {
    private String mText1;
    private String mText2;
    private boolean mBool;

    public DataObject(String text1, String text2) {
        mText1 = text1;
        mText2 = text2;
    }

    public DataObject(String text1, String text2, boolean mActive) {
        mText1 = text1;
        mText2 = text2;
        mBool = mActive;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }

    public boolean ismBool() {
        return mBool;
    }

    public void setmBool(boolean mBool) {
        this.mBool = mBool;
    }
}