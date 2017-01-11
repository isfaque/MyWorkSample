package com.example.mascot.mydeal.utils;

import android.content.Context;
import android.content.Intent;

import com.example.mascot.mydeal.views.login.LoginActivity;
import com.example.mascot.mydeal.views.main.MainActivity;
import com.example.mascot.mydeal.views.signup.SignupActivity;

/**
 * Created by Mascot on 1/8/2017.
 */
public class ActivityUtil {
    private Context context;

    public ActivityUtil(Context context) {
        this.context = context;
    }

    public void startMainActivity() {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public void startLoginActivity() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public void startSignupActivity() {
        context.startActivity(new Intent(context, SignupActivity.class));
    }
}
