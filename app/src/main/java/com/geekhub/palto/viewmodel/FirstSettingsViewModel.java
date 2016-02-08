package com.geekhub.palto.viewmodel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.geekhub.palto.R;
import com.geekhub.palto.activity.ChatListActivity;
import com.geekhub.palto.activity.FirstSettingsActivity;
import com.geekhub.palto.binding.BindableBoolean;
import com.geekhub.palto.binding.BindableString;
import com.geekhub.palto.useragent.UserAgent;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.model.VKApiPhoto;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by duke0808 on 29.01.16.
 */
public class FirstSettingsViewModel {
    public BindableString getUserId() {
        return userId;
    }

    public void setUserId(BindableString userId) {
        this.userId = userId;
    }

    public BindableString getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(BindableString imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BindableString getNickName() {
        return nickName;
    }

    public void setNickName(BindableString nickName) {
        this.nickName = nickName;
    }

    FirstSettingsActivity activity;
    BindableString userId = new BindableString();
    BindableString imageUrl = new BindableString();
    BindableString nickName = new BindableString();
    HashMap<String, Boolean> interestMap;

    public FirstSettingsViewModel(final FirstSettingsActivity activity) {
        this.activity = activity;
        SharedPreferences srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());

        userId.set(UserAgent.get(activity).getUserId());
        ArrayList<String> testList = new ArrayList<>();
        testList.add("Interest 1");
        testList.add("Auto");
        testList.add("Auto2");
        testList.add("Sex");
        testList.add("Sex2");
        testList.add("Bowling");
        testList.add("Bowling2");
        testList.add("Cats and Dogs");
        testList.add("Cats and Dogs2");
        activity.binding.helloTv.setText("Привет, " + srefs.getString("VKUserNAME", ""));
        Picasso.with(activity).load(srefs.getString("VKUserICON","")).into(activity.binding.smallAvatarIv);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(activity, R.array.animals,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity.binding.spinner.setAdapter(adapter);
        activity.binding.spinner2.setAdapter(adapter);
        activity.binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity,ChatListActivity.class));
            }
        });
    }



}
