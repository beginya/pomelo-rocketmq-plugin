package com.pomelo.rocketmq.plugin.core;

import com.pomelo.rocketmq.plugin.consumer.ListenerMethodPackage;
import com.pomelo.rocketmq.plugin.event.RocketMQListenersAssembledEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListenerAssembler extends InstantiationAwareBeanPostProcessorAdapter implements
        ApplicationEventPublisherAware, InitializingBean {

    private boolean assembled = false;
    private Map<String, List<Method>> listeners;
    private ApplicationEventPublisher applicationEventPublisher;
    private List<ListenerMethodPackage> listenerMethodPackages = new ArrayList<>();
    @Override
    public void afterPropertiesSet() throws Exception {
        if(listeners.size() == 0){
            assembled = true;
            applicationEventPublisher.publishEvent(new RocketMQListenersAssembledEvent(this, listenerMethodPackages));
        }

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(!assembled){
            List<Method> methods = listeners.get(beanName);
            if(methods != null){
                for(Method method : methods){
                    listenerMethodPackages.add(new ListenerMethodPackage(method, bean));
                }
                listeners.remove(beanName);
                if(listeners.size() == 0){
                    assembled = true;
                    applicationEventPublisher.publishEvent(new RocketMQListenersAssembledEvent(this, listenerMethodPackages));
                }
            }
        }
        return bean;
    }
}
