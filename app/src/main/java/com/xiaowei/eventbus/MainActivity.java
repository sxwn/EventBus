package com.xiaowei.eventbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xiaowei.eventbuss.EventBus;
import com.xiaowei.eventbuss.Subscibe;
import com.xiaowei.eventbuss.ThreadMode;

import java.lang.annotation.Retention;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
    }

    public void sendMsg(View view){
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
    @Subscibe(threadMode = ThreadMode.MAIN)
    public void getMessage(User user){
        Log.e("weip","MainActivity threadname:"+Thread.currentThread().getName());
        Log.e("weip","MainActivity user:"+user.getName());
    }

    @Subscibe(threadMode = ThreadMode.BACKGROUND)
    public void getMessage22(User user){
        Log.e("weip","MainActivity threadname:"+Thread.currentThread().getName());
        Log.e("weip","MainActivity user:"+user.getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
