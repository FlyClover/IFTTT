package com.mobvoi.ifttt.action;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AnkaZhao<kei@mobvoi.com>
 * @date 2015年04月11日
 */
public class ActionManager {
    private static final Map<String, Class> classMap;

    static {
        classMap = new HashMap<>();
        classMap.put("notification", NotificationAction.class);
    }

    public static Action loadAction(Action.Model model) throws Exception {
        Action action = (Action) classMap.get(model.type).newInstance();
        if (null != action) {
            action.setModel(model);
            return action;
        } else {
            throw new Exception("load action failed, action=" + model.toString());
        }
    }
}
