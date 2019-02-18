package com.pomelo.rocketmq.plugin.core;

import com.pomelo.rocketmq.plugin.consumer.PushConsumer;
import com.pomelo.rocketmq.plugin.producer.Producer;
import org.apache.rocketmq.client.ClientConfig;
import org.apache.rocketmq.client.exception.MQClientException;

import java.util.Arrays;

public class RocketMQInstance {
    protected Producer producer;
    protected PushConsumer consumer;
    protected MessageConverter messageConverter;

    protected void prepare(String cluster) {
        validateNamesrvAddr();
        prepare(consumer, cluster);
        prepare(producer, cluster);
    }

    private void prepare(RocketMQLifeCycle instance, String cluster) {
        if (instance != null) {
            instance.prepare(cluster);
        }
    }

    protected void stop(String cluster) {
        stop(producer, cluster);
        stop(consumer, cluster);
    }

    private void stop(RocketMQLifeCycle instance, String cluster) {
        if (instance != null) {
            instance.stop(cluster);
        }
    }

    protected void start(String cluster) throws MQClientException {
        if (messageConverter == null) {
            messageConverter = new DefaultMessageConverter();
        }
        start(producer, cluster);
        start(consumer, cluster);
    }

    private void start(RocketMQLifeCycle instance, String cluster) throws MQClientException {
        if (instance == null) {
            return;
        }
        if (instance instanceof MessageConverterAware) {
            ((MessageConverterAware) instance).setMessageConverter(messageConverter);
        }
        instance.start(cluster);
    }

    protected boolean allClientsAreNull() {
        return producer == null && consumer == null;
    }

    void validateNamesrvAddr() {
        ClientConfig[] clients = {producer, consumer};
        String[] sortedAddr = null;
        for (ClientConfig client : clients) {
            if (client != null) {
                String[] thisSortedAddr = client.getNamesrvAddr().replace(" ", "").split(";");
                Arrays.sort(thisSortedAddr);
                if(sortedAddr == null){
                    sortedAddr = thisSortedAddr;
                }else {
                    if(!Arrays.equals(sortedAddr, thisSortedAddr)){
                        throw RocketMQExceptionEnum.CLIENTS_IN_SAME_CLUSTER_SHOULD_HAVE_SAME_NAMESRVADDR.getException();
                    }
                }
            }
        }
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public PushConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(PushConsumer consumer) {
        this.consumer = consumer;
    }

}
