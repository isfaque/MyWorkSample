package com.example.admin.hicabsdesign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by admin on 11/05/2016.
 */
public class SplashActivity extends AppCompatActivity {

    public static String deviceId;

    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;
    public static String str_login_test, str_login_usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
/*
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2600);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashActivity.this, UserSelection.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
*/


        // here initializing the shared preference
        sh = getSharedPreferences("myprefe", 0);
        editor = sh.edit();

        // check here if user is login or not
        str_login_test = sh.getString("loginTest", null);
        str_login_usertype = sh.getString("loginType", null);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                /*
                 * if user login test is true on oncreate then redirect the user
                 * to result page
                 */

                if (str_login_test != null
                        && !str_login_test.toString().trim().equals("")) {
                    if(str_login_usertype.toString().trim().equals("user")) {
                        Intent send = new Intent(getApplicationContext(),
                                UserMainActivity.class);
                        send.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        send.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(send);
                    }
                    if(str_login_usertype.toString().trim().equals("driver")){
                        Intent send = new Intent(getApplicationContext(),
                                DriverMainActivity.class);
                        send.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        send.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(send);
                    }
                }
                /*
                 * if user login test is false on oncreate then redirect the
                 * user to login & registration page
                 */
                else {

                    Intent send = new Intent(getApplicationContext(),
                            UserSelection.class);
                    startActivity(send);

                }
            }

        }, 3000);



    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
