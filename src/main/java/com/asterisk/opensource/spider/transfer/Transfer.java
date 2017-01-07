package com.asterisk.opensource.spider.transfer;

import com.asterisk.opensource.spider.processor.Processor;
import com.asterisk.opensource.spider.queue.MessageQueue;

/**
 * @author donghao
 * @Date 17/1/7
 * @Time 下午5:15
 */
public interface Transfer {

    /**
     * 把数据推倒MQ里
     * @param processor
     */
    void transfer(MessageQueue messageQueue,Processor processor);

}
