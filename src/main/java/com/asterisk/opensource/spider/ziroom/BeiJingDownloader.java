package com.asterisk.opensource.spider.ziroom;

import com.asterisk.opensource.domain.HouseInfo;
import com.asterisk.opensource.utils.JsoupUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.stream.Collectors.toList;


public class BeiJingDownloader {

    private BlockingQueue<String> failURLQueue = new LinkedBlockingQueue<>();

    private URLGenerator urlGenerator = new URLGenerator();






    public void addQueue(String url) {
        try {
            failURLQueue.put(url);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String takeQueue() {
        try {
            return failURLQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    public List<HouseInfo> parse(String url) {
        Document document = JsoupUtil.getDocument(url, urlGenerator.headers(url), 10);
        if (document == null) {
            addQueue(url);
            return Collections.emptyList();
        }
        Elements elements = document.select(".t_newlistbox").select(".clearfix");
        return elements.stream().filter(element -> !element.text().contains("默认排序"))
                .filter(element -> !element.hasClass("room_tags"))
                .filter(Objects::nonNull)
                .map(element -> {
                    String imgUrl = element.select(".img > a > img").attr("src");
                    String houseInfo = element.select(".txt > h3 > a").text();
                    String houseAddress = element.select(".txt > h4 > a").text();
                    String houseOverview = element.select(".detail > p").get(0).select("span").text();
                    String housePosition = element.select(".detail > p").get(1).select("span").text();
                    String houseTags = element.select(".room_tags > span").text();
                    String price = element.select(".priceDetail").select(".price").text();
                    String moreInfo = "http:" + element.select(".priceDetail").select(".more > a").attr("href");
                    return HouseInfo.builder().imgUrl(imgUrl).houseInfo(houseInfo).houseAddress(houseAddress)
                            .houseOverview(houseOverview).housePosition(housePosition).houseTags(houseTags)
                            .housePrice(price).roomInfoUrl(moreInfo).build();
                }).collect(toList());
    }

    public List<HouseInfo> download() {
        return urlGenerator.urlGenerator().parallelStream().map(this::parse).flatMap(Collection::stream).collect(toList());
    }

    public List<HouseInfo> recoup() {
        return parse(takeQueue());
    }


    public List<HouseInfo> agg() {
        List<HouseInfo> agg = Lists.newArrayList(download());
        while (! failURLQueue.isEmpty()) {
            List<HouseInfo> recoup = recoup();
            agg.addAll(recoup);
        }
        return agg;
    }



}
