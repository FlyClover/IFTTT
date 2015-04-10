package com.mobvoi.ifttt.trigger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AnkaZhao<kei@mobvoi.com>
 * @date 2015年04月11日
 */
public class TriggerManager {
    private static final Map<String, Class> classMap;

    static {
        classMap = new HashMap<>();
        classMap.put("weather", WeatherTrigger.class);
    }

    public static Trigger loadTrigger(Trigger.Model model) throws Exception {
        Trigger trigger = (Trigger) classMap.get(model.type).newInstance();
        if (null != trigger) {
            trigger.setModel(model);
            return trigger;
        } else {
            throw new Exception("load trigger failed, model=" + model.toString());
        }
    }

}
