package com.geekhub.palto.servise;

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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.geekhub.palto.PaltoApplication;
import com.geekhub.palto.R;
import com.geekhub.palto.activity.ChatActivity;
import com.geekhub.palto.activity.ChatListActivity;
import com.geekhub.palto.activity.LogInActivity;
import com.geekhub.palto.object.ItemDialogList;

import java.util.ArrayList;
import java.util.Iterator;

public class MessageListener extends Service {
    public MessageListener() {
    }

    Context context;

    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (LogInActivity.authorized) {
            final SharedPreferences srefs = PreferenceManager.getDefaultSharedPreferences(PaltoApplication.CONTEXT);

            context = this;
            final Firebase myMessageListenerFirebase = new Firebase("https://paltochat.firebaseio.com/")
                    .child(srefs.getString("VKUserID", ""));
            myMessageListenerFirebase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<ItemDialogList> arrayList = new ArrayList();
                    Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                    if (iterator.hasNext()) {
                        do {
                            DataSnapshot dataSnapshot1 = iterator.next();
                            Iterator<DataSnapshot> iteratorSob = dataSnapshot1.getChildren().iterator();
                            Firebase firebase = myMessageListenerFirebase.child(dataSnapshot1.getKey());
                            do {
                                DataSnapshot dataSnapshot2 = iteratorSob.next();
                                ItemDialogList itemDialogList = dataSnapshot2.getValue(ItemDialogList.class);
                                Firebase firebase1 = firebase.child(dataSnapshot2.getKey());
                                if (!itemDialogList.getId().equals(srefs.getString("VKUserID", ""))) {
                                    if (itemDialogList.getReceived().equals("0")) {
                                        arrayList.add(itemDialogList);
                                        itemDialogList.setReceived("1");
                                        firebase1.setValue(itemDialogList);

                                    }
                                }

                            } while (iteratorSob.hasNext());

                        } while (iterator.hasNext());

                    }
                    if (arrayList.size() == 1) {
                        createOneNotification(context, arrayList.get(0));
                    }
                    if (arrayList.size() > 1) {
                        ArrayList<String> arrayList1 = new ArrayList<String>();
                        for (int i = 0; i < arrayList.size(); i++) {
                            arrayList1.add(arrayList.get(i).getNick());
                        }
                        createSeveralNotification(context, arrayList1);

                    }

                }


                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }


        return super.onStartCommand(intent, flags, startId);
    }

    private void createOneNotification(Context context, ItemDialogList itemDialogList) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("FriendID", itemDialogList.getId());
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

    private void createSeveralNotification(Context context, ArrayList<String> mes) {
        Intent intent = new Intent(context, ChatListActivity.class);
        String mesSt = "";
        for (int i = 0; i < mes.size(); i++) {
            mesSt = mesSt + ", " + mes.get(i);
        }

        PendingIntent pendingIntentApp = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon)
                .setContentText(mesSt)
                .setContentTitle("Новых сообщений -" + mes.size())
                .setContentIntent(pendingIntentApp)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, builder.build());
    }
}
