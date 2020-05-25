package com.hfad.reminderapp;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * @author ozgeonec
 */
public class NotificationHelper extends ContextWrapper{
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private NotificationManager notificationManager;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName,
                NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }
    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }
    public NotificationCompat.Builder getChannelNotification() {
        RemindrDatabase rb = new RemindrDatabase(getApplicationContext());
        Reminder reminder = rb.getReminder(1);
        String mTitle = reminder.getTitle();
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Scheduled Alert")
                .setContentText("Your Alert is Ringing")
                .setSmallIcon(R.drawable.bell);
    }
}
