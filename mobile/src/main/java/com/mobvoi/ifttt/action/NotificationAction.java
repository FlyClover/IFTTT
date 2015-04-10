package com.mobvoi.ifttt.action;

import com.mobvoi.ifttt.MobvoiSender;
import com.mobvoi.ifttt.trigger.Trigger;

/**
 * @author AnkaZhao<kei@mobvoi.com>
 * @date 2015年04月11日
 */
public class NotificationAction extends Action {
    public String msg;

    @Override
    public void doAction(Trigger trigger) {
        msg = trigger.getTriggerMsg();
        //send notification msg to wear
        MobvoiSender.getInstance().send(msg.getBytes());
    }

    @Override
    public void setModel(Action.Model model) {
    }
}
