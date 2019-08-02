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
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Log.e("weip","SecondActivity threadname:"+Thread.currentThread().getName());
                        EventBus.getDefault().post(new User("智囊三"));
                    }
                }
        ).start();
//        EventBus.getDefault().post(new User("智囊三"));
    }
}
