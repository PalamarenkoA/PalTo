package com.geekhub.palto.servise;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import com.geekhub.palto.activity.ChatActivity;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class NotivicationIntentService extends IntentService {
    public static final String ACTION_MESSAGE = "com.geekhub.palto.servise.action.MESSAGE";
    public static final String EXTRA_TEXT = "com.geekhub.palto.servise.extra.TEXT";
    public static final String EXTRA_NICK = "com.geekhub.palto.servise.extra.NICK";
    public static final String EXTRA_USER_ID = "com.geekhub.palto.servise.extra.USERID";

    public NotivicationIntentService() {
        super("NotivicationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_MESSAGE.equals(action)) {
                final String message = intent.getStringExtra(EXTRA_TEXT);
                final String nick = intent.getStringExtra(EXTRA_NICK);
                String frId = intent.getStringExtra(EXTRA_USER_ID);
                handleActionMessage(message, nick, frId);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void handleActionMessage(String text, String nick, String frId) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("FriendNick", nick);
        intent.putExtra("FriendID", frId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification noti = new Notification.Builder(this).setContentTitle("Новое сообщение от " + nick)
                .setContentText(text).setContentIntent(pendingIntent).build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify("new_message", 1, noti);
    }

}
