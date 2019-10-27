package com.xiaowei.eventbus;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class FuctionManager {
    //容器
    private Map<String, FuctionNoParamNoResult> mNoParamNoResultMap;
    private Map<String, FuctionNoParamHasResult> mNoParamHasResultMap;
    private Map<String, FuctionHasParamNoResult> mHasParamNoResultMap;
    private Map<String, FuctionHasParamHasResult> mHasParamHasResultMap;

    private static FuctionManager instance;

    private FuctionManager() {
//        context.getApplicationContext();  网络通信   or   Context Activity
        mNoParamNoResultMap = new HashMap<>();
        mNoParamHasResultMap = new HashMap<>();
        mHasParamNoResultMap = new HashMap<>();
        mHasParamHasResultMap = new HashMap<>();
    }

    public static FuctionManager getInstance() {
        if (instance == null) {
            instance = new FuctionManager();
        }
        return instance;
    }

    //添加方法
    public void addFuction(FuctionNoParamNoResult fuction) {
        mNoParamNoResultMap.put(fuction.fuctionName, fuction);
    }

    public void addFuction(FuctionNoParamHasResult fuction) {
        mNoParamHasResultMap.put(fuction.fuctionName, fuction);
    }

    public void addFuction(FuctionHasParamNoResult fuction) {
        mHasParamNoResultMap.put(fuction.fuctionName, fuction);
    }

    public void addFuction(FuctionHasParamHasResult fuction) {
        mHasParamHasResultMap.put(fuction.fuctionName, fuction);
    }

    //执行具体的方法  NoParamNoResult  优点缺点  原理
    public void invokeFuction(String fuctionName) {
        if (TextUtils.isEmpty(fuctionName)) {
            return;
        }
        if (mNoParamNoResultMap != null) {
            FuctionNoParamNoResult f = mNoParamNoResultMap.get(fuctionName);
            if (f != null) {
                f.fuction();
            } else {
                Log.e("weip", "can't find the method");
            }
        }
    }
    public <T> T invokeFuction(String fuctionName,Class<T> t){
        if (TextUtils.isEmpty(fuctionName)) {
            return null;
        }
        if (mNoParamHasResultMap != null) {
            FuctionNoParamHasResult f = mNoParamHasResultMap.get(fuctionName);
            if (f != null) {
                if (t != null){
                    return t.cast(f.fuction());
                }
            } else {
                Log.e("weip", "can't find the method"+fuctionName);
            }
        }
        return null;
    }

    /**
     * 有参数无返回值
     * @param fuctionName
     * @param p
     * @param <P>
     */
    public <P> void invokeFuction(String fuctionName, P p) {
        if (TextUtils.isEmpty(fuctionName)) {
            return;
        }
        if (mHasParamNoResultMap != null) {
            FuctionHasParamNoResult f = mHasParamNoResultMap.get(fuctionName);
            if (f != null) {
                f.fuction(p);
            } else {
                Log.e("weip", "can't find the method"+fuctionName);
            }
        }
    }

    public <T,P> T invokeFuction(String fuctionName,Class<T> t, P p){
        if (TextUtils.isEmpty(fuctionName)) {
            return null;
        }
        if (mHasParamHasResultMap != null){
            FuctionHasParamHasResult f = mHasParamHasResultMap.get(fuctionName);
            if (f != null){
                if (t != null){
                    return t.cast(f.fuction(p));
                }
            }else{
                Log.e("weip", "can't find the method"+fuctionName);
            }
        }
        return null;
    }
}
