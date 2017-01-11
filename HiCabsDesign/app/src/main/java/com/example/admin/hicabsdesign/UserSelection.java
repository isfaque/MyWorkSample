package com.example.admin.hicabsdesign;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by admin on 11/05/2016.
 */
public class UserSelection extends AppCompatActivity {

    Button btn_user, btn_driver;
    AlertDialog alertbox;
    //final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    //int hasLocationPermission = checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION );

    int android_version = Integer.valueOf(android.os.Build.VERSION.SDK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_selection);

        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
            return; // add this to prevent from doing unnecessary stuffs
        }

        btn_user = (Button)findViewById(R.id.btn_user_selection);
        btn_driver = (Button)findViewById(R.id.btn_driver_selection);

        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSelection.this, UserLogin.class);
                startActivity(intent);
            }
        });

        btn_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSelection.this, DriverLogin.class);
                startActivity(intent);
            }
        });


        /*
        if(android_version>=23){
            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CALL_PHONE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }*/



    }




    /** this function call when you click on back button for exit application **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /** function shows the exit application dialogue box **/
    protected void exitByBackKey() {

        alertbox = new AlertDialog.Builder(this).setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
                //close();
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
               //finish();
    }

}
