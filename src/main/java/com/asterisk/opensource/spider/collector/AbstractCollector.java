package com.asterisk.opensource.spider.collector;

import com.asterisk.opensource.spider.strategy.TimeWatingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * @author dongh38@ziroom.com
 * @version 1.0.0
 * 爬虫的起点，从此处传入抓取的URL
 */
@Slf4j
public abstract class AbstractCollector<T> implements Supplier<T> {


    private TimeWatingStrategy timeWatingStrategy;

    public AbstractCollector(TimeWatingStrategy timeWatingStrategy) {
        this.timeWatingStrategy = timeWatingStrategy;

    }

    protected abstract T collectLogic() throws Exception;


    @Override
    public T get() {
        log.info("{} start to collecting...",getClass().getSimpleName());
        T result = null;
        int retryTime = timeWatingStrategy.retryTimes();
        try {
            int loopTime = 1;
            while (retryTime > loopTime) {
                try {
                    result = collectLogic();
                    break;
                } catch (Exception e) {
                    if (!(e instanceof IOException)) throw e;
                    log.warn("Collector:Network busy Retry -> {} times",loopTime);
                    timeWatingStrategy.waiting(loopTime++);
                }
            }

        } catch (Exception e) {
            log.error("UnExpected exception",e);
        }
        return result;
    }
}
