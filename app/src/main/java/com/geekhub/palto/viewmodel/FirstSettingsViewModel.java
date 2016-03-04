package com.geekhub.palto.viewmodel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.geekhub.palto.R;
import com.geekhub.palto.activity.ChatListActivity;
import com.geekhub.palto.activity.FirstSettingsActivity;
import com.geekhub.palto.binding.BindableString;
import com.geekhub.palto.customviews.PalToChoicePicker;
import com.geekhub.palto.customviews.PolToMultipleChoicePicker;
import com.geekhub.palto.useragent.UserAgent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
        activity.setSupportActionBar(activity.binding.toolbar);
        userId.set(UserAgent.get().getUserId());




        Picasso.with(activity).load(srefs.getString("VKUserICON", "")).placeholder(R.drawable.imgpsh_fullsize).into(activity.binding.smallAvatarIv);
        activity.binding.contentFirstSettings.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!activity.binding.contentFirstSettings.musikInterestPicker.getInterestSet().isEmpty()
                        && !activity.binding.contentFirstSettings.filmInterestPicker.getInterestSet().isEmpty()
                        && !activity.binding.contentFirstSettings.znakomInterestPicker.getInterestSet().isEmpty()
                        && !activity.binding.contentFirstSettings.growthInterestPicker.getInterestSet().isEmpty()
                        && !activity.binding.contentFirstSettings.eyesInterestPicker.getInterestSet().isEmpty()
                        && activity.binding.contentFirstSettings.editTextNick.getText().toString().length() > 2) {
                    myFirebaseRef = new Firebase("https://palto.firebaseio.com/");
                    pushToFireBase("musik", activity.binding.contentFirstSettings.musikInterestPicker);
                    pushToFireBase("film", activity.binding.contentFirstSettings.filmInterestPicker);
                    pushToFireBase("znakom", activity.binding.contentFirstSettings.znakomInterestPicker);
                    if (!activity.binding.contentFirstSettings.growthInterestPicker.getInterestSet().isEmpty()) {
                        myFirebaseRef.child(srefs.getString("VKUserID", "")).child("interest")
                                .child("growth").setValue(activity.binding.contentFirstSettings.growthInterestPicker.getInterestSet().get(0));
                    }
                    if (!activity.binding.contentFirstSettings.eyesInterestPicker.getInterestSet().isEmpty()) {
                        myFirebaseRef.child(srefs.getString("VKUserID", ""))
                                .child("interest").child("eyes").setValue(activity.binding.contentFirstSettings.eyesInterestPicker.getInterestSet().get(0));
                    }
                    myFirebaseRef.child(srefs.getString("VKUserID", ""))
                            .child("nick").setValue(activity.binding.contentFirstSettings.editTextNick.getText().toString());
                    SharedPreferences.Editor edit = PreferenceManager.
                            getDefaultSharedPreferences(activity.getApplicationContext()).edit();
                    edit.putString("VKUserFirstSettings", "true").apply();
                    edit.putString("VKUserNICK", activity.binding.contentFirstSettings.editTextNick.getText().toString()).apply();
                    activity.startActivity(new Intent(activity, ChatListActivity.class));
                    activity.finish();

                } else {
                    Toast.makeText(activity, "Заполните все полня", Toast.LENGTH_LONG).show();

                }
            }
        });
  }

    private void pushToFireBase(String name, PalToChoicePicker polToMultipleChoicePicker){
        myFirebaseRef.child(srefs.getString("VKUserID","")).child("interest").child(name).removeValue();
        for(int i = 0;i<polToMultipleChoicePicker.getInterestSet().size();i++){
            myFirebaseRef.child(srefs.getString("VKUserID", "")).child("interest").child(name).child(name + i).setValue(polToMultipleChoicePicker.getInterestSet().get(i));
        }
    }

}
