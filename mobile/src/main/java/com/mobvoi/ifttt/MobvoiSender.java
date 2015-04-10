package com.mobvoi.ifttt;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.wearable.Wearable;

/**
 * @author AnkaZhao<kei@mobvoi.com>
 * @date 2015年04月11日
 */
public class MobvoiSender implements MobvoiApiClient.ConnectionCallbacks {
    private static final String TAG = "MobvoiSender";
    private static MobvoiApiClient mobvoiApiClient;
    private static MobvoiSender sInstance;
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public MobvoiSender() {
        mobvoiApiClient = new MobvoiApiClient.Builder(mContext).addApi(Wearable.API)
                .addConnectionCallbacks(this).build();
        mobvoiApiClient.connect();
    }

    public synchronized static MobvoiSender getInstance() {
        if (null == sInstance) {
            sInstance = new MobvoiSender();
        }
        return sInstance;
    }

    public void send(byte[] data) {
        while (!mobvoiApiClient.isConnected()) {
            Log.i(TAG, "not connected, waiting~");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        Log.i(TAG, "is connected. send data=" + new String(data));
        Wearable.MessageApi.sendMessage(mobvoiApiClient, "1", "/ifttt", data);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "连接上");
//        Wearable.MessageApi.sendMessage(mobvoiApiClient, "1", "/ifttt", "测试数据".getBytes());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
