package com.asterisk.opensource.spider.processor;

import java.util.List;

/**
 * @author donghao
 * @Date 17/1/7
 * @Time 上午11:51
 */
public interface JsonProcessor<T> extends Processor{


     List<T> jsonProcess(String json);


}
