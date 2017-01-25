package com.asterisk.opensource.spider.queue;


/**
 * @author donghao
 * @Date 17/1/7
 * @Time 上午11:07
 *  根据传入的名字来获取对应的消息队列
 */
public enum  QueueProvider {

    KAFKA("kafka"),RABBITMQ("rabbitmq"),REDIS("redis"),ROCKETMQ("rocketmq"),BLOCKINGQUEUE("blockingqueue");

    private String name;

    QueueProvider(String name) {
        this.name = name;
    }

    /**
     * 默认用blocking queue
     * @param queueName
     * @return
     */
    public QueueProvider getQueueType(String queueName) {
        QueueProvider[] providers = values();
        for (QueueProvider provider : providers) {
            if (provider.name.equalsIgnoreCase(queueName)) {
                return provider;
            }
        }
        return BLOCKINGQUEUE;
    }


}
