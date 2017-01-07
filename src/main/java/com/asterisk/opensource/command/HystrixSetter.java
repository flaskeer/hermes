package com.asterisk.opensource.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class HystrixSetter {

    private String commandGroupKey = "spider-command-group-key";
    private String commandKey = "spider-command-key";

    private int eitTimeout = 1000;
    private int etimeout = 1000;

    private int fismRequests = 10;

    private int cbRequests = 20;
    private int cbSleepWindow = 5;
    private int cbErrorRate = 50;

    private String threadPoolKey = "spider-threadpool-key";
    private int threadPoolCoreSize = 10;
    private int threadPoolQueueSize = 5;

    public static HystrixSetter build() {
        return new HystrixSetter();
    }

    public static HystrixSetter buildSenior() {
        return new HystrixSetter(80000, 80000, 30, 50, 50, 10);
    }

    public static HystrixSetter buildSuper() {
        return new HystrixSetter(80000, 80000, 50, 50, 100, 15);
    }

    public HystrixSetter() {}

    public HystrixSetter(int eitTimeout, int etimeout,
                         int cbRequests, int cbErrorRate,int threadPoolCoreSize,
                         int threadPoolQueueSize) {
        this.eitTimeout = eitTimeout;
        this.etimeout = etimeout;
        this.cbRequests = cbRequests;
        this.cbErrorRate = cbErrorRate;
        this.threadPoolCoreSize = threadPoolCoreSize;
        this.threadPoolQueueSize = threadPoolQueueSize;
    }



}
