package com.geekhub.palto.servise;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.firebase.client.Firebase;
import com.geekhub.palto.adapter.NewChatAdapter;
import com.geekhub.palto.object.ItemDialogList;

public class ChatListener extends Service {
    public ChatListener() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Firebase.setAndroidContext(getApplicationContext());
        Firebase firebase;
        firebase = new Firebase("https://palto.firebaseio.com/").child("message");
        firebase.keepSynced(true);

        final Firebase chatFirebase = firebase.child(sharedPreferences.getString("VKUserID", ""))
                .child(intent.getStringExtra("FriendID"));

        final NewChatAdapter newChatAdapter = new NewChatAdapter(chatFirebase, ItemDialogList.class) {
            @Override
            protected void itemAdded(ItemDialogList item, String key, int position) {
                Intent intent1 = new Intent(NotivicationIntentService.ACTION_MESSAGE);
                intent1.putExtra(NotivicationIntentService.EXTRA_TEXT, item.getLastMessage());
                intent1.putExtra(NotivicationIntentService.EXTRA_NICK, item.getUserChatWithNick());
                intent1.putExtra(NotivicationIntentService.EXTRA_USER_ID, item.getId());
                startService(intent1);
            }
        };

        return super.onStartCommand(intent, flags, startId);
    }
}
