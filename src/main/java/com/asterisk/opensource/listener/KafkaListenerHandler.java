package com.asterisk.opensource.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;

/**
 * @author donghao
 * @Date 17/1/8
 * @Time 下午5:14
 */

public class KafkaListenerHandler {

    @KafkaListener(id = "id0",topicPartitions = {@TopicPartition(topic = "spider",partitions = {"0"})})
    public void listenPartition0(ConsumerRecord<?,?> record) {

    }
}
