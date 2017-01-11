package com.example.admin.qbreaker.UserFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.admin.qbreaker.MainActivity;
import com.example.admin.qbreaker.R;

/**
 * Created by admin on 09/06/2016.
 */
public class UserAbout extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_about, container, false);

        MainActivity.isUserAboutShown=true;
        MainActivity.isUserContactShown=false;
        MainActivity.isUserNoticeShown=false;
        MainActivity.isUserNoticeDetailShown=false;


        return v;
    }
}
