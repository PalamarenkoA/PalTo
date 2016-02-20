package com.geekhub.palto.viewmodel;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.geekhub.palto.R;
import com.geekhub.palto.activity.ChatActivity;
import com.geekhub.palto.adapter.ChatListAdapter;
import com.geekhub.palto.object.ItemDialogList;
import com.geekhub.palto.useragent.UserAgent;
import com.squareup.picasso.Picasso;

/**
 * Created by andrey on 09.02.16.
 */
public class ChatViewModel {
    ChatActivity activity;
    SharedPreferences srefs;
    Firebase myFirebaseRef;
    private final ChatListAdapter chatListAdapter;

    public ChatViewModel ( final ChatActivity activity){
        this.activity = activity;
        srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        Firebase.setAndroidContext(activity.getApplicationContext());
        myFirebaseRef = new Firebase("https://paltochat.firebaseio.com/");
        final Firebase chatFirebase = myFirebaseRef.child(srefs.getString("VKUserID","")).child(srefs.getString("VKUserCHAT","1"));
        chatListAdapter = new ChatListAdapter(chatFirebase,activity, R.layout.dialog_list_item);


        activity.binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity.binding.editText.length()>0){
                    ItemDialogList itemDialogList = new ItemDialogList(srefs.getString("VKUserICON","null"),
                            srefs.getString("VKUserNICK","nick")
                            ,activity.binding.editText.getText().toString(),
                            UserAgent.get().getUserId(),"");
                    chatFirebase.push().setValue(itemDialogList);
                    myFirebaseRef.child(srefs.getString("VKUserCHAT","1")).child(srefs.getString("VKUserID","")).push().setValue(itemDialogList);
                    activity.binding.editText.setText("");
                    ListView chatList = activity.binding.chatList;
                    ((ListView) chatList).smoothScrollToPosition(chatList.getCount()+1);
                }
            }
        });

        activity.binding.chatList.setAdapter(chatListAdapter);
        Picasso.with(activity).load("http://cs623630.vk.me/v623630022/44bd6/wEJMXO7t_ic.jpg").into(activity.binding.myIcon);
        Picasso.with(activity).load("http://cs623630.vk.me/v623630022/44bd6/wEJMXO7t_ic.jpg").into(activity.binding.yourIcon);
    }
}
