package com.asterisk.opensource.event;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/16
 * @Time 9:36
 * @Description
 * @Since 1.0.0
 */
public interface EventBusListener {

    /**
     * 事件监听器
     * @return 事件监听器名称
     */
    String getName();

}
