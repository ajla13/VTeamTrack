package com.ul.lj.si.vteamtrack;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class PreferenceData {

    static final String PREF_LOGGEDIN_USER_EMAIL = "logged_in_email";
    static final String PREF_USER_LOGGEDIN_STATUS = "logged_in_status";
    static final String PREF_USER_LOGGEDIN = "logged_in_user";
    static final String PREF_USER_ROLE = "main_user_role";
    static final String PREF_TEAM = "team";
    static final String PREF_POST_ID = "postId";
    static final String PREF_TRAINING_EX="pref_training_expiration";
    static final String PREF_GAME_EX="pref_game_expiration";
    static final String PREF_PUBLIC_TEAM="pref_public_team";
    public static SharedPreferences getSharedPreferences(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setLoggedInUserEmail(Context ctx, String email)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOGGEDIN_USER_EMAIL, email);
        editor.commit();
    }
    public static void setPublicTeam(Context ctx, Boolean value)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_PUBLIC_TEAM, value);
        editor.commit();
    }
    public static void setCurentPostId(Context ctx, int id)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_POST_ID, id);
        editor.commit();
    }
    public static void setTrainingEx(Context ctx, boolean toggle)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_TRAINING_EX, toggle);
        editor.commit();
    }
    public static void setGameEx(Context ctx, boolean toggle)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_GAME_EX, toggle);
        editor.commit();
    }
    public static void setLoggedInUserRole(Context ctx, String role)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_ROLE, role);
        editor.commit();
    }
    public static void setLoggedInUser(Context ctx, int userId)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_USER_LOGGEDIN, userId);
        editor.commit();
    }
    public static void setTeam(Context ctx, String team)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TEAM, team);
        editor.commit();
    }

    public static String getLoggedInEmailUser(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_LOGGEDIN_USER_EMAIL, "");
    }
    public static String getUserRole(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_ROLE, "");
    }

    public static Boolean getPrefPublicTeam(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(PREF_PUBLIC_TEAM,true);
    }

    public static String getTeam(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_TEAM, "");
    }
    public static int getLoggedInUser(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF_USER_LOGGEDIN, 0);
    }
    public static boolean getTrainingPref(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_TRAINING_EX, false);
    }
    public static boolean getGamePref(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_GAME_EX, false);
    }
    public static int getPostId(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF_POST_ID, 0);
    }

    public static void setUserLoggedInStatus(Context ctx, boolean status)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_USER_LOGGEDIN_STATUS, status);
        editor.commit();
    }

    public static boolean getUserLoggedInStatus(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_USER_LOGGEDIN_STATUS, false);
    }

    public static void clearLoggedInUser(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_USER_LOGGEDIN);
        editor.remove(PREF_LOGGEDIN_USER_EMAIL);
        editor.remove(PREF_USER_ROLE);
        editor.remove(PREF_USER_LOGGEDIN_STATUS);
        editor.remove(PREF_TEAM);
        editor.commit();
    }
}
