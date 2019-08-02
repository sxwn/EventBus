package com.xiaowei.eventbuss;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventBus {

    private static volatile EventBus eventBus;

    //装载订阅方法的容器
    private Map<Object, List<MethodManager>> methodMap;
    //切换线程的Handler
    private Handler handler;
    //切换到子线程的对象
    private ExecutorService executorService;

    private EventBus() {
        methodMap = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newCachedThreadPool();
    }

    public static EventBus getDefault() {
        if (eventBus == null) {
            synchronized (EventBus.class) {
                if (eventBus == null) {
                    eventBus = new EventBus();
                }
            }
        }
        return eventBus;
    }

    /**
     * 注册订阅者,把传送进来的组件进行收取所有的订阅方法
     *
     * @param object
     */
    public void register(Object object) {
        //收集所有的订阅方法
        List<MethodManager> methodManagers = methodMap.get(object);
        if (methodManagers == null || methodManagers.size() == 0) {
            methodManagers = findMethod(object);
            methodMap.put(object,methodManagers);
        }
    }

    /**
     * 去object找所有的订阅方法
     *
     * @param object
     */
    private List<MethodManager> findMethod(Object object) {
        List<MethodManager> methodManagers = new ArrayList<>();
        Class<?> aClass = object.getClass();
        //获取到这个类中所有的方法
//        aClass.getMethods() 祖宗方法也可以获取到
        Method[] declaredMethods = aClass.getDeclaredMethods();
        //遍历方法
        for (Method declaredMethod : declaredMethods) {
            Subscibe annotation = declaredMethod.getAnnotation(Subscibe.class);
            if (annotation == null)
                continue;
            //获取到该方法的所有的接受参数的类型
            Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
            if (parameterTypes != null && parameterTypes.length > 1) {
                //不符合我们的要求
                continue;
                //throw new RuntimeException("不符合我们的要求");
            }
            //线程类型
            ThreadMode threadMode = annotation.threadMode();
            MethodManager methodManager = new MethodManager(declaredMethod, parameterTypes[0], threadMode);
            methodManagers.add(methodManager);
        }
        return methodManagers;
    }

    /**
     * 发布消息，发布数据
     *
     * @param object
     */
    public void post(final Object object) {
        //拿到map的key的遍历对象
        Iterator<Object> iterator = methodMap.keySet().iterator();
        while (iterator.hasNext()) {
            //拿到组件的对象
            final Object next = iterator.next();
            List<MethodManager> methodManagers = methodMap.get(next);
            //遍历方法集合
            for (final MethodManager methodManager : methodManagers) {
                //如果类型匹配
                if (object.getClass().isAssignableFrom(methodManager.getType())) {
                    //线程切换就在这儿
                    switch (methodManager.getThreadMode()){
                        case POSTING:
                            invoke(next, methodManager.getMethod(), object);
                            break;
                        case MAIN:
                            //如果当前线程是主线程
                            if (Looper.myLooper() == Looper.getMainLooper()){
                                invoke(next, methodManager.getMethod(), object);
                            }else{
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(next, methodManager.getMethod(), object);
                                    }
                                });
                            }
                            break;
                        case BACKGROUND:
                            //如果当前线程是主线程
                            if (Looper.myLooper() == Looper.getMainLooper()){
                                //切换到子线程
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(next, methodManager.getMethod(), object);
                                    }
                                });
                            }else{
                                invoke(next, methodManager.getMethod(), object);
                            }
                            break;
                    }
                }
            }
        }
    }

    private void invoke(Object next, Method method, Object object) {
        try {
            //传next对象,参数是object
            method.invoke(next, object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除组件的订阅方法
     * @param object
     */
    public void unRegister(Object object){
        if (methodMap.get(object)!= null){
            methodMap.remove(object);
        }
    }
}
