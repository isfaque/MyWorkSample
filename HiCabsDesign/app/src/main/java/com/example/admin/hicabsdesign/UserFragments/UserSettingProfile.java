package com.example.admin.hicabsdesign.UserFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.SplashActivity;
import com.example.admin.hicabsdesign.UserLogin;

import java.lang.ref.SoftReference;

/**
 * Created by admin on 16/05/2016.
 */
public class UserSettingProfile extends Fragment {

    Button btn_user_setting_profile_edit;
    public static String user_setting_profile_id, user_setting_profile_name, user_setting_profile_email, user_setting_country_code, user_setting_mobile_number, user_setting_device_id;
    TextView profile_name_head, profile_number_head, profile_id, profile_name, profile_email, profile_country_code, profile_number, profile_device_id;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_setting_profile_user, container, false);

        profile_name_head = (TextView) v.findViewById(R.id.user_setting_profile_name_head);
        profile_number_head = (TextView) v.findViewById(R.id.user_setting_mobile_no_head);
        profile_name = (TextView) v.findViewById(R.id.user_setting_profile_name);
        profile_email = (TextView) v.findViewById(R.id.user_setting_profile_email);
        profile_id = (TextView) v.findViewById(R.id.user_setting_profile_id);
        profile_country_code = (TextView) v.findViewById(R.id.user_setting_profile_country_code);
        profile_number = (TextView) v.findViewById(R.id.user_setting_profile_mobile_no);
        profile_device_id = (TextView) v.findViewById(R.id.user_setting_profile_device_id);

        btn_user_setting_profile_edit = (Button) v.findViewById(R.id.btn_user_setting_profile_edit);

        user_setting_profile_id = SplashActivity.sh.getString("login_id", null);
        user_setting_profile_name = SplashActivity.sh.getString("login_name", null);
        user_setting_profile_email = SplashActivity.sh.getString("login_email", null);
        user_setting_country_code = SplashActivity.sh.getString("login_country_code", null);
        user_setting_mobile_number = SplashActivity.sh.getString("login_mobile_number", null);
        user_setting_device_id = SplashActivity.sh.getString("login_device_id", null);

        profile_name_head.setText(user_setting_profile_name);
        profile_number_head.setText("(+"+user_setting_country_code+") "+user_setting_mobile_number);
        profile_id.setText("Profile ID: "+user_setting_profile_id);
        profile_name.setText("Name: "+user_setting_profile_name);
        profile_country_code.setText("Country Code: "+user_setting_country_code);
        profile_number.setText("Mobile Number: "+user_setting_mobile_number);
        profile_email.setText("Email: "+user_setting_profile_email);
        profile_device_id.setText("Device ID: "+user_setting_device_id);

        btn_user_setting_profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr;
                fr = new UserSettingProfileUpdate();
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
