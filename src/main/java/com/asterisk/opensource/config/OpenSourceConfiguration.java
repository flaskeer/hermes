package com.asterisk.opensource.config;

import com.asterisk.opensource.spider.queue.RedisMessageQueue;
import com.asterisk.opensource.spider.transfer.NeteaseNewsTransfer;
import com.asterisk.opensource.spider.transfer.SinaTransfer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSourceConfiguration {

    @Bean
    public SinaTransfer sinaTransfer() {
        return new SinaTransfer();
    }

    @Bean
    public NeteaseNewsTransfer neteaseNewsTransfer() {
        return new NeteaseNewsTransfer();
    }

    @Bean
    public RedisMessageQueue redisMessageQueue() {
        return new RedisMessageQueue();
    }

}
