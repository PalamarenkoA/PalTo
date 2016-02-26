package com.geekhub.palto.viewmodel;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
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
import com.geekhub.palto.object.User;
import com.geekhub.palto.object.UserForSearch;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by andrey on 08.02.16.
 */
public class ChatListViewModel {
    ChatListActivity activity;
    SharedPreferences srefs;
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
        chatfirebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Firebase firebase = new Firebase("https://palto.firebaseio.com").child(dataSnapshot.getKey());
                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserForSearch userForSearch = dataSnapshot.getValue(UserForSearch.class);
                        photo.add(userForSearch.getPhoto_200());
                        dialogListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                idArray.add(dataSnapshot.getKey());

                ArrayList<ItemDialogList> arrayList = new ArrayList();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                do {
                    arrayList.add(iterator.next().getValue(ItemDialogList.class));
                } while (iterator.hasNext());

                itemDialogListArrayList.add(arrayList.get(arrayList.size() - 1));


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        activity.binding.dialogList.setAdapter(dialogListAdapter);
        activity.binding.dialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity,ChatActivity.class);
                intent.putExtra("FriendID",idArray.get(position));
                activity.startActivity(intent);
            }
        });
        activity.binding.addNewChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, SearchNewChatActivity.class));
            }
        });


    }
}
