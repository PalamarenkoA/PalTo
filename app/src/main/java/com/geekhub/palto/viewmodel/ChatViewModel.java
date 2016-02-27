package com.geekhub.palto.viewmodel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.geekhub.palto.activity.ChatActivity;
import com.geekhub.palto.activity.UserInfoActivity;
import com.geekhub.palto.activity.UserSettingsActivity;
import com.geekhub.palto.adapter.NewChatAdapter;
import com.geekhub.palto.object.ItemDialogList;
import com.geekhub.palto.object.UserForSearch;
import com.geekhub.palto.useragent.UserAgent;
import com.squareup.picasso.Picasso;

/**
 * Created by andrey on 09.02.16.
 */
public class ChatViewModel {
    ChatActivity activity;
    SharedPreferences srefs;
    Firebase myFirebaseRef;
    Intent intent;

    public ChatViewModel ( final ChatActivity activity){
        this.activity = activity;
        srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        Firebase.setAndroidContext(activity.getApplicationContext());
        myFirebaseRef = new Firebase("https://paltochat.firebaseio.com/");
        myFirebaseRef.keepSynced(true);
        intent = activity.getIntent();

        final Firebase chatFirebase = myFirebaseRef.child(srefs.getString("VKUserID",""))
                .child(intent.getStringExtra("FriendID"));

        final NewChatAdapter newChatAdapter = new NewChatAdapter(chatFirebase, ItemDialogList.class, activity);


        activity.binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.binding.editText.length() > 0) {
                    ItemDialogList itemDialogList = new ItemDialogList(srefs.getString("VKUserICON", "null"),
                            srefs.getString("VKUserNICK", "nick")
                            , activity.binding.editText.getText().toString(),
                            srefs.getString("VKUserID",""), "0");
                    chatFirebase.push().setValue(itemDialogList);

                    myFirebaseRef.child(intent.getStringExtra("FriendID")).child(srefs.getString("VKUserID","")).push().setValue(itemDialogList);
                    activity.binding.editText.setText("");
                }
            }
        });

        final RecyclerView chatList = activity.binding.chatList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity){
            @Override
            public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) {
                super.onItemsAdded(recyclerView, positionStart, itemCount);
                recyclerView.smoothScrollToPosition(chatList.getBottom());
            }
        };

        chatList.setLayoutManager(layoutManager);
        chatList.setAdapter(newChatAdapter);
        chatList.smoothScrollToPosition(chatList.getBottom());

        Firebase firebase = new Firebase("https://palto.firebaseio.com/").child(intent.getStringExtra("FriendID"));
        firebase.keepSynced(true);
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserForSearch userForSearch = dataSnapshot.getValue(UserForSearch.class);
                Picasso.with(activity).load(userForSearch.getPhoto_200()).into(activity.binding.yourIcon);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Picasso.with(activity).load(srefs.getString("VKUserICON","")).into(activity.binding.myIcon);
    }
    public void clickUserInfo (View view){
        Intent intent = new Intent(activity, UserInfoActivity.class);
        intent.putExtra("FriendID", this.intent.getStringExtra("FriendID"));
        activity.startActivity(intent);
    }
    public void clickUserSettings (View view){
        Intent intent = new Intent(activity, UserSettingsActivity.class);
        intent.putExtra("FriendID", srefs.getString("VKUserID",""));
        activity.startActivity(intent);
    }
}
