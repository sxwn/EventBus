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
//        EventBus.getDefault().register(this);
//        FuctionManager.getInstance().addFuction(new FuctionNoParamNoResult("noparamnoresult") {
//            @Override
//            public void fuction() {
//                Log.e("Mainactivity ==>","Hello from the SecondActivity ");
//            }
//        });

        FuctionManager.getInstance().addFuction(new FuctionNoParamHasResult<Nebean>("noparamhasresult") {
            @Override
            public Nebean fuction() {
                Log.e("Mainactivity ==>","Hello from the SecondActivity ");
                Nebean bean = new Nebean("一一老师","网易最好老师,没有之一");
                return bean;
            }
        });

//        FuctionManager.getInstance().addFuction(new FuctionHasParamHasResult<Nebean,Nebean>("hasparamhasresult") {
//            @Override
//            public Nebean fuction(Nebean o) {
//                Log.e("MainActivity ====>",o.toString());
//                Nebean nebean = new Nebean("e12311","342414");
//                return nebean;
//            }
//        });

        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }

//    public void sendMsg(View view){
//        Intent intent = new Intent(this,SecondActivity.class);
//        startActivity(intent);
//    }
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
