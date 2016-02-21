package com.geekhub.palto.viewmodel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.geekhub.palto.activity.ChatActivity;
import com.geekhub.palto.activity.ChatListActivity;
import com.geekhub.palto.activity.SearchNewChatActivity;
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
    public ChatListViewModel (final ChatListActivity activity){
        this.activity = activity;
        srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        final ArrayList<ItemDialogList> itemDialogListArrayList = new ArrayList<>();
        final ArrayList <String> idArray = new ArrayList<>();
        Firebase firebaseForListAdapter = new Firebase("https://paltochat.firebaseio.com");
        Firebase chatfirebase = firebaseForListAdapter.child(srefs.getString("VKUserID","null"));
        final DialogListAdapter dialogListAdapter = new DialogListAdapter(activity,itemDialogListArrayList);
        chatfirebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("logos", dataSnapshot.getKey());
                idArray.add(dataSnapshot.getKey());
                ArrayList<ItemDialogList> arrayList = new ArrayList();
              Iterator<DataSnapshot> iterator =  dataSnapshot.getChildren().iterator();
                do {
                    arrayList.add(iterator.next().getValue(ItemDialogList.class));
                }while (iterator.hasNext());
                Log.d("logos", arrayList.get(arrayList.size() - 1).getLastDate());
                itemDialogListArrayList.add(arrayList.get(arrayList.size() - 1));
                dialogListAdapter.notifyDataSetChanged();

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
