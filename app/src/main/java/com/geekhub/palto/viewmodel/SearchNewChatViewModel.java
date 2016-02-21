package com.geekhub.palto.viewmodel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.geekhub.palto.R;
import com.geekhub.palto.SearchHelper;
import com.geekhub.palto.activity.ChatActivity;
import com.geekhub.palto.activity.SearchNewChatActivity;
import com.geekhub.palto.object.Film;
import com.geekhub.palto.object.Interest;
import com.geekhub.palto.object.ItemDialogList;
import com.geekhub.palto.object.UserForSearch;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
    public ArrayList<String> users;
    public SearchNewChatViewModel (SearchNewChatActivity activity){
        users = new ArrayList<>();
        srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        this.activity = activity;
        myFirebaseRef = new Firebase("https://palto.firebaseio.com/");
        myFirebaseChat = new Firebase("https://paltochat.firebaseio.com/");
        Firebase firebase = myFirebaseChat.child(srefs.getString("VKUserID",""));
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                if(iterator.hasNext()){
                do {
                    DataSnapshot dataSnapshot1 = iterator.next();
                    users.add(dataSnapshot1.getKey());
                }while (iterator.hasNext());
            }}

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
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
        Interest interest = new Interest();
        if(activity.binding.cityInterestPicker.getInterestSet().size()>0) {
            userForSearch.setCityID(Integer.parseInt(activity.binding.cityInterestPicker.getInterestSet().get(0)));
        }
        if(activity.binding.growthInterestPicker.getInterestSet().size()>0) {
           interest.setGrowth(activity.binding.growthInterestPicker.getInterestSet().get(0));
        }
        if(activity.binding.eyesInterestPicker.getInterestSet().size()>0) {
            interest.setEyes(activity.binding.eyesInterestPicker.getInterestSet().get(0));
        }
        userForSearch.setInterest(interest);
        UserForSearch choice = searchHelper.init(list, userForSearch,users,
                activity.binding.filmInterestPicker.getInterestSet(),
                activity.binding.musikInterestPicker.getInterestSet(),
                activity.binding.znakomInterestPicker.getInterestSet()
                );
        if(choice != null) {
            ItemDialogList firstMes = new ItemDialogList(srefs.getString("VKUserICON", "null"),
                    srefs.getString("VKUserNICK", "null"), "Привет", srefs.getString("VKUserID", ""), "0");


            myFirebaseChat.child(choice.getId()).child(srefs.getString("VKUserID", "null")).push().setValue(firstMes);
            myFirebaseChat.child(srefs.getString("VKUserID", "null")).child(choice.getId()).push().setValue(firstMes);
            Intent intent = new Intent(activity, ChatActivity.class);
            intent.putExtra("FriendID", choice.getId());
            activity.startActivity(intent);
        }
        else{
            Toast.makeText(activity,"Нет ни одного подходящего пользователь",Toast.LENGTH_LONG).show();
        }
    }

}
