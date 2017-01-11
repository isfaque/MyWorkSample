package com.example.admin.hicabsdesign.UserFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.hicabsdesign.R;

/**
 * Created by admin on 25/06/2016.
 */
public class UserHistoryStatus extends Fragment {

    TextView txt_user_booking_status, txt_user_booking_id, txt_user_booking_date, txt_user_booking_time, txt_user_pick_city, txt_user_pick_address, txt_user_drop_city, txt_user_drop_address, txt_user_booking_amount, txt_user_booking_stop;
    public static String user_booking_status, user_booking_id, user_dbooking_id, user_booking_date, user_booking_time, user_pick_city, user_pick_address, user_drop_city, user_drop_address, user_booking_amount, user_booking_stop;
    public static String update_hours, update_id, update_message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_status_user, container, false);

        txt_user_booking_status = (TextView) v.findViewById(R.id.txt_user_booking_status);
        txt_user_booking_id = (TextView) v.findViewById(R.id.txt_user_booking_id);
        txt_user_booking_date = (TextView) v.findViewById(R.id.txt_user_booking_date);
        txt_user_booking_time = (TextView) v.findViewById(R.id.txt_user_booking_time);
        txt_user_pick_city = (TextView) v.findViewById(R.id.txt_user_pick_city);
        txt_user_pick_address = (TextView) v.findViewById(R.id.txt_user_pick_address);
        txt_user_drop_city = (TextView) v.findViewById(R.id.txt_user_drop_city);
        txt_user_drop_address = (TextView) v.findViewById(R.id.txt_user_drop_address);
        txt_user_booking_amount = (TextView) v.findViewById(R.id.txt_user_booking_amount);
        txt_user_booking_stop = (TextView) v.findViewById(R.id.txt_user_booking_stop);


        user_booking_status = UserHistoryList.user_booking_status;
        user_booking_id = UserHistoryList.user_booking_id;
        user_dbooking_id = UserHistoryList.user_dbooking_id;
        user_booking_date = UserHistoryList.user_booking_date;
        user_booking_time = UserHistoryList.user_booking_time;
        user_pick_city = UserHistoryList.user_picktown_city;
        user_pick_address = UserHistoryList.user_picktown_address;
        user_drop_city = UserHistoryList.user_droptown_city;
        user_drop_address = UserHistoryList.user_droptown_address;
        user_booking_amount = UserHistoryList.user_booking_amount;
        user_booking_stop = UserHistoryList.user_booking_stop;

        txt_user_booking_status.setText("Status: "+user_booking_status);
        txt_user_booking_id.setText("Booking ID: "+user_dbooking_id);
        txt_user_booking_date.setText("Booking Date: "+user_booking_date);
        txt_user_booking_time.setText("Booking Time: "+user_booking_time);
        txt_user_pick_city.setText("Pickup Town: "+user_pick_city);
        txt_user_pick_address.setText("Pickup Address: "+user_pick_address);
        txt_user_drop_city.setText("Drop Town: "+user_drop_city);
        txt_user_drop_address.setText("Drop Address: "+user_drop_address);
        txt_user_booking_amount.setText("Booking Amount: €"+user_booking_amount);
        txt_user_booking_stop.setText("No. of Stop: "+user_booking_stop);

        return v;


    }
}
