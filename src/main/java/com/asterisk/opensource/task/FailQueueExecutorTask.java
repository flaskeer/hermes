package com.asterisk.opensource.task;

import com.asterisk.opensource.spider.transfer.SinaTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/9
 * @Time 16:14
 * @Description
 * @Since 1.0.0
 */
@Slf4j
@Component
public class FailQueueExecutorTask {

    private static final String STORE_PATH = "sina.json";

    @Autowired
    private SinaTransfer sinaTransfer;

    @Scheduled(fixedDelay = 5000)
    public void failTask() {
        log.info("开始重新执行失败的任务.....");
        sinaTransfer.failure(STORE_PATH);
        log.info("失败任务执行完毕.....");
    }

}
