package com.pomelo.rocketmq.plugin.consumer;

import java.lang.reflect.Method;

public class ListenerMethodPackage {

    private Method method;
    private Object object;

    public ListenerMethodPackage(Method method, Object object) {
        this.method = method;
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Class<?> getParameterType(){
        return method.getParameterTypes()[0];
    }
}
