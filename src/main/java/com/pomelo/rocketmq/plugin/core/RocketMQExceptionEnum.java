package com.pomelo.rocketmq.plugin.core;

public enum RocketMQExceptionEnum {

    INVALID_ARGS_FOUND_FOR_LISTENER("601", "Expect 1 parameter for consumer but found %s"),

    NO_CONFIGURATION_FOUND_FOR_CLUSTER("602", "Method %s consumes from cluster '%s' which has no configuration"),

    CANNOT_MATCH_LISTENER_WITH_CONFIG("603", "Cannot match listener method %s with consumer config"),

    CLIENTS_IN_SAME_CLUSTER_SHOULD_HAVE_SAME_NAMESRVADDR("604", "Clients in same cluster should have same namesrvAddr");

    private String code;
    private String errMsg;

    RocketMQExceptionEnum(String code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public RocketMQException getException(Object... args) {
        return new RocketMQException(this.code, this.errMsg, args);
    }

    private class RocketMQException extends RuntimeException {
        public RocketMQException(String code, String errMsg, Object... args) {
            super(String.format("CODE: " + code + ", MESSAGE: " + errMsg, args));
        }
    }
}
