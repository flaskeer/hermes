package com.asterisk.opensource.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * @author donghao
 * @Date 17/1/25
 * @Time 上午11:01
 */
@Slf4j
public class FileUtil {

    public static void writeStringToFile(String jsonString, File file) {
        try {
            org.apache.commons.io.FileUtils.writeStringToFile(file, jsonString + "\n", true);
        } catch (IOException e) {
            log.error("写入文件失败");
        }
    }
}
