package com.pomelo.rocketmq.plugin.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public interface ProducerExecutor {

    SendResult send(String topic, Object object) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;

    SendResult send(String topic, String tags, Object object) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;

    List<MessageQueue> fetchPublishMessageQueues(final String topic) throws MQClientException;

    SendResult send(Message msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;

    SendResult send(Message msg, long timeout) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;

    void send(final Message msg, final SendCallback sendCallback) throws InterruptedException, RemotingException, MQClientException;

    void send(final Message msg, final SendCallback sendCallback, final long timeout) throws InterruptedException, RemotingException, MQClientException;

    void sendOneway(final Message msg) throws InterruptedException, RemotingException, MQClientException;

    SendResult send(final Message msg, final MessageQueue mq) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;

    SendResult send(final Message msg, final MessageQueue mq, final long timeout) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;

    void send(final Message msg, final MessageQueue mq, final SendCallback sendCallback) throws InterruptedException, RemotingException, MQClientException;

    void send(final Message msg, final MessageQueue mq, final SendCallback sendCallback, final long timeout) throws InterruptedException, RemotingException, MQClientException;

    void sendOneway(final Message msg, final MessageQueue mq) throws InterruptedException, RemotingException, MQClientException;

    SendResult send(final Message msg, final MessageQueueSelector selector, final Object arg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;

    SendResult send(final Message msg, final MessageQueueSelector selector, final Object arg, final long timeout) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;

    void send(final Message msg, final MessageQueueSelector selector, final Object arg, final SendCallback sendCallback) throws InterruptedException, RemotingException, MQClientException;

    void send(final Message msg, final MessageQueueSelector selector, final Object arg, final SendCallback sendCallback, final long timeout) throws InterruptedException, RemotingException, MQClientException;

    void sendOneway(final Message msg, final MessageQueueSelector selector, final Object arg) throws InterruptedException, RemotingException, MQClientException;

    TransactionSendResult sendMessageInTransaction(final Message msg, final Object object) throws MQClientException;

    TransactionListener getTransactionListener();

    void setTransactionListener(TransactionListener transactionListener);



}
