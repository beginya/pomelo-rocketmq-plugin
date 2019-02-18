package com.pomelo.rocketmq.plugin.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public abstract class AbstractRocketMQProducerImpl implements RocketMQProducer{

    protected Producer producer;
    protected String cluster;

    public AbstractRocketMQProducerImpl() {
    }

    public AbstractRocketMQProducerImpl(Producer producer, String cluster) {
        this.producer = producer;
        this.cluster = cluster;
    }

    @Override
    public SendResult send(String topic, Object object) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return producer.send(new Message(topic, producer.getMessageConverter().toJSONBytes(object)));
    }

    @Override
    public SendResult send(String topic, String tags, Object object) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return producer.send(new Message(topic,tags, producer.getMessageConverter().toJSONBytes(object)));
    }

    @Override
    public List<MessageQueue> fetchPublishMessageQueues(String topic) throws MQClientException {
        return producer.fetchPublishMessageQueues(topic);
    }

    @Override
    public SendResult send(Message msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return producer.send(msg);
    }

    @Override
    public SendResult send(Message msg, long timeout) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return producer.send(msg, timeout);
    }

    @Override
    public void send(Message msg, SendCallback sendCallback) throws InterruptedException, RemotingException, MQClientException {
        producer.send(msg, sendCallback);
    }

    @Override
    public void send(Message msg, SendCallback sendCallback, long timeout) throws InterruptedException, RemotingException, MQClientException {
        producer.send(msg, sendCallback, timeout);
    }

    @Override
    public void sendOneway(Message msg) throws InterruptedException, RemotingException, MQClientException {
        producer.sendOneway(msg);
    }

    @Override
    public SendResult send(Message msg, MessageQueue mq) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return producer.send(msg, mq);
    }

    @Override
    public SendResult send(Message msg, MessageQueue mq, long timeout) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return producer.send(msg, mq,timeout);
    }

    @Override
    public void send(Message msg, MessageQueue mq, SendCallback sendCallback) throws InterruptedException, RemotingException, MQClientException {
        producer.send(msg, mq,sendCallback);
    }

    @Override
    public void send(Message msg, MessageQueue mq, SendCallback sendCallback, long timeout) throws InterruptedException, RemotingException, MQClientException {
        producer.send(msg, mq,sendCallback,timeout);
    }

    @Override
    public void sendOneway(Message msg, MessageQueue mq) throws InterruptedException, RemotingException, MQClientException {
        producer.sendOneway(msg, mq);
    }

    @Override
    public SendResult send(Message msg, MessageQueueSelector selector, Object arg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return producer.send(msg, selector,arg);
    }

    @Override
    public SendResult send(Message msg, MessageQueueSelector selector, Object arg, long timeout) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return producer.send(msg, selector,arg,timeout);
    }

    @Override
    public void send(Message msg, MessageQueueSelector selector, Object arg, SendCallback sendCallback) throws InterruptedException, RemotingException, MQClientException {
        producer.send(msg, selector,arg,sendCallback);
    }

    @Override
    public void send(Message msg, MessageQueueSelector selector, Object arg, SendCallback sendCallback, long timeout) throws InterruptedException, RemotingException, MQClientException {
        producer.send(msg, selector,arg,sendCallback,timeout);
    }

    @Override
    public void sendOneway(Message msg, MessageQueueSelector selector, Object arg) throws InterruptedException, RemotingException, MQClientException {
        producer.sendOneway(msg, selector,arg);
    }

    @Override
    public TransactionSendResult sendMessageInTransaction(Message msg, Object object) throws MQClientException {
        return producer.sendMessageInTransaction(msg, object);
    }

    @Override
    public TransactionListener getTransactionListener() {
        return producer.getTransactionListener();
    }

    @Override
    public void setTransactionListener(TransactionListener transactionListener) {
        producer.setTransactionListener(transactionListener);
    }
}
