package pl.com.januszpol.qnotes.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTE_ID = "note-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        Log.d("NotificationPublisher", "onReceive");
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTE_ID, 0);

        notificationManager.notify(id, notification);

    }
}