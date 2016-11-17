package com.example.dimka.currencyconverter;


import android.content.Context;
import android.content.SharedPreferences;

public class ChangeStyles {

    private static Context context;
    private int typeOfStyle;
    final static String SAVED_POST = "saved_post";
    private static SharedPreferences preferences;

    public void setPosition(int position) {
        if(position >= 0){
            preferences = context.getSharedPreferences(SAVED_POST, 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(SAVED_POST, position);
            editor.apply();
        }
    }

    public int getPosition() {
        int temp = 1;
        int post;
        preferences = context.getSharedPreferences(SAVED_POST, 0);
        post = preferences.getInt(SAVED_POST, temp);
        return post;
    }

    public int styleType () {
        int post = getPosition();
        switch (post){
            case 0:
                typeOfStyle = R.style.AppBlue;
                break;
            case 1:
                typeOfStyle = R.style.AppGreen;
                break;
            case 2:
                typeOfStyle = R.style.AppOrange;
                break;
        }
        return typeOfStyle;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
