package com.example.hieule.planofstudy;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hieu le on 3/2/2018.
 */

public class SharePreManager {

    private static SharePreManager mInstance;
    private static Context mCtx;

    private static String SHARE_PRE_NAME = "mysharedpref12";
    private static String KEY_USERNAME = "username";
    private static String KEY_NAME = "name";
    private static String KEY_STUDENT_ID = "student_id";

    private SharePreManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharePreManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharePreManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(String student_id, String username, String name){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();

        editor.putString(KEY_STUDENT_ID, student_id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_USERNAME, username);

        editor.apply();

        return true;

    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USERNAME, null) != null){
            return  true;
        }
        return  false;
    }

    public boolean logOut(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return  true;
    }

    public String getOwnerName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null);
    }

    public String getUserName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getStudentID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_STUDENT_ID, null);

    }

}