package com.ibreathe.ibreathe_v2.global;

/**
 * Created by aloknerurkar on 6/28/15.
 */


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.facebook.AccessToken;
import com.ibreathe.ibreathe_v2.login.LoginActivity;
import com.ibreathe.ibreathe_v2.models.UserType;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "iBreathe";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_REGISTERED = "IsRegistered";
    private static final String FB_LOGIN = "FBLoggedIn";
    private static final String GPLUS_LOGIN = "GPlusLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String KEY_FBNAME = "fb_name";
    public static final String KEY_FBEMAIL = "fb_email";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String FB_ACCESS = "fb_access";
    public static final String KEY_ORG_ID = "org_id";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_GCM_ID = "gcm_id";
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    /**
     * Methods to get and update access token for
     * Facebook login
     * */

    public void put_fb_access(AccessToken accessToken){
        editor.putString(FB_ACCESS,accessToken.toString());
        editor.putBoolean(FB_LOGIN,true);
        editor.apply();

    }

    public void put_fb_info(String name, String email){

            // Storing name in pref
            editor.putString(KEY_FBNAME, name);

            // Storing email in pref
            editor.putString(KEY_FBEMAIL, email);

            // commit changes
            editor.commit();
    }

    public void put_user_info(UserType user){

        // Storing name in pref
        editor.putString(KEY_NAME, user.user_name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, user.email);
        editor.putString(KEY_MOBILE, user.mobile);
        editor.putString(KEY_ORG_ID, user.org_id);
        editor.putString(KEY_GCM_ID, user.gcm_reg_id);
        editor.putBoolean(IS_REGISTERED,true);
        editor.putBoolean(IS_LOGIN,true);

        // commit changes
        editor.commit();
    }

    public UserType get_user_info(){

        UserType user = new UserType();
        user.user_name = pref.getString(KEY_NAME,null);
        user.email = pref.getString(KEY_EMAIL,null);
        user.mobile = pref.getString(KEY_MOBILE,null);

        return user;
    }

    public String get_fb_access(){
        return pref.getString(FB_ACCESS,null);
    }

    public boolean isFBlogin(){
        return pref.getBoolean(FB_LOGIN,false);
    }

    public HashMap<String, String> getFBDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_FBNAME, pref.getString(KEY_FBNAME, null));

        // user email id
        user.put(KEY_FBEMAIL, pref.getString(KEY_FBEMAIL, null));

        // return user
        return user;
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    public boolean isRegistered(){
        return pref.getBoolean(IS_REGISTERED, false);
    }
}