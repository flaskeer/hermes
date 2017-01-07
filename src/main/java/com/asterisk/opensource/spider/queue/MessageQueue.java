package com.asterisk.opensource.spider.queue;


/**
 * @author donghao
 * @Date 17/1/7
 * @Time 上午11:06
 */
public interface MessageQueue {


    QueueProvider queueName();

    /**
     *  消费队列
     * @param <T>
     * @return
     */
    <T> T consume();

    /**
     *  弹出队列
     * @param data
     * @param <T>
     */
    <T> void provide(T data);
}
