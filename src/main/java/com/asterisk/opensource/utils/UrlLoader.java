package com.asterisk.opensource.utils;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author donghao
 * @Date 17/1/7
 * @Time 上午10:29
 *  读取文件信息
 */
@Slf4j
public class UrlLoader {


    /**
     *   加载所有的URL  不指定路径则默认加载urls.txt
     * @param location
     * @return
     * @throws IOException
     */
    public static List<String> getUrls(String location) throws IOException {
        if (Strings.isNullOrEmpty(location)) {
            return readResources("classpath:urls.txt");
        } else {
           return readResources(location);
        }
    }

    /**
     *  加载默认的urls.txt
     * @return
     * @throws IOException
     */
    public static List<String> getDefaultUrls() throws IOException {
        return readResources("classpath:urls.txt");
    }

    private static List<String> readResources(String location) throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
        return IOUtils.readLines(inputStream);
    }

}
