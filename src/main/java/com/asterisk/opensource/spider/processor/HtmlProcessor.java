package com.asterisk.opensource.spider.processor;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * @author donghao
 * @Date 17/1/7
 * @Time 上午11:52
 */
public interface HtmlProcessor extends Processor{

    <T> List<T> htmlProcess(Document document);
}
