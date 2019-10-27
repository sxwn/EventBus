package com.xiaowei.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xiaowei.eventbuss.EventBus;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
    }

    public void sendMessage(View view) {
//        FuctionManager.getInstance().invokeFuction("noparamnoresult");
        Nebean nebean = FuctionManager.getInstance().invokeFuction("noparamhasresult", Nebean.class);
//        Nebean nebean = FuctionManager.getInstance().invokeFuction("hasparamhasresult", Nebean.class,new Nebean("111111111111","222222222222222"));
        Log.e("weip","SecondActivity"+nebean.toString());
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Log.e("weip","SecondActivity threadname:"+Thread.currentThread().getName());
//                        EventBus.getDefault().post(new User("智囊三"));
                    }
                }
        ).start();
//        EventBus.getDefault().post(new User("智囊三"));
    }
}
