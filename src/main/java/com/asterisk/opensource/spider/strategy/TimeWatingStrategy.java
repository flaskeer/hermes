package com.asterisk.opensource.spider.strategy;

/**
 * @author dongh38@ziroom.com
 * @version 1.0.0
 */
public interface TimeWatingStrategy {

    /**
     *
     * @param loopTime always loop until to loopTime
     */
    void waiting(int loopTime);

    /**
     *
     * @return most retry time
     */
    int retryTimes();

}
