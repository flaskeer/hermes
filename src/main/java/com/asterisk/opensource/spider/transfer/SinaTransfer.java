package com.asterisk.opensource.spider.transfer;

import com.alibaba.fastjson.JSON;
import com.asterisk.opensource.domain.News;
import com.asterisk.opensource.utils.HttpUtil;
import com.asterisk.opensource.utils.MyStringUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/9
 * @Time 10:26
 * @Description
 * @Since 1.0.0
 */
@Slf4j
public class SinaTransfer {

    private static final String SINA_NEWS_URLS = "sina:news:urls";
    public static final String FAILURE_URLS = "failure_urls";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public  List<String> getUrls() {
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
        List<String> urls = Lists.newArrayListWithExpectedSize(num);
        List<String> list = stringRedisTemplate.opsForList().range(SINA_NEWS_URLS, 0, -1);
        if (list != null && list.size() > 0) {
            stringRedisTemplate.delete(SINA_NEWS_URLS);
        }
        for (Integer i = 0; i < num; i++) {
            String url = "http://platform.sina.com.cn/news/news_list?app_key=2872801998&show_cat=1&show_num=10&channel=mil&cat_1=dgby&format=json&tag=1&page=" + i + "&show_all=0&show_ext=1";
            stringRedisTemplate.opsForList().leftPush(SINA_NEWS_URLS,url);
        }
      return urls;
    }

    public  void parseAndStore(String path) {
        sinaQueue(path, SINA_NEWS_URLS);


    }

    private void sinaQueue(String path, String sinaNewsUrls) {
        while (true) {
            String url = stringRedisTemplate.opsForList().rightPop(sinaNewsUrls);
            if (url == null) {
                break;
            }
            String request = HttpUtil.getRequest(url);
            if (Strings.isNullOrEmpty(request)) {
                log.warn("请求url:{} 失败，将当前请求加入失败队列", url);
                stringRedisTemplate.opsForList().leftPush(FAILURE_URLS, url);
                continue;
            }
            List<LinkedHashMap<String, String>> datas = JsonPath.read(request, "$['result']['data'][*]");
            dealData(datas, path);
        }
    }

    public void failure(String path) {
        sinaQueue(path, FAILURE_URLS);
    }

    private void dealData(List<LinkedHashMap<String, String>> datas,String path) {
        datas.forEach(data -> {
            String jsonString = toJson(data);
            writeStringToFile(jsonString,new File(path));
        });
    }

    private static void writeStringToFile(String jsonString,File file) {
        try {
            FileUtils.writeStringToFile(file,jsonString + "\n",true);
        } catch (IOException e) {
            log.error("写入文件失败");
        }
    }

    private static String toJson(LinkedHashMap<String, String> data) {
        String id = data.get("id");
        String url =  data.get("url");
        String keywords =  data.get("keywords");
        String title =  data.get("title");
        title = MyStringUtil.decodeUnicode(title);
        News news = new News(id,url,keywords,title);
        return JSON.toJSONString(news);
    }


}
