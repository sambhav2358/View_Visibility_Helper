package com.sambhav2358.notificationbuttonsviewvisibiltymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final String CHANNEL_ID = "Channel_id";
    NotificationManager mNotificationManager;
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle(CHANNEL_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.parent).setOnClickListener(view -> updateNotification());
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();

        if (intent.getAction().equals("open")){
            findViewById(R.id.tv).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.tv).setVisibility(View.GONE);
        }
    }

    @TargetApi(26)
    private synchronized void createChannel() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String name = "STOPWATCH";
        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

        mChannel.setName("Notifications");

        if (mNotificationManager != null) {
            mNotificationManager.createNotificationChannel(mChannel);
        }

    }

    @SuppressLint("NewApi")
    public void updateNotification() {
        createChannel();
        notificationBuilder.setContentTitle(getString(R.string.app_name))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .addAction(createNotificationActionButton("OPEN", "open"))
                .setContentText("Click to open and show view");


        mNotificationManager.notify(100, notificationBuilder.build());
    }

    public NotificationCompat.Action createNotificationActionButton(String text, String actionName){

        Intent intent = new Intent(this, MainActivity.class).setAction(actionName);

        @SuppressLint("InlinedApi") PendingIntent pendingIntent = PendingIntent.getActivity(this, new Random().nextInt(100), intent, PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Action(0, text, pendingIntent);
    }
}