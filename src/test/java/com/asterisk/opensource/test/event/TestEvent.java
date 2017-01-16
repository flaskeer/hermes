package com.asterisk.opensource.test.event;

import com.asterisk.opensource.event.EventExecutionType;
import com.asterisk.opensource.event.ExecutionEvent;
import com.asterisk.opensource.event.ExecutionEventBusFactory;
import org.junit.Test;

import java.util.UUID;

import static com.asterisk.opensource.test.event.TestListener.NAME;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/16
 * @Time 10:37
 * @Description
 * @Since 1.0.0
 */
public class TestEvent {


    @Test
    public void test_event() {
        ExecutionEventBusFactory.getInstance(NAME).register(new TestListener());
        ExecutionEventBusFactory.getInstance(NAME).post(new ExecutionEvent(UUID.randomUUID().toString().replace("-","^-^"), EventExecutionType.EXECUTE_SUCCESS));
    }

}
