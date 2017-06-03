package com.asterisk.opensource.spider.ziroom;

import com.asterisk.opensource.domain.HouseInfo;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by donghao on 2017/6/3.
 */
public class BeiJingDownloaderTest {


    @Test
    public void download() throws Exception {
        BeiJingDownloader downloader = new BeiJingDownloader();
        List<HouseInfo> houseInfos = downloader.agg();
        houseInfos.stream().filter(houseInfo -> houseInfo.getHouseAddress().contains("育新")).forEach(System.out::println);

    }

    @Test
    public void test_url() {
        URLGenerator urlGenerator = new URLGenerator();
        urlGenerator.urlGenerator().forEach(System.out::println);
    }

}