package com.mobvoi.ifttt;

import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.mobvoi.android.wearable.MessageEvent;
import com.mobvoi.android.wearable.WearableListenerService;

/**
 * @author AnkaZhao<kei@mobvoi.com>
 * @date 2015年04月11日
 */
public class DataLayoutService extends WearableListenerService {

    private static final String TAG = "DataLayoutService";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.i(TAG, "receive message=" + messageEvent.getPath() + ", " + new String(messageEvent.getData()));
        if (messageEvent.getPath().equals("/ifttt")) {
            Notification.Builder builder = new Notification.Builder(this).setContentText(new String(messageEvent.getData())).setContentTitle("IFTTT").setSmallIcon(this.getApplicationInfo().icon);
            NotificationManagerCompat.from(this).notify(1, builder.build());
        }
    }
}
