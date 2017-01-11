package com.example.admin.jerne.UserFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.jerne.MainActivity;
import com.example.admin.jerne.SplashActivity;
import com.example.admin.jerne.UserLogin;
import com.example.admin.jerne.UserLoginSignup;

/**
 * Created by admin on 17/06/2016.
 */
public class UserLogout extends Fragment {

    AlertDialog alertbox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alertbox = new AlertDialog.Builder(getActivity()).setMessage("Do you really want to Logout from app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(getActivity().getBaseContext(), UserLoginSignup.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .show();
    }
}
