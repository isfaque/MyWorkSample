package com.example.admin.jerne;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by admin on 17/06/2016.
 */
public class UserLoginSignup extends AppCompatActivity {

    Button btn_user_login_selection, btn_user_signup_selection;
    AlertDialog alertbox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup_selection_screen);

        btn_user_login_selection = (Button) findViewById(R.id.btn_user_login_selection);
        btn_user_signup_selection = (Button) findViewById(R.id.btn_user_signup_selection);

        btn_user_login_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginSignup.this, UserLogin.class);
                startActivity(intent);

            }
        });

        btn_user_signup_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginSignup.this, UserSignup.class);
                startActivity(intent);
            }
        });
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
