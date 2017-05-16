package com.asterisk.opensource.spider.strategy;

/**
 * @author dongh38@ziroom.com
 * @version 1.0.0
 */
public class DefaultTimeWaitingStrategy implements TimeWatingStrategy {

    private final long timeWaitingThreshold;

    private final long timewaiting;

    private final int retryTime;


    public DefaultTimeWaitingStrategy() {
        this(10000, 500, 10);
    }

    /**
     * @param timeWaitingThreshold 超时等待阈值(最多等待阈值指定时间然后进入下一次请求尝试)
     * @param timewaiting          起始等待时间
     * @param retryTime            重试次数(超过重试次数抛异常)
     */
    public DefaultTimeWaitingStrategy(long timeWaitingThreshold, long timewaiting, int retryTime) {
        this.timeWaitingThreshold = timeWaitingThreshold;
        this.timewaiting = timewaiting;
        this.retryTime = retryTime;
    }

    @Override
    public void waiting(int loopTime) {
        try {
            long sleepTime = this.timewaiting * (2 << loopTime);
            sleepTime = Math.min(sleepTime, timeWaitingThreshold);
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public int retryTimes() {
        return retryTime;
    }
}
