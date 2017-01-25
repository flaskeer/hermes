package com.asterisk.opensource.spider.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author donghao
 * @Date 17/1/7
 * @Time 上午11:26
 */
public class RedisMessageQueue implements MessageQueue{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public QueueProvider queueName() {
        return QueueProvider.REDIS;
    }

    @Override
    public <T> T consume() {
        return null;
    }

    @Override
    public <T> void provide(T data) {

    }
}
