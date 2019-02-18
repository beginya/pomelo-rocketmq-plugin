package com.pomelo.rocketmq.plugin.consumer;

import com.pomelo.rocketmq.plugin.core.Constants;
import com.pomelo.rocketmq.plugin.core.MessageConverter;
import com.pomelo.rocketmq.plugin.core.MessageConverterAware;
import com.pomelo.rocketmq.plugin.core.RocketMQLifeCycle;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushConsumer extends DefaultMQPushConsumer implements RocketMQLifeCycle, MessageConverterAware {

    private boolean broadcasting = false;
    private MessageConverter messageConverter;
    private Map<String, Map<String, List<ListenerMethodPackage>>> listeners = new HashMap<>();
    @Override
    public void prepare(String cluster) {
        super.setInstanceName(cluster + "PushConsumer@" + Thread.currentThread().getId());
        super.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        super.registerMessageListener(new Listener());
        if(broadcasting){
            super.setMessageModel(MessageModel.BROADCASTING);
        }
    }

    @Override
    public void start(String cluster) throws MQClientException {

        if(listeners.isEmpty()){
            //输出日志
            return;
        }
        super.setSubscription(getSubscriptions(cluster));
        super.start();
    }

    @Override
    public void stop(String cluster) {
        super.shutdown();
    }

    public void putListener(String topic, String tags, ListenerMethodPackage listenerMethodPackage){
        /** 获取集群下Topic的Map*/
        Map<String, List<ListenerMethodPackage>> tagsMap = listeners.get(topic) == null ?  new HashMap<String, List<ListenerMethodPackage>>() : listeners.get(topic);
        listeners.put(topic, tagsMap);
        /** 获取Topic下Tags的Map*/
        List<ListenerMethodPackage> packageList = tagsMap.get(tags) == null ? new ArrayList<ListenerMethodPackage>() : tagsMap.get(tags);
        tagsMap.put(tags, packageList);
        /** 添加方法包*/
        packageList.add(listenerMethodPackage);

    }

    @Override
    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    /**
     * 获取指定集群下要被监听的topic和tags
     * @param cluster
     * @return
     */
    Map<String, String> getSubscriptions(String cluster){
        Map<String, String> subscriptions = new HashMap<>();
        for(Map.Entry<String, Map<String, List<ListenerMethodPackage>>> entry : listeners.entrySet()){
            subscriptions.put(entry.getKey(), generateTagsString(entry.getValue()));
        }
        return subscriptions;
    }

    /**
     * 生产tags字符串
     * @param tagsMap
     * @return
     */
    private String generateTagsString(Map<String, List<ListenerMethodPackage>> tagsMap){
        StringBuilder tags = new StringBuilder("");
        String separator = " || ";
        for(String tag : tagsMap.keySet()){
            tags.append(tag);
            tags.append(separator);
        }
        tags.delete(tags.length() - separator.length(), tags.length());
        return tags.toString();
    }


    public class Listener implements MessageListenerConcurrently{

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            try {
                return consumeMessage(messageExtList);
            } catch (Exception e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }

        private ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList){
            for(MessageExt message : messageExtList){
                String tags = StringUtils.isEmpty(message.getTags()) ? Constants.DEFAULT_TAGS : message.getTags();
                String topic = message.getTopic();
                //获取所有订阅该Topic和tags的方法包
                List<ListenerMethodPackage> methodPackageList = listeners.get(topic).get(tags);
                if(CollectionUtils.isEmpty(messageExtList)){
                    //输出告警日志
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                for(ListenerMethodPackage pack : methodPackageList){
                    if(pack.getParameterType().equals(MessageExt.class)){
                        ReflectionUtils.invokeMethod(pack.getMethod(), pack.getObject(), message);
                    } else {
                        Object object = messageConverter.parseObject(message.getBody(), pack.getParameterType());
                        ReflectionUtils.invokeMethod(pack.getMethod(), pack.getObject(), object);
                    }
                }

            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

    public boolean isBroadcasting() {
        return broadcasting;
    }

    public void setBroadcasting(boolean broadcasting) {
        this.broadcasting = broadcasting;
    }

    public MessageConverter getMessageConverter() {
        return messageConverter;
    }
}
