package com.pomelo.rocketmq.plugin.core;

public interface MessageConverterAware {
    void setMessageConverter(MessageConverter messageConverter);
}
