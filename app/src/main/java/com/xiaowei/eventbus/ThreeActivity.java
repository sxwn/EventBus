package com.xiaowei.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

public class ThreeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        EventBus.getDefault().post(new Nebean("fdafafafaf","32141414444444444444"));
        new Thread(){
            @Override
            public void run() {
                super.run();
                EventBus.getDefault().post(new Nebean("fdafafafaf","32141414444444444444"));
            }
        }.start();
    }
}
