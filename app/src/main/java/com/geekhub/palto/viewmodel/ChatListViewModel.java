package com.geekhub.palto.viewmodel;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.geekhub.palto.R;
import com.geekhub.palto.activity.ChatActivity;
import com.geekhub.palto.activity.ChatListActivity;
import com.geekhub.palto.activity.SearchNewChatActivity;
import com.geekhub.palto.activity.UserSettingsActivity;
import com.geekhub.palto.adapter.DialogListAdapter;
import com.geekhub.palto.object.ItemDialogList;
import com.geekhub.palto.object.UserForSearch;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by andrey on 08.02.16.
 */
public class ChatListViewModel {
    ChatListActivity activity;
    SharedPreferences srefs;
    int count = 0;
    public ChatListViewModel (final ChatListActivity activity){
        this.activity = activity;
        srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        activity.setSupportActionBar(activity.binding.toolbar);
        activity.binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        Intent intent = new Intent(activity, UserSettingsActivity.class);
                        intent.putExtra("FriendID", srefs.getString("VKUserID", ""));
                        activity.startActivity(intent);
                        break;
                }
                return false;
            }
        });


        final ArrayList<ItemDialogList> itemDialogListArrayList = new ArrayList<>();
        final ArrayList <String> idArray = new ArrayList<>();
        final ArrayList<String> photo = new ArrayList<>();
        Firebase firebaseForListAdapter = new Firebase("https://paltochat.firebaseio.com");
        Firebase chatfirebase = firebaseForListAdapter.child(srefs.getString("VKUserID","null"));
        final DialogListAdapter dialogListAdapter = new DialogListAdapter(activity,itemDialogListArrayList,photo);
        chatfirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                do {
                    DataSnapshot dataSnapshot1 = iterator.next();
                    idArray.add(dataSnapshot1.getKey());
                    ArrayList<ItemDialogList> arrayList = new ArrayList();
                    Iterator<DataSnapshot> iterator1 = dataSnapshot1.getChildren().iterator();
                    do {
                        arrayList.add(iterator1.next().getValue(ItemDialogList.class));
                    } while (iterator1.hasNext());

                    itemDialogListArrayList.add(arrayList.get(arrayList.size() - 1));


                } while (iterator.hasNext());
                Iterator<DataSnapshot> iterator2 = dataSnapshot.getChildren().iterator();
                do {
                    DataSnapshot dataSnapshot1 = iterator2.next();
                    Firebase firebase = new Firebase("https://palto.firebaseio.com").child(dataSnapshot1.getKey());
                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            UserForSearch userForSearch = dataSnapshot.getValue(UserForSearch.class);
                            photo.add(userForSearch.getPhoto_200());
                            dialogListAdapter.notifyDataSetChanged();
                            count++;
                            if(idArray.size()==count){
                                activity.binding.dialogList.setAdapter(dialogListAdapter);
                            }

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                }while (iterator2.hasNext());
            }

                @Override
                public void onCancelled (FirebaseError firebaseError){

                }
            }

            );

            activity.binding.dialogList.setOnItemClickListener(new AdapterView.OnItemClickListener()

            {

                @Override
                public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                Intent intent = new Intent(activity, ChatActivity.class);
                intent.putExtra("FriendID", idArray.get(position));
                activity.startActivity(intent);
            }
            }

            );
            activity.binding.addNewChatButton.setOnClickListener(new View.OnClickListener()

                                                                 {
                                                                     @Override
                                                                     public void onClick(View v) {
                                                                         activity.startActivity(new Intent(activity, SearchNewChatActivity.class));
                                                                     }
                                                                 }

            );


        }
    }
