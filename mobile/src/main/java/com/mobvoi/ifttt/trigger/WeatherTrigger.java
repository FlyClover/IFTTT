package com.mobvoi.ifttt.trigger;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author AnkaZhao<kei@mobvoi.com>
 * @date 2015年04月11日
 */
public class WeatherTrigger extends Trigger {

    private static final String TAG = "WeatherTrigger";
    private WeatherTrigger.Model weatherModel = null;
    private String msg = "";

    public static class Model {
        public int pm25Min = 0;
        public int pm25Max = Integer.MAX_VALUE;
    }

    @Override
    public void setModel(Trigger.Model model) {
        Log.i(TAG, "setModel=" + model.toString());
        weatherModel = new Model();
        try {
//            JSONObject descJson = new JSONObject(model.desc);
//            weatherModel.pm25Max = descJson.getInt("pm_25_max");
//            weatherModel.pm25Min = descJson.getInt("pm_25_min");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isTriggered() {
        msg = "当前北京的pm25指数高达400，请注意出行";
        return true;
    }

    @Override
    public String getTriggerMsg() {
        return msg;
    }
}
