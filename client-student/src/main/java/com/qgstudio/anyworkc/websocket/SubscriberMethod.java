package com.qgstudio.anyworkc.websocket;

import java.lang.reflect.Method;

public class SubscriberMethod {
    Class<?> type;
    ThreadMode threadMode;
    Method method;

    public SubscriberMethod(Class<?> type, ThreadMode threadMode, Method method) {
        this.type = type;
        this.threadMode = threadMode;
        this.method = method;
    }
}
