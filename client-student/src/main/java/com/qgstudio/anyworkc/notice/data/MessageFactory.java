package com.qgstudio.anyworkc.notice.data;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.google.gson.JsonObject;
import com.qgstudio.anyworkc.App;
import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.notice.NoticeActivity;
import com.qgstudio.anyworkc.websocket.WebSocketHolder;

public class MessageFactory {
    static NotificationManager mNotificationManager;

    public static final String CHANNEL_ID = "1";

    public static Message fromJsonObject(JsonObject jsonObject) {
        int messageType = jsonObject.get("type").getAsInt();
        Message message;
        switch (messageType) {
            case Message.TYPE_ONLINE_COUNT:
                OnlineCount onlineCount = new OnlineCount();
                onlineCount.type = Message.TYPE_ONLINE_COUNT;
                onlineCount.onlineCount = jsonObject.get("onlineCount").getAsInt();
                message = onlineCount;
                WebSocketHolder.getDefault().onlineCount.postValue(onlineCount.onlineCount);
                break;
            case Message.TYPE_NOTICE:
                Notice notice = new Notice();
                notice.messageId = jsonObject.get("messageId").getAsInt();
                notice.title = jsonObject.get("title").getAsString();
                notice.content = jsonObject.get("content").getAsString();
                notice.publisher = jsonObject.get("publisher").getAsString();
                notice.status = jsonObject.get("status").getAsInt();
                notice.type = Message.TYPE_NOTICE;
                message = notice;
                buildNotification(notice.title, notice.content);
                break;
            default:
                message = null;
                break;
        }
        return message;
    }


//    public static void makeNotice(String content) {
////        Log.d("makeNotice", "makeNotice");
////        //?????????????????????
////        NotificationManager mNotificationManager = (NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
////        int icon = R.drawable.ic_icon;
////        long when = System.currentTimeMillis();
////        //?????????????????????????????????????????????
////        Notification notification = new Notification(icon, null, when);//????????????????????????,????????????????????????,????????????????????????
////        notification.defaults = Notification.DEFAULT_SOUND;//??????????????????
//        Intent openintent = new Intent(App.getContext(), NoticeActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(App.getContext(), 0, openintent, 0);//???????????????????????????????????????openintent??????
//        notification.setLatestEventInfo(App.getContext(), "AnyWork?????????????????????", content, contentIntent);
//        mNotificationManager.notify(1, notification);//????????????????????????????????????????????????
//    }

//    public Notification getNotification(String title, String content) {
//        NotificationCompat.Builder builder = buildNotification(title, content);
//        return builder.build();
//    }

    public static void buildNotification(String title, String content) {
        Intent openintent = new Intent(App.getContext(), NoticeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(App.getContext(), 0, openintent, 0);//???????????????????????????????????????openintent??????
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(App.getContext(), CHANNEL_ID);

        builder.setSmallIcon(R.drawable.ic_icon)
                //????????????????????????
                //.setContentIntent(createContentIntent())
                .setContentTitle(title)
                .setContentText(content)
                //??????Glide????????????,?????????Clide??????api???????????????
                .setSmallIcon(R.drawable.ic_icon)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        if (isAndroidOOrHigher()) {
            createChannel(title, content, builder);
        }


        Notification notification = builder.build();
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        mNotificationManager.notify(1, notification);
    }

    private static boolean isAndroidOOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private static void createChannel(String title, String content, NotificationCompat.Builder builder) {
        NotificationManager mNotificationManager = (NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        final String CHANNEL_ID = "1";
        if (mNotificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            // The user-visible name of the channel.
//            CharSequence name = "MediaSession";
//            // The user-visible description of the channel.
//            String description = "MediaSession and MediaPlayer";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, title, importance);
            // Configure the notification channel.
            mChannel.setDescription(content);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(
                    new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(1, builder.build());
        } else {

        }
    }

}
