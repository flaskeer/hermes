package com.asterisk.opensource.spider.transfer;

import com.asterisk.opensource.domain.Food;
import com.asterisk.opensource.spider.processor.impl.FoodProcessor;
import com.asterisk.opensource.utils.JsoupUtil;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author donghao
 * @Date 17/1/7
 * @Time 下午4:59
 */
public class FoodTransfer {

    private static void foodExactor(String url, Map<String, String> headers) {
        String json = JsoupUtil.getJson(url, headers);
        FoodProcessor foodProcessor = new FoodProcessor();
        List<Food> list = foodProcessor.jsonProcess(json);
        System.out.println(list);
    }

    private static Map<String, String> headers() {
        Map<String,String> headers = Maps.newHashMap();
        headers.put("Cookie","connect.sid=s%3AKBKP-rYoXCuqFRrTUnadJfiODBB_9Jma.Sxy%2BmiFuhf7O4EuwTnlssmsF8188byNMgvkVfG0ci4A; rb_city=140; Hm_lvt_6ad0f9786e9035e0275f33c495e02f52=1473994512; Hm_lpvt_6ad0f9786e9035e0275f33c495e02f52=1473995526");
        headers.put("Host","enjoy.ricebook.com");
        headers.put("Upgrade-Insecure-Requests","1");
        return headers;
    }


    public static void main(String[] args) {
        foodExactor("http://enjoy.ricebook.com/innerpage/140,5,0",headers());
    }


}
