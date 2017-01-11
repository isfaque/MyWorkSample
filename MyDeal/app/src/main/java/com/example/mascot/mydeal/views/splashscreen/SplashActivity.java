package com.example.mascot.mydeal.views.splashscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mascot.mydeal.R;
import com.example.mascot.mydeal.utils.ActivityUtil;

/**
 * Created by Mascot on 1/8/2017.
 */
public class SplashActivity extends AppCompatActivity implements SplashView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2600);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    startLoginActivity();
                }
            }
        };
        timerThread.start();
    }

    @Override
    public void startLoginActivity() {
        new ActivityUtil(this).startLoginActivity();
    }
}
