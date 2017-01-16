package com.asterisk.opensource.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/16
 * @Time 9:50
 * @Description
 * @Since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpiderExecutionEventBus {

    private static final String NAME = "Spider-EventBus";

    public static void post(SpiderExecutionEvent spiderExecutionEvent) {
        ExecutionEventBusFactory.getInstance(NAME).post(spiderExecutionEvent);
    }

    public static void register(SpiderEventBusListener spiderEventBusListener) {
        ExecutionEventBusFactory.getInstance(NAME).register(spiderEventBusListener);
    }

    public static void clearListener() {
        ExecutionEventBusFactory.getInstance(NAME).clearListener();
    }
}
