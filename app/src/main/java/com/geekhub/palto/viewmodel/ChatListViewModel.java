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
import com.geekhub.palto.activity.FirstSettingsActivity;
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

    public ChatListViewModel(final ChatListActivity activity) {
        this.activity = activity;
        srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        activity.setSupportActionBar(activity.binding.toolbar);
        activity.binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        Intent intent = new Intent(activity, FirstSettingsActivity.class);
                        activity.startActivity(intent);
                        break;
                }
                return false;
            }
        });


        final ArrayList<ItemDialogList> itemDialogListArrayList = new ArrayList<>();
        final ArrayList<String> idArray = new ArrayList<>();
        final ArrayList<String> photo = new ArrayList<>();
        Firebase firebaseForListAdapter = new Firebase("https://palto.firebaseio.com");
        firebaseForListAdapter.keepSynced(true);

        final DialogListAdapter dialogListAdapter = new DialogListAdapter(activity, itemDialogListArrayList, photo);
        firebaseForListAdapter.orderByValue().limitToLast(10).
                addValueEventListener(new ValueEventListener() {
                                          @Override
                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                              DataSnapshot chatDataSnapshot = dataSnapshot.child("message").child(srefs.getString("VKUserID", ""));

                                              Iterator<DataSnapshot> iterator = chatDataSnapshot.getChildren().iterator();
                                              if(iterator.hasNext()) {
                                                  do {
                                                      DataSnapshot dataSnapshot1 = iterator.next();
                                                      idArray.add(dataSnapshot1.getKey());
                                                      DataSnapshot membersDataSnapshot = dataSnapshot.child("members").child(dataSnapshot1.getKey());
                                                      UserForSearch userForSearch = membersDataSnapshot.getValue(UserForSearch.class);
                                                      photo.add(userForSearch.getPhoto_200());
                                                      dialogListAdapter.notifyDataSetChanged();
                                                      ArrayList<ItemDialogList> arrayList = new ArrayList();
                                                      Iterator<DataSnapshot> iterator1 = dataSnapshot1.getChildren().iterator();
                                                      do {
                                                          arrayList.add(iterator1.next().getValue(ItemDialogList.class));
                                                      }
                                                      while (iterator1.hasNext());

                                                      itemDialogListArrayList.add(arrayList.get(arrayList.size() - 1));


                                                  }
                                                  while (iterator.hasNext());}
                                              dialogListAdapter.notifyDataSetChanged();
                                              activity.binding.dialogList.setAdapter(dialogListAdapter);
                                      }

                                          @Override
                                          public void onCancelled(FirebaseError firebaseError) {

                                          }
                                      }

                );

        activity.binding.dialogList.
                setOnItemClickListener(new AdapterView.OnItemClickListener()

                                       {

                                           @Override
                                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                               Intent intent = new Intent(activity, ChatActivity.class);
                                               intent.putExtra("FriendID", idArray.get(position));
                                               intent.putExtra("FriendNick", dialogListAdapter.getItem(position).getUserChatWithNick());
                                               activity.startActivity(intent);
                                           }
                                       }

                );
        activity.binding.addNewChatButton.
                setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {
                                           activity.startActivity(new Intent(activity, SearchNewChatActivity.class));
                                       }
                                   }

                );


        }
    }
