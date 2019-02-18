package com.pomelo.rocketmq.plugin.core;

import java.lang.reflect.Type;

public interface MessageConverter {

    byte [] toJSONBytes(Object object);

    Object parseObject(byte [] bytes, Type clazz);
}
