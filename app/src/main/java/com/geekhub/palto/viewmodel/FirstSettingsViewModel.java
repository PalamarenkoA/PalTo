package com.geekhub.palto.viewmodel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.firebase.client.Firebase;
import com.geekhub.palto.R;
import com.geekhub.palto.activity.ChatListActivity;
import com.geekhub.palto.activity.FirstSettingsActivity;
import com.geekhub.palto.binding.BindableBoolean;
import com.geekhub.palto.binding.BindableString;
import com.geekhub.palto.customviews.PolToMultipleChoicePicker;
import com.geekhub.palto.useragent.UserAgent;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.model.VKApiPhoto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by duke0808 on 29.01.16.
 */
public class FirstSettingsViewModel {
    Firebase myFirebaseRef;
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
    SharedPreferences srefs;
    BindableString userId = new BindableString();
    BindableString imageUrl = new BindableString();
    BindableString nickName = new BindableString();


    public FirstSettingsViewModel(final FirstSettingsActivity activity) {
        this.activity = activity;
        srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());

        userId.set(UserAgent.get().getUserId());



        activity.binding.helloTv.setText("Привет, " + srefs.getString("VKUserNAME", "") + "!");
        Picasso.with(activity).load(srefs.getString("VKUserICON", "")).placeholder(R.drawable.imgpsh_fullsize).into(activity.binding.smallAvatarIv);
        activity.binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFirebaseRef = new Firebase("https://palto.firebaseio.com/");
                pushToFireBase("musik",activity.binding.musikInterestPicker);
                pushToFireBase("film",activity.binding.filmInterestPicker);
                pushToFireBase("growth",activity.binding.growthInterestPicker);
                pushToFireBase("eyes", activity.binding.eyesInterestPicker);
                pushToFireBase("znakom", activity.binding.znakomInterestPicker);
                myFirebaseRef.child(srefs.getString("VKUserID", "")).child("nick").setValue(activity.binding.editTextNick.getText().toString());
                SharedPreferences.Editor edit = PreferenceManager.
                        getDefaultSharedPreferences(activity.getApplicationContext()).edit();
                edit.putString("VKUserNICK",activity.binding.editTextNick.getText().toString()).apply();
                activity.startActivity(new Intent(activity, ChatListActivity.class));


            }
        });

        activity.binding.musikInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.musik));
        activity.binding.znakomInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.znakom));
        activity.binding.filmInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.film));
        activity.binding.growthInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.growth));
        activity.binding.eyesInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.eyes));

    }

    private void pushToFireBase(String name, PolToMultipleChoicePicker polToMultipleChoicePicker){
        myFirebaseRef.child(srefs.getString("VKUserID","")).child("interest").child(name).removeValue();
        for(int i = 0;i<polToMultipleChoicePicker.getInterestSet().size();i++){
            myFirebaseRef.child(srefs.getString("VKUserID", "")).child("interest").child(name).child("" + i).setValue(polToMultipleChoicePicker.getInterestSet().get(i));
        }
    }

}
