package com.pomelo.rocketmq.plugin.core;

import com.pomelo.rocketmq.plugin.annotations.RocketMQListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ListenerCollector implements BeanFactoryPostProcessor {

    private final Map<String, List<Method>> listeners = new ConcurrentHashMap<>();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    private void processBeanClass(Class<?> clazz, String beanName){
        ReflectionUtils.doWithMethods(clazz, method -> {
            RocketMQListener annotation = AnnotatedElementUtils.findMergedAnnotation(method, RocketMQListener.class);
            //要求方法参数有且仅有一个
            if(method.getParameterCount() != 1){
                throw RocketMQExceptionEnum.INVALID_ARGS_FOUND_FOR_LISTENER.getException(Integer.toString(method.getParameterCount()));
            }
            List<Method> methods = listeners.get(beanName);
            if(methods == null){
                methods = new ArrayList<>();
                listeners.put(beanName, methods);
            }
            methods.add(method);

        }, method -> AnnotatedElementUtils.findMergedAnnotation(method, RocketMQListener.class) != null);
    }

    public Map<String, List<Method>> getListeners() {
        return listeners;
    }
}
