package com.xiaowei.eventbuss;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 订阅方法的封装
 */
public class MethodManager {
    //订阅方法的本身
    private Method method;
    //接受参数的类型
    private Class<?> type;
    //线程的类型
    private ThreadMode threadMode;

    public MethodManager(Method method, Class<?> type, ThreadMode threadMode) {
        this.method = method;
        this.type = type;
        this.threadMode = threadMode;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }
}
