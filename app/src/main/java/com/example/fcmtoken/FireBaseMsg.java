package com.example.fcmtoken;
import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;

 public class FireBaseMsg extends FirebaseMessagingService {
//
//     private static final String TAG ="kjkjj" ;
//
//     @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//
//        if (remoteMessage.getNotification() != null) {
//
//            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
//            getCustomDesign(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
//        }
//    }
//
//    private RemoteViews getCustomDesign(String title, String message) {
//        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
//        remoteViews.setTextViewText(R.id.title, title);
//        remoteViews.setTextViewText(R.id.message, message);
//        remoteViews.setImageViewResource(R.id.icon, R.drawable.ic_launcher_background);
//        return remoteViews;
//    }
//
//    public void showNotification(String title, String message) {
//
//        Intent intent = new Intent(this, MainActivity.class);
//        String channel_id = "notification_channel";
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
//                .setSmallIcon(R.drawable.ic_launcher_foreground).setAutoCancel(true).setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}).setOnlyAlertOnce(true)
//                .setContentIntent(pendingIntent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            builder = builder.setContent(getCustomDesign(title, message));
//        }
//        else {
//            builder = builder.setContentTitle(title).setContentText(message).setSmallIcon(R.drawable.ic_launcher_background);
//        }
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        // Check if the Android Version is greater than Oreo
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "web_app", NotificationManager.IMPORTANCE_HIGH);
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//        notificationManager.notify(0, builder.build());
//    }
//
//     @Override
//     public void onNewToken(String token) {
//         Log.d(TAG, "Refreshed token: " + token);
//
//         sendRegistrationToServer(token);
//     }
//
//     private void sendRegistrationToServer(String token) {
//         // TODO: Implement this method to send token to your app server.
//     }
//
//     public void onTokenRefresh() {
//         Task<String> refreshedToken = FirebaseMessaging.getInstance().getToken();
//
//         Log.i("***refreshedToken", String.valueOf(refreshedToken));
//
//
//     }
}
