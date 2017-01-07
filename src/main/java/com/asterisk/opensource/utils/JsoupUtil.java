package com.asterisk.opensource.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

/**
 * @author donghao
 * @Date 17/1/7
 * @Time 下午4:09
 */
@Slf4j
public class JsoupUtil {

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";

    public static Document getDocument(String url, int timeout, Map<String,String> headers,int retryTimes)  {
        Document document = null;
        Connection connection = Jsoup.connect(url).timeout(timeout).userAgent(USER_AGENT).ignoreContentType(true);
        headers.forEach(connection::header);
        for (int i = 1; i <= retryTimes; i++) {
            try {
                document = connection.get();
                break;
            } catch (IOException e) {
                log.error("get document error:{},",e);
                log.info("当前重试次数:{}",i);
                if (i == retryTimes) {
                    log.error("已经超出最大重试次数,请检查请求是否正确");
                }
            }
        }

        return document;
    }

    public static Document getDocument(String url,Map<String,String> headers,int retryTimes)  {
        return getDocument(url,3000,headers,retryTimes);
    }

    /**
     *  默认重试5次
     * @param url
     * @param headers
     * @return
     */
    public static Document getDocument(String url,Map<String,String> headers) {
        return getDocument(url,headers,5);
    }



    public static String getJson(String url,Map<String,String> headers,int timeout,int retryTimes) {
        String json = null;
        Connection connection = Jsoup.connect(url).timeout(timeout).userAgent(USER_AGENT).ignoreContentType(true);
        headers.forEach(connection::header);
        for (int i = 1; i <= retryTimes; i++) {
            try {
                json = connection.execute().body();
                break;
            } catch (IOException e) {
                log.error("get json error:{},",e);
                log.info("当前重试次数:{}",i);
                if (i == retryTimes) {
                    log.error("已经超出最大重试次数,请检查请求是否正确");
                }
            }
        }
        return json;
    }


    public static String getJson(String url,Map<String,String> headers,int retryTimes)  {
        return getJson(url,headers,3000,retryTimes);
    }

    /**
     *  默认重试5次
     * @param url
     * @param headers
     * @return
     */
    public static String getJson(String url,Map<String,String> headers) {
        return getJson(url,headers,5);
    }




}
