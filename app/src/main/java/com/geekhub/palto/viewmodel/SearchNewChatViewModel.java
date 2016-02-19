package com.geekhub.palto.viewmodel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Query;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.geekhub.palto.R;
import com.geekhub.palto.SearchHelper;
import com.geekhub.palto.activity.ChatActivity;
import com.geekhub.palto.activity.SearchNewChatActivity;
import com.geekhub.palto.object.ItemDialogList;
import com.geekhub.palto.object.User;
import com.geekhub.palto.object.UserForSearch;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.httpClient.VKAbstractOperation;
import com.vk.sdk.api.methods.VKApiBase;
import com.vk.sdk.api.model.VKApiPhotoAlbum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.geekhub.palto.object.UserForSearch.*;

/**
 * Created by andrey on 13.02.16.
 */
public class SearchNewChatViewModel {

    SearchNewChatActivity activity;
    private Firebase myFirebaseRef;
    private Firebase myFirebaseChat;
    private ChildEventListener mListener;
    ArrayList<UserForSearch> list;
    SharedPreferences srefs;
    public SearchNewChatViewModel (SearchNewChatActivity activity){
        srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        this.activity = activity;
        myFirebaseRef = new Firebase("https://palto.firebaseio.com/");
        myFirebaseChat = new Firebase("https://paltochat.firebaseio.com/");
        list = new ArrayList<>();
        myFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterable = dataSnapshot.getChildren().iterator();
                dataSnapshot.getChildrenCount();
                do {

                    list.add(iterable.next().getValue(UserForSearch.class));
                } while (iterable.hasNext());


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        activity.binding.musikInterestPicker.setItemsFromResource(activity.getResources()
                .getStringArray(R.array.musik));
        activity.binding.znakomInterestPicker.setItemsFromResource(activity.getResources()
                .getStringArray(R.array.znakom));
        activity.binding.filmInterestPicker.setItemsFromResource(activity.getResources()
                .getStringArray(R.array.film));
        activity.binding.growthInterestPicker.setItemsFromResource(activity.getResources()
                .getStringArray(R.array.growth));
        activity.binding.eyesInterestPicker.setItemsFromResource(activity.getResources()
                .getStringArray(R.array.eyes));

        VKRequest request = getVkRequestCities();
        activity.binding.cityInterestPicker.setItemsFromVKRequest(request);
    }

    @NonNull
    private VKRequest getVkRequestCities() {
        VKRequest request = new VKRequest("database.getCities");
        HashMap<String, Object> map = new HashMap<>();
        map.put("country_id", "2");
        map.put("need_all", "0");
        VKParameters parameters = new VKParameters(map);
        request.addExtraParameters(parameters);
        return request;
    }

    public void startChat(View view){


        SearchHelper searchHelper = new SearchHelper();
        UserForSearch userForSearch = new UserForSearch();
        userForSearch.setCityID(Integer.parseInt(activity.binding.cityInterestPicker.getInterestSet().get(0)));
        UserForSearch choice = searchHelper.init(list, userForSearch);
        ItemDialogList firstMes = new ItemDialogList(srefs.getString("VKUserICON", "null"),
                srefs.getString("VKUserNICK", "null"), "Привет", "");
        myFirebaseChat.child(choice.getId()).child(srefs.getString("VKUserID", "null")).push().setValue(firstMes);
        myFirebaseChat.child(srefs.getString("VKUserID", "null")).child(choice.getId()).push().setValue(firstMes);

        SharedPreferences.Editor edit = PreferenceManager.
                getDefaultSharedPreferences(activity.getApplicationContext()).edit();
        edit.putString("VKUserCHAT", choice.getId()).apply();
        activity.startActivity(new Intent(activity, ChatActivity.class));
    }
}
