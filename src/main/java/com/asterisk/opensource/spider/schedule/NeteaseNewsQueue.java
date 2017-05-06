package com.asterisk.opensource.spider.schedule;

import com.asterisk.opensource.spider.fetch.NeteaseUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import static com.asterisk.opensource.constants.Constants.NETEASE__NEWS_URLS;

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
    }


    public void addUrlList() {
        NeteaseUrls.urls().parallelStream().forEach(url -> stringRedisTemplate.opsForList().leftPush(NETEASE__NEWS_URLS,url));
    }
}
