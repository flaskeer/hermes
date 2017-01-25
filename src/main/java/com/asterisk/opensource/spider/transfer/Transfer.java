package com.asterisk.opensource.spider.transfer;


/**
 * @author donghao
 * @Date 17/1/7
 * @Time 下午5:15
 */
public interface Transfer {

    /**
     * 把数据推倒MQ里
     * @param
     */
    void insertQueue();


    void handle();

}
