package com.pomelo.rocketmq.plugin.core;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;

public class DefaultMessageConverter implements MessageConverter {
    @Override
    public byte[] toJSONBytes(Object object) {
        return JSONObject.toJSONBytes(object);
    }

    @Override
    public Object parseObject(byte[] bytes, Type clazz) {
        return JSONObject.parseObject(bytes, clazz);
    }
}
