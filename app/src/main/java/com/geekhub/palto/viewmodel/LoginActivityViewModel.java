package com.geekhub.palto.viewmodel;

import android.content.Intent;
import android.view.View;

import com.geekhub.palto.activity.FirstSettingsActivity;
import com.geekhub.palto.activity.LogInActivity;
import com.geekhub.palto.useragent.UserAgent;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

/**
 * Created by duke0808 on 25.01.16.
 */
public class LoginActivityViewModel {
    LogInActivity activity;

    public LoginActivityViewModel(LogInActivity activity) {
        this.activity = activity;
        if (UserAgent.get(activity).getToken().length()>0){
            Intent intent = new Intent(activity, FirstSettingsActivity.class);
            activity.startActivity(intent);
        }
    }

    public void auth (View view){
        String[] myScope = new String[]{VKScope.OFFLINE, VKScope.EMAIL, VKScope.PHOTOS};
        VKSdk.login(activity, myScope);
    }
}
