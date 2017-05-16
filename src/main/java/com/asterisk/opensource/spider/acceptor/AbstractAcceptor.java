package com.asterisk.opensource.spider.acceptor;

import com.asterisk.opensource.spider.strategy.TimeWatingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author dongh38@ziroom.com
 * @version 1.0.0
 *  接受处理完的数据进行分析
 */
@Slf4j
public abstract class AbstractAcceptor<T> implements Consumer<T> {


    private TimeWatingStrategy timeWatingStrategy;

    public AbstractAcceptor(TimeWatingStrategy timeWatingStrategy) {
        this.timeWatingStrategy = timeWatingStrategy;
    }

    protected abstract void consumeLogic(T t) throws Exception;

    @Override
    public void accept(T t) {
        log.info("{} accepting...", getClass().getSimpleName());
        int retryTime = timeWatingStrategy.retryTimes();
        try {
            int loopTime = 1;
            while (retryTime > loopTime) {
                try {
                    consumeLogic(t);
                    break;
                } catch (Exception e) {
                    if (!(e instanceof IOException)) throw e;
                    log.info("Acceptor:Network busy  Retry -> {} times", loopTime);
                    this.timeWatingStrategy.waiting(loopTime++);
                }
            }
        } catch (Exception e) {
            log.error("UnExcepted exception",e);
        }
    }

}
