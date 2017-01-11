package com.example.admin.hicabsdesign.DriverFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.hicabsdesign.DriverMainActivity;
import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.SplashActivity;

/**
 * Created by admin on 23/05/2016.
 */
public class DriverSettingProfile extends Fragment {

    public static String driver_setting_profile_id, driver_setting_profile_name, driver_setting_country_code, driver_setting_mobile_number, driver_setting_device_id;
    TextView profile_name_head, profile_number_head, profile_id, profile_name, profile_country_code, profile_number, profile_device_id;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_setting_profile_driver, container, false);

        DriverMainActivity.userHomeShow = false;

        profile_name_head = (TextView) v.findViewById(R.id.driver_setting_profile_name_head);
        profile_number_head = (TextView) v.findViewById(R.id.driver_setting_mobile_no_head);
        profile_name = (TextView) v.findViewById(R.id.driver_setting_profile_name);
        profile_id = (TextView) v.findViewById(R.id.driver_setting_profile_id);
        profile_country_code = (TextView) v.findViewById(R.id.driver_setting_profile_country_code);
        profile_number = (TextView) v.findViewById(R.id.driver_setting_profile_mobile_no);
        profile_device_id = (TextView) v.findViewById(R.id.driver_setting_profile_device_id);

        driver_setting_profile_id = SplashActivity.sh.getString("login_id", null);
        driver_setting_profile_name = SplashActivity.sh.getString("login_name", null);
        Log.i("profile name is", driver_setting_profile_name);
        driver_setting_country_code = SplashActivity.sh.getString("login_country_code", null);
        driver_setting_mobile_number = SplashActivity.sh.getString("login_mobile_number", null);
        driver_setting_device_id = SplashActivity.sh.getString("login_device_id", null);

        profile_name_head.setText(driver_setting_profile_name);
        profile_number_head.setText("(+"+driver_setting_country_code+") "+driver_setting_mobile_number);
        profile_id.setText("Profile ID: "+driver_setting_profile_id);
        profile_name.setText("Name: "+driver_setting_profile_name);
        profile_country_code.setText("Country Code: "+driver_setting_country_code);
        profile_number.setText("Mobile Number: "+driver_setting_mobile_number);
        profile_device_id.setText("Device ID: "+driver_setting_device_id);


        return v;
    }
}
