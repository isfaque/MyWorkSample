package com.example.admin.hicabsdesign.UserFragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.SplashActivity;
import com.example.admin.hicabsdesign.UserLogin;
import com.example.admin.hicabsdesign.UserSelection;

/**
 * Created by admin on 12/05/2016.
 */
public class UserLogout extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashActivity.editor.clear();
        SplashActivity.editor.commit();
        Intent intent = new Intent(getActivity().getBaseContext(), UserSelection.class);
        Intent intent2 = new Intent(getActivity().getBaseContext(), UserLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();

    }
}
