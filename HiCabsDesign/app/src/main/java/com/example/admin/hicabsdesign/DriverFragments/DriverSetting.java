package com.example.admin.hicabsdesign.DriverFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.hicabsdesign.DriverMainActivity;
import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.UserFragments.UserSettingProfile;

/**
 * Created by admin on 12/05/2016.
 */
public class DriverSetting extends Fragment {

    Button btn_driver_profile, btn_driver_change_password;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_setting_driver, container, false);

        DriverMainActivity.userHomeShow = false;

        btn_driver_profile = (Button) v.findViewById(R.id.btn_driver_profile);
        btn_driver_change_password = (Button) v.findViewById(R.id.btn_driver_change_password);

        btn_driver_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr;
                fr = new DriverSettingProfile();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_driver_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment fr;
                fr = new DriverSettingChangePasswordOTP();
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
