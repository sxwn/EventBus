package com.xiaowei.eventbuss;

public enum ThreadMode {
    /**
     *  POSTING :不管发布者是在主线程还是子线程，随意
     *  MAIN:不管发布者是在主线程还是子线程，如果开发者选择的是MAIN，就代表订阅方法一定要在主线程中执行
     *  BACKGROUND:不管发布者是在主线程还是子线程，如果开发者选择的是BACKGROUND，就代表订阅方法一定要在子线程中执行
     */
    POSTING,
    MAIN,
    BACKGROUND
}
