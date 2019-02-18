package com.pomelo.rocketmq.plugin.producer;

public interface RocketMQProducer extends ProducerExecutor {

    ProducerExecutor cluster(String cluster);
}
