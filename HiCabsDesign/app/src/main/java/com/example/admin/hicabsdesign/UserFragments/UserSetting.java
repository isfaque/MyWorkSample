package com.example.admin.hicabsdesign.UserFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.hicabsdesign.R;

/**
 * Created by admin on 12/05/2016.
 */
public class UserSetting extends Fragment {

    Button btn_user_profile, btn_user_change_password;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_setting_user, container, false);

        btn_user_profile = (Button) v.findViewById(R.id.btn_user_profile);
        btn_user_change_password = (Button) v.findViewById(R.id.btn_user_change_password);

        btn_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr;
                fr = new UserSettingProfile();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_user_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr;
                fr = new UserSettingChangePasswordOTP();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return v;
    }
}
