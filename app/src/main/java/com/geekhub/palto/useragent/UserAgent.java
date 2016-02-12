package com.geekhub.palto.useragent;

import android.content.Context;
import android.preference.PreferenceManager;

import com.vk.sdk.VKAccessToken;

/**
 * Created by duke0808 on 29.01.16.
 */
public class UserAgent {
    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }
    private String token;
    private String userId;
    private static UserAgent agent;
    public static Context appContext;

    public UserAgent(Context context) {
        this.token= PreferenceManager.getDefaultSharedPreferences(context).getString("VKAccessToken", "");
        this.userId = PreferenceManager.getDefaultSharedPreferences(context).getString("VKUserID", "");
    }

    public static UserAgent get() {
        if (agent==null){
            agent=new UserAgent(appContext);
        }
        return agent;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        UserAgent.appContext = appContext;
    }
}
