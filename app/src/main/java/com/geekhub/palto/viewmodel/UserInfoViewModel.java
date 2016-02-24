package com.geekhub.palto.viewmodel;

import android.content.Intent;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.geekhub.palto.R;
import com.geekhub.palto.activity.UserInfoActivity;
import com.geekhub.palto.object.UserForSearch;
import com.squareup.picasso.Picasso;

/**
 * Created by andrey on 24.02.16.
 */
public class UserInfoViewModel {
    UserInfoActivity activity;
    Intent intent;
    Firebase myFirebaseRef;
    UserForSearch userForSearch;
    public  UserInfoViewModel(final UserInfoActivity activity){
        intent = activity.getIntent();
        myFirebaseRef = new Firebase("https://palto.firebaseio.com/").child(intent.getStringExtra("FriendID"));
        this.activity = activity;
        myFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userForSearch = dataSnapshot.getValue(UserForSearch.class);
                Picasso.with(activity).load(userForSearch.getPhoto_200()).placeholder(R.drawable.imgpsh_fullsize).into(activity.binding.smallAvatarIv);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }


}
