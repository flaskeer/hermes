package com.asterisk.opensource.task;

import com.asterisk.opensource.spider.transfer.NeteaseNewsTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class NeteaseQueueExecutorTask {

    private static final String  STORE_PATH = "netease.json";

    @Autowired
    private NeteaseNewsTransfer neteaseNewsTransfer;

    @Scheduled(fixedRate = 5000)
    public void schedule(){
        log.info("开始执行爬虫任务.......");
        neteaseNewsTransfer.storeUrsToQueue();
        neteaseNewsTransfer.parseAndStore(STORE_PATH);
        log.info("爬虫任务结束.....");
    }

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
