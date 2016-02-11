package com.geekhub.palto.viewmodel;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import com.firebase.client.Firebase;
import com.geekhub.palto.R;
import com.geekhub.palto.activity.ChatActivity;
import com.geekhub.palto.activity.ChatListActivity;
import com.geekhub.palto.adapter.ChatListAdapter;
import com.geekhub.palto.adapter.DialogListAdapter;
import com.geekhub.palto.object.ItemDialogList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by andrey on 09.02.16.
 */
public class ChatViewModel {
    ChatActivity activity;
    SharedPreferences srefs;
    Firebase myFirebaseRef;
    public ChatViewModel ( final ChatActivity activity){
        this.activity = activity;
        myFirebaseRef = new Firebase("https://palto.firebaseio.com/");
        Firebase chatFirebase = myFirebaseRef.child("Chat");
        ChatListAdapter chatListAdapter = new ChatListAdapter(chatFirebase,activity, R.layout.dialog_list_item);
        srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        final ArrayList<ItemDialogList> itemDialogListArrayList = new ArrayList<>();
        final DialogListAdapter dialogListAdapter = new DialogListAdapter(activity,itemDialogListArrayList);
        activity.binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity.binding.editText.length()>0){
                    ItemDialogList itemDialogList = new ItemDialogList(srefs.getString("VKUserICON","null"),srefs.getString("VKUserNICK","nick"),"2323",activity.binding.editText.getText().toString());
                    myFirebaseRef.child("Chat").setValue(itemDialogList);

                }
            }
        });





        activity.binding.chatList.setAdapter(chatListAdapter);
        Picasso.with(activity).load("http://cs623630.vk.me/v623630022/44bd6/wEJMXO7t_ic.jpg").into(activity.binding.myIcon);
        Picasso.with(activity).load("http://cs623630.vk.me/v623630022/44bd6/wEJMXO7t_ic.jpg").into(activity.binding.yourIcon);
    }
}
