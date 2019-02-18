package com.pomelo.rocketmq.plugin.annotations;

import com.pomelo.rocketmq.plugin.core.RocketMQAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启mq
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({RocketMQAutoConfiguration.class})
public @interface EnableRocketMQ {
}
