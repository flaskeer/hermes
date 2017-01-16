package com.asterisk.opensource.task;

import com.asterisk.opensource.spider.transfer.SinaTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/9
 * @Time 15:23
 * @Description
 * @Since 1.0.0
 */
@Component
@Slf4j
public class SpdierExecutorTask {

    private static final String STORE_PATH = "sina.json";

    @Autowired
    private SinaTransfer sinaTransfer;


    /**
     * 这个做一次全量更新
     */
    @Scheduled(fixedRate = 50000000)
    public void schedule() {
        log.info("开始执行爬虫任务.......");
        sinaTransfer.getUrls();
//        sinaTransfer.parseAndStore(STORE_PATH);
        sinaTransfer.writeDb();
        log.info("爬虫任务结束.....");
    }



}
