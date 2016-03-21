package com.geekhub.palto.viewmodel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import com.geekhub.palto.servise.MessageListener;
import com.geekhub.palto.activity.ChatListActivity;
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
        SharedPreferences srefs = PreferenceManager.getDefaultSharedPreferences(activity);
        if (UserAgent.get().getToken().length()>0){
            Intent intent;
            if(srefs.getString("VKUserFirstSettings","").length()>0){
                LogInActivity.authorized=true;
                Intent serviseIntent = new Intent(activity, MessageListener.class);
                serviseIntent.putExtra("ID", UserAgent.get().getUserId());
                activity.startService(serviseIntent);
            intent = new Intent(activity, ChatListActivity.class);
            }else{
            intent = new Intent(activity, FirstSettingsActivity.class);
           }
            activity.startActivity(intent);





        }
    }

    public void auth (View view){
        String[] myScope = new String[]{VKScope.OFFLINE, VKScope.EMAIL, VKScope.PHOTOS};
        VKSdk.login(activity, myScope);
    }
}
