package com.example.admin.qbreaker.UserFragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;

import com.example.admin.qbreaker.LoginActivity;
import com.example.admin.qbreaker.MainActivity;
import com.example.admin.qbreaker.R;
import com.example.admin.qbreaker.SplashActivity;

/**
 * Created by Mascot on 6/12/2016.
 */
public class UserLogout extends android.support.v4.app.Fragment {

    AlertDialog alertbox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        alertbox = new AlertDialog.Builder(getActivity()).setMessage("Do you really want to Logout from app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        android.webkit.CookieManager cookieManager = CookieManager.getInstance();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                                // a callback which is executed when the cookies have been removed
                                @Override
                                public void onReceiveValue(Boolean aBoolean) {
                                }
                            });
                        }
                        else cookieManager.removeAllCookie();

                        SplashActivity.editor.clear();
                        SplashActivity.editor.commit();
                        Intent intent = new Intent(getActivity().getBaseContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .show();

    }
}
