package com.geekhub.palto.viewmodel;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.geekhub.palto.Helper.Helper;
import com.geekhub.palto.R;
import com.geekhub.palto.activity.ChatListActivity;
import com.geekhub.palto.activity.FirstSettingsActivity;
import com.geekhub.palto.activity.UserSettingsActivity;
import com.geekhub.palto.binding.BindableString;
import com.geekhub.palto.customviews.PolToMultipleChoicePicker;
import com.geekhub.palto.object.UserForSearch;
import com.geekhub.palto.useragent.UserAgent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by andrey on 24.02.16.
 */
public class UserSettingsViewModel {
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

    UserSettingsActivity activity;
    SharedPreferences srefs;
    BindableString userId = new BindableString();
    BindableString imageUrl = new BindableString();
    BindableString nickName = new BindableString();


    public UserSettingsViewModel (final UserSettingsActivity activity) {
        this.activity = activity;
        srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        myFirebaseRef = new Firebase("https://palto.firebaseio.com/");
        userId.set(UserAgent.get().getUserId());



        activity.binding.helloTv.setText("Привет, " + srefs.getString("VKUserNAME", "") + "!");
        Picasso.with(activity).load(srefs.getString("VKUserICON", "")).placeholder(R.drawable.imgpsh_fullsize).into(activity.binding.smallAvatarIv);
        activity.binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!activity.binding.musikInterestPicker.getInterestSet().isEmpty()
                        && !activity.binding.filmInterestPicker.getInterestSet().isEmpty()
                        && !activity.binding.znakomInterestPicker.getInterestSet().isEmpty()
                        && !activity.binding.growthInterestPicker.getInterestSet().isEmpty()
                        && !activity.binding.eyesInterestPicker.getInterestSet().isEmpty()
                        && activity.binding.editTextNick.getText().toString().length() > 2) {

                    pushToFireBase("musik", activity.binding.musikInterestPicker);
                    pushToFireBase("film", activity.binding.filmInterestPicker);
                    pushToFireBase("znakom", activity.binding.znakomInterestPicker);
                    if (!activity.binding.growthInterestPicker.getInterestSet().isEmpty()) {
                        myFirebaseRef.child(srefs.getString("VKUserID", "")).child("interest").child("growth").setValue(activity.binding.growthInterestPicker.getInterestSet().get(0));
                    }
                    if (!activity.binding.eyesInterestPicker.getInterestSet().isEmpty()) {
                        myFirebaseRef.child(srefs.getString("VKUserID", "")).child("interest").child("eyes").setValue(activity.binding.eyesInterestPicker.getInterestSet().get(0));
                    }
                    myFirebaseRef.child(srefs.getString("VKUserID", "")).child("nick").setValue(activity.binding.editTextNick.getText().toString());
                    SharedPreferences.Editor edit = PreferenceManager.
                            getDefaultSharedPreferences(activity.getApplicationContext()).edit();
                    edit.putString("VKUserFirstSettings", "true").apply();
                    edit.putString("VKUserNICK", activity.binding.editTextNick.getText().toString()).apply();
                    activity.startActivity(new Intent(activity, ChatListActivity.class));

                } else {
                    Toast.makeText(activity, "Заполните все полня", Toast.LENGTH_LONG).show();

                }
            }
        });

        activity.binding.musikInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.musik));
        activity.binding.znakomInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.znakom));
        activity.binding.filmInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.film));
        activity.binding.growthInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.growth));
        activity.binding.eyesInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.eyes));
            myFirebaseRef.child(srefs.getString("VKUserID", "")).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserForSearch userForSearch = dataSnapshot.getValue(UserForSearch.class);
                    Helper helper = new Helper();
                    activity.binding.musikInterestPicker
                            .setInterest(helper.createList(userForSearch.getInterest(), 1));
                    activity.binding.filmInterestPicker
                            .setInterest(helper.createList(userForSearch.getInterest(), 0));
                    activity.binding.znakomInterestPicker
                            .setInterest(helper.createList(userForSearch.getInterest(), 2));
                    ArrayList<String> arrayList = new ArrayList();
                    arrayList.add(userForSearch.getInterest().getGrowth());
                    activity.binding.growthInterestPicker.setInterest(arrayList);
                    ArrayList<String> arrayList1 = new ArrayList();
                    arrayList1.add(userForSearch.getInterest().getEyes());
                    activity.binding.eyesInterestPicker.setInterest(arrayList1);
                    activity.binding.editTextNick.setText(userForSearch.getNick());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

    }

    private void pushToFireBase(String name, PolToMultipleChoicePicker polToMultipleChoicePicker){
        myFirebaseRef.child(srefs.getString("VKUserID","")).child("interest").child(name).removeValue();
        for(int i = 0;i<polToMultipleChoicePicker.getInterestSet().size();i++){
            myFirebaseRef.child(srefs.getString("VKUserID", "")).child("interest").child(name).child(name + i).setValue(polToMultipleChoicePicker.getInterestSet().get(i));
        }
    }

}
