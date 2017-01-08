package com.asterisk.opensource.utils;

import com.asterisk.opensource.spider.queue.QueueProvider;

/**
 * @author donghao
 * @Date 17/1/8
 * @Time 上午11:10
 */
public class ConditionUtils {

    public static boolean matchMessageQueue(QueueProvider queueProvider) {
        String messageQueue = ConfigLoader.getString("message.queue", "");
        return queueProvider.name().equalsIgnoreCase(messageQueue);
    }
}
