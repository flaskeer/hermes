package com.asterisk.opensource.spider.mapper;

import com.asterisk.opensource.spider.strategy.TimeWatingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.function.Function;

/**
 * @author dongh38@ziroom.com
 * @version 1.0.0
 */
@Slf4j
public abstract class AbstractMapper<T, R> implements Function<T, R> {

    private TimeWatingStrategy timeWatingStrategy;

    public AbstractMapper(TimeWatingStrategy timeWatingStrategy) {
        this.timeWatingStrategy = timeWatingStrategy;
    }

    protected abstract R mapLogic(T t) throws Exception;

    @Override
    public R apply(T t) {
        log.info("{} mapping -> {}",getClass().getSimpleName(),t.getClass().getSimpleName());
        R result = null;
        int retryTime = timeWatingStrategy.retryTimes();
        try {
            int loopTime = 1;
            while (retryTime > loopTime) {
                try {
                    result = mapLogic(t);
                    break;
                } catch (Exception e) {
                    if (!(e instanceof IOException)) throw e;
                    log.warn("Mapper:Network busy Retry -> {} times",loopTime);
                    timeWatingStrategy.waiting(loopTime++);
                }
            }
        } catch (Exception e) {
            log.error("UnExpected error",e);
        }
        return result;
    }
}
