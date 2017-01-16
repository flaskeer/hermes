package com.asterisk.opensource.spider.transfer;

import com.alibaba.fastjson.JSON;
import com.asterisk.opensource.async.ExceptionHandlingAsyncTaskExecutor;
import com.asterisk.opensource.domain.NeteaseNews;
import com.asterisk.opensource.utils.HttpUtil;
import com.asterisk.opensource.vo.NeteaseNewsVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by dudycoco on 17-1-12.
 */
@Slf4j
public class NeteaseNewsTransfer {

    public static final String FAILURE_URLS = "netease_guonei_failure_urls";
    private static final String NETEASE_GUONEI_NEWS_URLS = "netease:guonei:news:urls";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ExceptionHandlingAsyncTaskExecutor asyncTaskExecutor;

    public void storeUrsToQueue() {
        String starturl = "http://temp.163.com/special/00804KVA/cm_guonei.js";
        Map<String, String> params = Maps.newHashMap();
        params.put("callback", "data_callback");
        List<String> urls = Lists.newArrayList();
        urls.add(starturl);
        int x = 2;
        while (true) {
            int subpostion = starturl.lastIndexOf(".");
            String url = starturl.substring(0, subpostion);
            if (x < 10) {
                url = url + "_0" + x + ".js";
            } else {
                url = url + x + ".js";
            }
            String requestUrl = getRequestUrl(url, params);
            boolean flag = isAcessAndPushUrlToQueue(requestUrl);
            if (flag) {
                x++;
            } else {
                break;
            }
        }
    }

    private boolean isAcessAndPushUrlToQueue(String requestUrl) {
        boolean flag;
        if (Strings.isNullOrEmpty(requestUrl)) {
            flag = false;
            return flag;
        }
        CloseableHttpClient client = HttpUtil.getHttpClient();
        HttpGet httpGet = new HttpGet(requestUrl);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            int statuCode = response.getStatusLine().getStatusCode();
            if (400 < statuCode) {
                stringRedisTemplate.opsForList().leftPush(FAILURE_URLS, requestUrl);
                return false;
            }
            stringRedisTemplate.opsForList().leftPush(NETEASE_GUONEI_NEWS_URLS, requestUrl);
        } catch (IOException e) {
            log.error("访问" + requestUrl + "出错");
        }
        return false;
    }

    private void netEaseQueue(String storePath, String neteaseGuoneiNewsUrls) {
        while (true) {
            String url = stringRedisTemplate.opsForList().rightPop(neteaseGuoneiNewsUrls);
            if (Strings.isNullOrEmpty(url)) {
                break;
            }
            asyncTaskExecutor.execute(() -> {
                String result = HttpUtil.getRequest(url, "gbk");
                datapersisted(result, storePath);
            });

        }
    }

    public void failure(String storePath) {
        netEaseQueue(storePath, FAILURE_URLS);
    }

    public void parseAndStore(String storePath) {
        netEaseQueue(storePath, NETEASE_GUONEI_NEWS_URLS);
    }


    private void datapersisted(String result, String storePath) {
        List<NeteaseNewsVo> list = beanToJson(result);
        List<NeteaseNews> neteaseNewsList = trans(list);
        neteaseNewsList.forEach(obj -> {
            String json = JSON.toJSONString(obj);
            writeJsonToFile(json + "\n", storePath);
        });
    }

    private List<NeteaseNews> trans(List<NeteaseNewsVo> list) {
        List<NeteaseNews> neteaseNewsList = Lists.newArrayList();
        list.forEach(vo -> {
            NeteaseNews neteaseNews = new NeteaseNews(vo.getTitle(), listToString(vo.getKeywords()), vo.getCommenturl(), vo.getTlastid());
            neteaseNewsList.add(neteaseNews);
        });
        return neteaseNewsList;
    }

    private String listToString(Object object) {
        return JSON.toJSONString(object).replace("\"","").replace("[","").replace("]","")
                .replace("{","").replace("}","");

    }

    private static void writeJsonToFile(String json, String storePath) {
        try {
            FileUtils.writeStringToFile(new File(storePath), json, true);
        } catch (IOException e) {
            log.error("存储文件失败");
        }
    }

    private List<NeteaseNewsVo> beanToJson(String result) {
        String[] tmpJsonStr = result.split("data_callback\\(");
        String json = tmpJsonStr[1].split("\\)")[0];
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<NeteaseNewsVo>>() {
        }.getType());
    }

    private String getRequestUrl(String url, Map<String, String> params) {
        String requestUrl;
        try {
            requestUrl = HttpUtil.getParameters(url, params, "utf-8");
            return requestUrl;
        } catch (IOException e) {
            log.error("url添加参数出错");
        }
        return null;
    }


}
