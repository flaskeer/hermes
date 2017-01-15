package com.asterisk.opensource.task;

import com.asterisk.opensource.spider.transfer.NeteaseNewsTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by dudycoco on 17-1-15.
 */
@Component
@Slf4j
public class NeteaseFailTureExecutorTask {
    private static final String STORE_PATH = "netease.json";

    @Autowired
    private NeteaseNewsTransfer neteaseNewsTransfer;

    /**
     * 任务补偿 把失败的任务队列定时重试
     */
    @Scheduled(fixedDelay = 5000)
    public void failTask() {
        log.info("开始重新执行失败的任务.....");
        neteaseNewsTransfer.failure(STORE_PATH);
        log.info("失败任务执行完毕.....");
    }
}
