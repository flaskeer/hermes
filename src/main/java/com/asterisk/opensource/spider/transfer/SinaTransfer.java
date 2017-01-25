package com.asterisk.opensource.spider.transfer;

import com.asterisk.opensource.async.ExceptionHandlingAsyncTaskExecutor;
import com.asterisk.opensource.service.NewsService;
import com.asterisk.opensource.utils.HttpUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.asterisk.opensource.constants.Constants.FAILURE_URLS;
import static com.asterisk.opensource.constants.Constants.SINA_NEWS_URLS;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/9
 * @Time 10:26
 * @Description
 * @Since 1.0.0
 */
@Slf4j
public class SinaTransfer implements Transfer{


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ExceptionHandlingAsyncTaskExecutor asyncTaskExecutor;

    @Autowired
    private NewsService newsService;

    @Override
    public void insertQueue() {
        String listUrl = "http://platform.sina.com.cn/news/news_list";
        Map<String, String> params = Maps.newHashMap();
        params.put("app_key", "2872801998");
        params.put("channel", "mil");
        params.put("cat_1", "dgby");
        params.put("show_all", "0");
        params.put("show_cat", "1");
        params.put("show_ext", "1");
        params.put("tag", "1");
        params.put("format", "json");
        params.put("page", "1");
        params.put("show_num", "10");
        String data = HttpUtil.getRequest(listUrl, params, "utf-8");
        String total = JsonPath.read(data, "$['result']['total']");
        Integer num = Integer.parseInt(total);
        List<String> list = stringRedisTemplate.opsForList().range(SINA_NEWS_URLS, 0, -1);
        if (list != null && list.size() > 0) {
            stringRedisTemplate.delete(SINA_NEWS_URLS);
        }
        for (Integer i = 0; i < num; i++) {
            String url = "http://platform.sina.com.cn/news/news_list?app_key=2872801998&show_cat=1&show_num=10&channel=mil&cat_1=dgby&format=json&tag=1&page=" + i + "&show_all=0&show_ext=1";
            stringRedisTemplate.opsForList().leftPush(SINA_NEWS_URLS, url);
        }
    }

    @Override
    public void handle() {
        writeDb();
    }


    public void writeDb() {
        log.info("开始写入数据库中存储");
        dbQueue(SINA_NEWS_URLS);
    }

    public void failureToDb() {
        log.info("将失败任务插入数据库中");
        dbQueue(FAILURE_URLS);
    }

    private void writeDb(List<LinkedHashMap<String, String>> datas) {
        newsService.insert(datas);
    }

    private void dbQueue(String sinaNewsUrls) {
        while (true) {
            String url = stringRedisTemplate.opsForList().rightPop(sinaNewsUrls);
            if (url == null) {
                break;
            }
            asyncTaskExecutor.execute(() -> {
                String request = HttpUtil.getRequest(url);
                if (Strings.isNullOrEmpty(request)) {
                    log.warn("请求url:{} 失败，将当前请求加入失败队列", url);
                    stringRedisTemplate.opsForList().leftPush(FAILURE_URLS, url);
                } else {
                    List<LinkedHashMap<String, String>> datas = JsonPath.read(request, "$['result']['data'][*]");
                    writeDb(datas);
                }
            });

        }
    }









}
