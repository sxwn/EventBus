package com.xiaowei.eventbus;

import android.content.Context;

public class FuctionManager {

    private static  FuctionManager instance;

    private FuctionManager(){
//        context.getApplicationContext();  网络通信   or   Context Activity
    }
    public static FuctionManager getInstance(){
        if (instance == null){
            instance = new FuctionManager();
        }
        return instance;
    }
}
