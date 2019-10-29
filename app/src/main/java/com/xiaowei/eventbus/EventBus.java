package com.xiaowei.eventbus;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class EventBus {

    //带有SubscribeMathod注解的方法
    private Map<Object, List<SubscribeMethod>> cacheMap;

    private static volatile  EventBus eventBus;

    private Handler mHandler;

    private EventBus(){
        cacheMap = new HashMap<>();
        mHandler = new Handler();
    }

    public static EventBus getDefault(){
        if (eventBus == null){
            synchronized (EventBus.class){
                if (eventBus == null){
                    eventBus= new EventBus();
                }
            }
        }
        return eventBus;
    }

    /**
     * 管理各个Activity中的方法
     * @param obj
     */
    public void  register(Object obj){
        //寻找obj (本例子中对应的就是MainActivity中的所有带有subscribe注解的方法.放到 map中进行管理)
        List<SubscribeMethod> list = cacheMap.get(obj);
        if (list == null){
            list = findSubscribe(obj);
            cacheMap.put(obj,list);
        }
    }

    private List<SubscribeMethod> findSubscribe(Object obj) {
        List<SubscribeMethod> list = new ArrayList<>();
        Class<?> clazz = obj.getClass();
//        clazz.getMethods(); 所有的方法 父类中的方法也得找
        //只获取当前类中的方法,不找父类中的方法
        while (clazz != null) {
            //凡是系统级别的父类直接忽略掉
            String name = clazz.getName();
            if (name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.")){
                break;
            }
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method:methods) {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                //override永远等于空,在注解里面定义Runtime,源码中定义的是source,表示仅仅使用在编译时
                //Override override = method.getAnnotation(Override.class);
                if (subscribe == null){
                    //当前这个方法没有必要加入到eventbus中
                    continue;
                }
                //判断这个方法中的参数是否唯一
                Class<?>[] types = method.getParameterTypes();
                if (types.length != 1){
                    Log.e("weip","eventbus only accept one param");
                }
                ThreadMode threadMode = subscribe.threadMode();
                SubscribeMethod subscribeMethod = new SubscribeMethod(method,threadMode,types[0]);
                list.add(subscribeMethod);
            }
            clazz = clazz.getSuperclass();
        }
        return list;
    }

    public void post(final Object type){
        //直接循环cacheMap中的方法,找到对应的方法进行调用
        Set<Object> set = cacheMap.keySet();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()){
            final Object obj = iterator.next();
            List<SubscribeMethod> list = cacheMap.get(type);
            for(final SubscribeMethod subscribeMethod:list){
                //开始匹配参数
                if (subscribeMethod.getType().isAssignableFrom(type.getClass())){
                    switch (subscribeMethod.getThreadMode()){
                        case MAIN:
                            if (Looper.myLooper() == Looper.getMainLooper()){
                                //主  主
                                invoke(subscribeMethod,obj,type);
                            }else{
                                //子  主
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribeMethod,obj,type);
                                    }
                                });
                            }
                            break;
                        case BACKGROUND:
                            //主 子
                            //   ExecutorService
                            //子 子
                            invoke(subscribeMethod,obj,type);
                            break;
                    }
                }
            }
        }
    }

    private void invoke(SubscribeMethod subscribeMethod,Object obj,Object type) {
        Method method = subscribeMethod.getMethod();
        try {
            method.invoke(obj,type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void remove(){

    }
}
