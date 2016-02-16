package com.geekhub.palto.viewmodel;

import android.util.Log;

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
import com.geekhub.palto.activity.SearchNewChatActivity;
import com.geekhub.palto.object.User;
import com.geekhub.palto.object.UserForSearch;

import java.util.ArrayList;

/**
 * Created by andrey on 13.02.16.
 */
public class SearchNewChatViewModel {

    SearchNewChatActivity activity;
    private Firebase myFirebaseRef;
    private ChildEventListener mListener;
    ArrayList<UserForSearch> list;
    Transaction.Handler h;
    public SearchNewChatViewModel (SearchNewChatActivity activity){
        this.activity = activity;
        myFirebaseRef = new Firebase("https://palto.firebaseio.com/");
        list = new ArrayList<>();
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("logos", dataSnapshot.toString());
                UserForSearch userForSearch = dataSnapshot.getChildren().iterator().next().getValue(UserForSearch.class);
                list.add(userForSearch);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
//        mListener = myFirebaseRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//               Log.d("logos",dataSnapshot.toString());
//               UserForSearch userForSearch = dataSnapshot.getValue(UserForSearch.class);
//                list.add(userForSearch);
//
//
//
//
//
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });



        activity.binding.musikInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.musik));
        activity.binding.znakomInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.znakom));
        activity.binding.filmInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.film));
        activity.binding.growthInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.growth));
        activity.binding.eyesInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.eyes));
        activity.binding.cityInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.eyes));
    }
}
