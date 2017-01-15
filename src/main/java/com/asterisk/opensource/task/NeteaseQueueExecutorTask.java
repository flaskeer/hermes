package com.asterisk.opensource.task;

import com.asterisk.opensource.spider.transfer.NeteaseNewsTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by dudycoco on 17-1-15.
 */
@Slf4j
@Component
public class NeteaseQueueExecutorTask {

    private static final String  SOTRE_PATH = "netease.json";

    @Autowired
    private NeteaseNewsTransfer neteaseNewsTransfer;

    @Scheduled(fixedRate = 5000)
    public void schedule(){
        log.info("开始执行爬虫任务.......");
        neteaseNewsTransfer.storeUrsToQueue();
        neteaseNewsTransfer.parseAndStore(SOTRE_PATH);
        log.info("爬虫任务结束.....");
    }
}
