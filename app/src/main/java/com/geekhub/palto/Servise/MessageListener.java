package com.geekhub.palto.Servise;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.geekhub.palto.PaltoApplication;
import com.geekhub.palto.R;
import com.geekhub.palto.activity.LogInActivity;
import com.geekhub.palto.object.Item;
import com.geekhub.palto.object.ItemDialogList;
import com.geekhub.palto.useragent.UserAgent;

import java.util.ArrayList;
import java.util.Iterator;

public class MessageListener extends Service {
    public MessageListener() {
    }

    Context context;
    @Override
    public IBinder onBind(Intent intent) {


          return null;}

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        final String id = intent.getStringExtra("ID");
        context = this;
        final Firebase myMessageListenerFirebase = new Firebase("https://paltochat.firebaseio.com/")
                .child(intent.getStringExtra("ID"));
       myMessageListenerFirebase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();


               do {
                   DataSnapshot dataSnapshot1 = iterator.next();
                   Iterator<DataSnapshot> iteratorSob = dataSnapshot1.getChildren().iterator();
                   Firebase firebase = myMessageListenerFirebase.child(dataSnapshot1.getKey());
                   do {
                       DataSnapshot dataSnapshot2 = iteratorSob.next();
                       ItemDialogList itemDialogList = dataSnapshot2.getValue(ItemDialogList.class);
                       Firebase firebase1 = firebase.child(dataSnapshot2.getKey());
                       if (!itemDialogList.getId().equals(id)) {
                           if (itemDialogList.getReceived().length() == 0) {
                               createNotification(context,itemDialogList);
                               itemDialogList.setReceived("1");
                               firebase1.setValue(itemDialogList);

                           }
                       }

                   } while (iteratorSob.hasNext());

               } while (iterator.hasNext());
           }

           @Override
           public void onCancelled(FirebaseError firebaseError) {

           }
       });


        return super.onStartCommand(intent, flags, startId); }
    private void createNotification(Context context, ItemDialogList itemDialogList){
        Intent intent = new Intent(context,LogInActivity.class);
        PendingIntent pendingIntentApp = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
       android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon)
                .setContentText(itemDialogList.getLastMessage())
                .setContentTitle(itemDialogList.getNick())
                .setContentIntent(pendingIntentApp)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, builder.build());
    }
}
