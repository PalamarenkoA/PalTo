package com.geekhub.palto.viewmodel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.geekhub.palto.binding.BindableString;
import com.geekhub.palto.customviews.PalToChoicePicker;
import com.geekhub.palto.customviews.PolToMultipleChoicePicker;
import com.geekhub.palto.object.Item;
import com.geekhub.palto.object.UserForSearch;
import com.geekhub.palto.object.VKCItiesResponse;
import com.geekhub.palto.useragent.UserAgent;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by duke0808 on 29.01.16.
 */
public class FirstSettingsViewModel {
    Firebase myFirebaseRef;
    String[] strings;
    ArrayList<Integer> id;
    int city = 0;
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

    private void setInterest (){
        myFirebaseRef.child(srefs.getString("VKUserID", "")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserForSearch userForSearch = dataSnapshot.getValue(UserForSearch.class);
                Helper helper = new Helper();
                if (userForSearch.getInterest() != null ) {
                    if(userForSearch.getInterest().getMusik() != null){
                    activity.binding.contentFirstSettings.musikInterestPicker
                            .setInterest(helper.createList(userForSearch.getInterest(), 1));}
                    if(userForSearch.getInterest().getFilm() != null){
                    activity.binding.contentFirstSettings.filmInterestPicker
                            .setInterest(helper.createList(userForSearch.getInterest(), 0));}
                    if(userForSearch.getInterest().getZnakom() != null){
                    activity.binding.contentFirstSettings.znakomInterestPicker
                            .setInterest(helper.createList(userForSearch.getInterest(), 2));}
                    if (userForSearch.getInterest().getGrowth() != null){
                    ArrayList<String> arrayList = new ArrayList();
                    arrayList.add(userForSearch.getInterest().getGrowth());
                    activity.binding.contentFirstSettings.growthInterestPicker.setInterest(arrayList);}
                    if(userForSearch.getInterest().getEyes() != null){
                    ArrayList<String> arrayList1 = new ArrayList();
                    arrayList1.add(userForSearch.getInterest().getEyes());
                    activity.binding.contentFirstSettings.eyesInterestPicker.setInterest(arrayList1);}
                    if(userForSearch.getNick() != null){
                    activity.binding.contentFirstSettings.editTextNick.setText(userForSearch.getNick());}
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    public FirstSettingsViewModel(final FirstSettingsActivity activity) {
        this.activity = activity;
        srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        myFirebaseRef = new Firebase("https://palto.firebaseio.com/").child("members");
        setInterest();
        activity.setSupportActionBar(activity.binding.toolbar);
        userId.set(UserAgent.get().getUserId());
        Picasso.with(activity).load(srefs.getString("VKUserICON", "")).placeholder(R.drawable.imgpsh_fullsize).into(activity.binding.smallAvatarIv);
        createCityPicker(getVkRequestCities(2));
        activity.binding.contentFirstSettings.cityPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citypicker(v);
            }
        });
        activity.binding.contentFirstSettings.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirebase(v);
            }
        });
  }
    public void saveToFirebase(View view){
        if (!activity.binding.contentFirstSettings.musikInterestPicker.getInterestSet().isEmpty()
                && !activity.binding.contentFirstSettings.filmInterestPicker.getInterestSet().isEmpty()
                && !activity.binding.contentFirstSettings.znakomInterestPicker.getInterestSet().isEmpty()
                && !activity.binding.contentFirstSettings.growthInterestPicker.getInterestSet().isEmpty()
                && !activity.binding.contentFirstSettings.eyesInterestPicker.getInterestSet().isEmpty()
                && activity.binding.contentFirstSettings.editTextNick.getText().toString().length() > 2) {
            pushToFireBase("musik", activity.binding.contentFirstSettings.musikInterestPicker);
            pushToFireBase("film", activity.binding.contentFirstSettings.filmInterestPicker);
            pushToFireBase("znakom", activity.binding.contentFirstSettings.znakomInterestPicker);
            myFirebaseRef.child(srefs.getString("VKUserID", "")).child("city").setValue(strings[city]);
            myFirebaseRef.child(srefs.getString("VKUserID", "")).child("cityID").setValue(id.get(city));
                myFirebaseRef.child(srefs.getString("VKUserID", "")).child("interest")
                        .child("growth").setValue(activity.binding.contentFirstSettings.growthInterestPicker.getInterestSet().get(0));
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


    private void pushToFireBase(String name, PalToChoicePicker polToMultipleChoicePicker){
        myFirebaseRef.child(srefs.getString("VKUserID","")).child("interest").child(name).removeValue();
        for(int i = 0;i<polToMultipleChoicePicker.getInterestSet().size();i++){
            myFirebaseRef.child(srefs.getString("VKUserID", "")).child("interest").child(name).child(name + i).setValue(polToMultipleChoicePicker.getInterestSet().get(i));
        }
    }

    @NonNull
    private VKRequest getVkRequestCities(int id) {
        VKRequest request = new VKRequest("database.getCities");
        HashMap<String, Object> map = new HashMap<>();
        map.put("country_id",String.valueOf(id));
        map.put("need_all", "0");
        VKParameters parameters = new VKParameters(map);
        request.addExtraParameters(parameters);
        return request;
    }
    private String[] createCityPicker(VKRequest request){

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                Gson gson = new Gson();
                VKCItiesResponse vkcItiesResponse = gson.fromJson(String.valueOf(response.json), VKCItiesResponse.class);
                List<Item> items = vkcItiesResponse.getResponse().getItems();
                ArrayList<Item> list = new ArrayList<Item>();
                list.addAll(items);
                strings = new String[list.size()];
                id = new ArrayList<Integer>();
                ArrayList<String> nameList = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    strings[i] = list.get(i).getTitle();
                    id.add(list.get(i).getId());
                }


            }
        });

        return strings;}
    public void citypicker(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Укажите ваш город").
                setSingleChoiceItems(strings, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        city = which;
                        activity.binding.contentFirstSettings.cityID.setText(strings[which]);
                        dialog.dismiss();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
