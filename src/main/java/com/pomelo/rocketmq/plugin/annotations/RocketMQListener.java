package com.pomelo.rocketmq.plugin.annotations;

import com.pomelo.rocketmq.plugin.core.Constants;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface RocketMQListener {

    String topic();

    String tags() default Constants.DEFAULT_TAGS;

    String cluster() default Constants.DEFAULT_CLUSTER;
}
