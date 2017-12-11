package pl.com.januszpol.qnotes.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

import pl.com.januszpol.qnotes.Model.ObjectModel.Note;
import pl.com.januszpol.qnotes.Model.ObjectModel.NoteNotification;
import pl.com.januszpol.qnotes.Presentation.MainActivity;
import pl.com.januszpol.qnotes.QnotesApplication;
import pl.com.januszpol.qnotes.R;

/**
 * Created by Kordian on 11.12.2017.
 */

public class NotificationManager {
    public static void scheduleNoteNotifications(Note note) {
        Context context = QnotesApplication.getAppContext();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int counter = 0;
        for (NoteNotification noteNotification : note.getNotificationsList()) {
            scheduleNoteNotification(note, noteNotification, counter++, context);
        }
    }

    public static void removeNoteNotifications(Note note) {
        Context context = QnotesApplication.getAppContext();
        for (int counter = 0; counter < note.getNotificationsList().size(); counter++) {
            Intent intent = createIntent(note, counter, context);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) note.getId(), intent, PendingIntent.FLAG_NO_CREATE);

            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            if (null != pendingIntent)
                alarmManager.cancel(pendingIntent);
        }

    }

    private static Intent createIntent(Note note, int counter, Context context) {
        Intent intent = new Intent(context, NotificationPublisher.class);
        intent.setData(Uri.parse("id:" + counter));
        intent.putExtra(NotificationPublisher.NOTE_ID, (int) note.getId());
        intent.putExtra(NotificationPublisher.NOTIFICATION, createNotification(note, context));
        return intent;
    }


    private static void scheduleNoteNotification(Note note, NoteNotification noteNotification, int counter, Context context) {
        if (noteNotification.getExecuteDate().after(new Date())) {
            Intent intent = createIntent(note, counter, context);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) note.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, noteNotification.getExecuteDate().getTime(), pendingIntent);
            //alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + ((counter + 1) * 10000), pendingIntent);
        }
    }


    private static Notification createNotification(Note note, Context context) {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("note_id", note.getId());
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(note.getTopic());
        builder.setContentText(note.getDescription());
        builder.setSmallIcon(R.drawable.ic_menu_camera);
        builder.setContentIntent(intent);



        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        return notification;
    }
}
