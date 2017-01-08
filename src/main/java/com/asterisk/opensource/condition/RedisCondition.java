package com.asterisk.opensource.condition;

import com.asterisk.opensource.spider.queue.QueueProvider;
import com.asterisk.opensource.utils.ConditionUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author donghao
 * @Date 17/1/8
 * @Time 上午10:36
 */
public class RedisCondition implements Condition {


    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
       return ConditionUtils.matchMessageQueue(QueueProvider.REDIS);
    }
}
