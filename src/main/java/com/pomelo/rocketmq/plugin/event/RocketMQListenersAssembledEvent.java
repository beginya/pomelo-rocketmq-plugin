package com.pomelo.rocketmq.plugin.event;

import com.pomelo.rocketmq.plugin.consumer.ListenerMethodPackage;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class RocketMQListenersAssembledEvent extends ApplicationEvent {

    private final List<ListenerMethodPackage> listenerMethodPackages;

    public RocketMQListenersAssembledEvent(Object source, List<ListenerMethodPackage> listenerMethodPackages) {
        super(source);
        this.listenerMethodPackages = listenerMethodPackages;
    }

    public List<ListenerMethodPackage> getListenerMethodPackages() {
        return listenerMethodPackages;
    }
}
