package com.asterisk.opensource.spider.schedule;

import com.asterisk.opensource.event.ExecutionEventBusFactory;
import com.asterisk.opensource.event.SpiderExecutionEvent;
import com.asterisk.opensource.spider.collector.NeteaseUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import static com.asterisk.opensource.constants.Constants.NETEASENEWS_EVENT_NAME;
import static com.asterisk.opensource.constants.Constants.NETEASE__NEWS_URLS;
import static com.asterisk.opensource.event.SpiderExecutionEvent.SpiderType.NETEASE;

/**
 * @author donghao
 * @Date 17/5/6
 * @Time 下午4:32
 */
@Component
public class NeteaseNewsQueue {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public void addUrl(String url) {
        stringRedisTemplate.opsForList().leftPush(NETEASE__NEWS_URLS,url);
        ExecutionEventBusFactory.getInstance(NETEASENEWS_EVENT_NAME).post(SpiderExecutionEvent.builder().spiderType(NETEASE).url(url).build());
    }


    public void addUrlList() {
        NeteaseUrls.urls().parallelStream().forEach(url -> stringRedisTemplate.opsForList().leftPush(NETEASE__NEWS_URLS,url));
    }
}
