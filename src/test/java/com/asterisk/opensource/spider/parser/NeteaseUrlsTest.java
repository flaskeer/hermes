package com.asterisk.opensource.spider.parser;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

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