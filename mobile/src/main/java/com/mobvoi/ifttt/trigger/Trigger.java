package com.mobvoi.ifttt.trigger;

/**
 * @author AnkaZhao<kei@mobvoi.com>
 * @date 2015年04月11日
 */
public abstract class Trigger {

    public abstract void setModel(Model model);

    public abstract boolean isTriggered();

    public abstract String getTriggerMsg();

    public static class Model {
        public int id;
        public String type;
        public String desc;

        public String toString() {
            return "[" + id + ", " + type + ", " + desc + "]";
        }
    }
}
