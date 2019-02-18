package com.pomelo.rocketmq.plugin.core;

import org.apache.rocketmq.client.exception.MQClientException;

public interface RocketMQLifeCycle {

    void prepare(String cluster);

    void start(String cluster) throws MQClientException;

    void stop(String cluster);
}
