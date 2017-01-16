package com.asterisk.opensource.event;

import com.google.common.eventbus.EventBus;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/16
 * @Time 9:35
 * @Description
 * @Since 1.0.0
 */
public class ExecutionEventBus {

    private final EventBus instance = new EventBus();

    private final ConcurrentHashMap<String,EventBusListener> listeners = new ConcurrentHashMap<>();

    /**
     *
     * @param event  要执行的事件
     */
    public void post(ExecutionEvent event) {
        if (listeners.isEmpty()) {
            return;
        }
        instance.post(event);
    }

    /**
     *   注册事件监听器
     * @param eventBusListener 执行事件监听器
     */
    public void register(EventBusListener eventBusListener) {
        if (listeners.putIfAbsent(eventBusListener.getName(),eventBusListener) != null) {
            return;
        }
        instance.register(eventBusListener);
    }


    public synchronized void clearListener() {
        listeners.values().forEach(instance::register);
        listeners.clear();
    }
}
