package com.asterisk.opensource.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/16
 * @Time 9:34
 * @Description
 * @Since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExecutionEventBusFactory {


    private static final ConcurrentHashMap<String,ExecutionEventBus> CONTAINER = new ConcurrentHashMap<>();

    /**
     * 获取事件总线实例
     * @param name 事件总线名称
     * @return 事件总线实例
     */
    public static ExecutionEventBus getInstance(String name) {
        if (CONTAINER.contains(name)) {
            return CONTAINER.get(name);
        }
        CONTAINER.putIfAbsent(name,new ExecutionEventBus());
        return CONTAINER.get(name);
    }
}
