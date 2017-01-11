package com.example.admin.hicabsdesign.UserFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.SplashActivity;
import com.example.admin.hicabsdesign.UserLogin;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 12/05/2016.
 */
public class UserBooking extends Fragment {

    public static String user_login_id, booking_data;
    public static String user_confirm_booking_id, user_picktown_code, user_droptown_code, user_picktown_address, user_droptown_address, user_booking_date, user_booking_time, user_booking_amount, user_booking_status;
    public static String user_pickup_latitude, user_pickup_longitude, user_drop_latitude, user_drop_longitude;
    String Url ="http://hicabs-system.com/Api/getsinglebookingavailable";

    TextView txt_user_check_booking;
    ProgressBar progressBar;
    AlertDialog alertDialog;

    JSONObject jArray;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_booking_user, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        txt_user_check_booking = (TextView) v.findViewById(R.id.txt_user_check_booking);

        user_login_id = SplashActivity.sh.getString("login_id", null);

        progressBar.setIndeterminate(true);
        if(user_login_id == null)
        {
            progressBar.setIndeterminate(false);
            txt_user_check_booking.setText("Please Login First");
            Toast.makeText(getActivity(), "Please Login First", Toast.LENGTH_LONG).show();

        }else {

            setConfirmdata();
        }

        return v;
    }

    /** get current booking information from server **/
    @SuppressLint("LongLogTag")
    public void setConfirmdata()
    {
        System.out.println("inside to get Data for Confirm Page");

        RequestParams params = new RequestParams();


        params.put("client", user_login_id);
        System.out.print(user_login_id);
        Log.e("Booking Id is", user_login_id);

        params.put("status", "no");
        Log.e("Booking Status is", "no");

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(Url, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {
                Log.d("response for Confirm Page connect", response);
                String str_response = response;
                Log.e("Confirm Page URL", Url);
                try {

                    jArray = new JSONObject(response);

                    booking_data = jArray.getString("data");
                    Log.i("Booking Data is", booking_data);


                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (booking_data.equals("yes")) {
                        progressBar.setIndeterminate(false);

                        try {

                            booking_data = jArray.getString("data");

                            Log.i("Confirm Rate in try block is", booking_data);

                            Fragment fr;
                            fr = new UserBookingList();
                            FragmentManager fm = getFragmentManager();
                            android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.main_content, fr);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }
                    } else {
                        progressBar.setIndeterminate(false);
                        txt_user_check_booking.setText("Active Booking data not available");
                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    progressBar.setIndeterminate(false);
                    // TODO: handle exception
                    Toast tost = new Toast(getActivity());
                    tost.makeText(getActivity(), "Please check Confirmation page data fields ", Toast.LENGTH_SHORT).show();
                    tost.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                    Intent i = new Intent(getActivity(), SplashActivity.class);
                    startActivity(i);
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Throwable e) {
                progressBar.setIndeterminate(false);
                alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("Please check Internet connection or Server is not responding");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        });

    }
}
