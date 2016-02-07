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

    public UserAgent(Context context) {
        this.token= PreferenceManager.getDefaultSharedPreferences(context).getString("VKAccessToken", "");
        this.userId = PreferenceManager.getDefaultSharedPreferences(context).getString("VKUserID", "");
    }

    public static UserAgent get(Context context) {
        if (agent==null){
            agent=new UserAgent(context);
        }
        return agent;
    }
}
