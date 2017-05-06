package com.asterisk.opensource.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/16
 * @Time 9:52
 * @Description
 * @Since 1.0.0
 */
@Getter
@Setter
@Builder
public class SpiderExecutionEvent extends ExecutionEvent{

    public enum SpiderType {
        NETEASE
    }

    private SpiderType spiderType;

    private String url;
}
