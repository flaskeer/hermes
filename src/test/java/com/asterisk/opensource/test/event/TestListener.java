package com.asterisk.opensource.test.event;

import com.asterisk.opensource.event.EventBusListener;
import com.asterisk.opensource.event.ExecutionEvent;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/16
 * @Time 10:38
 * @Description
 * @Since 1.0.0
 */
@Slf4j
public class TestListener implements EventBusListener{

    public static final String NAME = "TEST";

    @Subscribe
    @AllowConcurrentEvents
    public void listen(ExecutionEvent executionEvent) {
        String id = executionEvent.getId();
        log.info("executionEvent 's id is:{}",id);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
