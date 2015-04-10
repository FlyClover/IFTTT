package com.mobvoi.ifttt;

import com.mobvoi.ifttt.action.Action;
import com.mobvoi.ifttt.action.ActionManager;
import com.mobvoi.ifttt.trigger.Trigger;
import com.mobvoi.ifttt.trigger.TriggerManager;

/**
 * @author AnkaZhao<kei@mobvoi.com>
 * @date 2015年04月11日
 */
public class Recipe {

    public Trigger trigger;
    public Action action;

    public void loadRecipe(Trigger.Model triggerModel, Action.Model actionModel) throws Exception {
        this.trigger = TriggerManager.loadTrigger(triggerModel);
        this.action = ActionManager.loadAction(actionModel);
    }

    public void check() {
        if (trigger.isTriggered()) {
            action.doAction(trigger);
        }
    }

    public static class Model {
        public Trigger.Model trigger;
        public Action.Model action;
    }
}
