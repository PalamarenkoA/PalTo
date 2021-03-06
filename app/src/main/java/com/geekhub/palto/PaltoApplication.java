package com.geekhub.palto;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.firebase.client.Firebase;
import com.geekhub.palto.useragent.UserAgent;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import io.fabric.sdk.android.Fabric;

/**
 * Created by duke0808 on 29.01.16.
 */
public class PaltoApplication extends Application {
    public  static Context CONTEXT;
    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                // VKAccessToken is invalid
                SharedPreferences.Editor edit = PreferenceManager.
                        getDefaultSharedPreferences(getApplicationContext()).edit();
                edit.putString("VKAccessToken", "").apply();
                edit.putString("VKUserID", "").apply();
            }
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        CONTEXT = this;
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
        UserAgent.appContext=this;
    }
}
