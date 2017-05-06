package com.asterisk.opensource.spider.fetch;

import org.junit.Test;

/**
 * @author donghao
 * @Date 17/5/6
 * @Time 下午4:05
 */
public class NeteaseUrlsTest {
    @Test
    public void test_urls() throws Exception {
        NeteaseUrls.urls().forEach(System.out::println);

    }

}