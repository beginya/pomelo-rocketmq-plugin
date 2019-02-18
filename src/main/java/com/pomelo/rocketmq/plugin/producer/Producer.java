package com.pomelo.rocketmq.plugin.producer;

import com.pomelo.rocketmq.plugin.core.MessageConverter;
import com.pomelo.rocketmq.plugin.core.MessageConverterAware;
import com.pomelo.rocketmq.plugin.core.RocketMQLifeCycle;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;

public class Producer extends TransactionMQProducer implements RocketMQLifeCycle, MessageConverterAware {


    private MessageConverter messageConverter;

    @Override
    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Override
    public void prepare(String cluster) {
        super.setInstanceName(cluster + "Producer@" + Thread.currentThread().getId());
    }

    @Override
    public void start(String cluster) throws MQClientException {
        super.start();
    }

    @Override
    public void stop(String cluster) {
        super.shutdown();
    }

    public MessageConverter getMessageConverter() {
        return messageConverter;
    }
}
