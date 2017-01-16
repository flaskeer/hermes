package com.asterisk.opensource.spider.processor.impl;

import com.asterisk.opensource.domain.News;
import com.asterisk.opensource.spider.parser.HtmlParser;
import com.asterisk.opensource.spider.parser.JsonPipeline;
import com.asterisk.opensource.utils.HttpTookit;
import com.asterisk.opensource.utils.MyStringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.Map;

public class XinLanNewsPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public static String[] getUrls() {
        String getUrl = "http://platform.sina.com.cn/news/news_list";
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
        String getData = HttpTookit.doGet(getUrl, params, "utf-8");
        String total = JsonPath.read(getData, "$['result']['total']");
        Integer num = Integer.parseInt(total);
        String[] urls = new String[num];
        for (int x = 1; x <= num; x++) {
            String url = "http://platform.sina.com.cn/news/news_list?app_key=2872801998&show_cat=1&show_num=10&channel=mil&cat_1=dgby&format=json&tag=1&page=" + x + "&show_all=0&show_ext=1";
            urls[x - 1] = url;
        }

        return urls;
    }

    public static void main(String[] args) {
        String[] urls = getUrls();
        Spider.create(new XinLanNewsPageProcessor())
                // 从"https://github.com/code4craft"开始抓
                .addPipeline(new ConsolePipeline())
                .addPipeline(new JsonPipeline())
                .addUrl(urls)
                // 开启5个线程抓取
                .thread(10)
                // 启动爬虫
                .run();
    }

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        String json = page.getRawText();
        List<JSONObject> datas = JsonPath.read(json, "$['result']['data'][*]");
        List<Request> requests = Lists.newArrayList();
        for (JSONObject jsonObject : datas) {
            String id = (String) jsonObject.get("id");
            String url = (String) jsonObject.get("url");
            String keywords = (String) jsonObject.get("keywords");
            String title = (String) jsonObject.get("title");
            title = MyStringUtil.decodeUnicode(title);
            News news = new News(url, keywords, title);
            news = new HtmlParser(news).parseHtmlToString();
            page.putField("news", news);
        }
    }


}
