package com.mobvoi.ifttt.action;

import com.mobvoi.ifttt.trigger.Trigger;

/**
 * @author AnkaZhao<kei@mobvoi.com>
 * @date 2015年04月11日
 */
public abstract class Action {

    public abstract void doAction(Trigger trigger);

    public abstract void setModel(Model model);

    public static class Model {
        public int id;
        public String type;
        public String desc;

        public String toString() {
            return "[" + id + ", " + type + ", " + desc + "]";
        }
    }
}
