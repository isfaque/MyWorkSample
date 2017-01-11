package com.example.admin.hicabsdesign.DriverFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.hicabsdesign.DriverMainActivity;
import com.example.admin.hicabsdesign.MapsActivity;
import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.SplashActivity;
import com.example.admin.hicabsdesign.UserFragments.UserBookingStatus;
import com.example.admin.hicabsdesign.UserMainActivity;
import com.example.admin.hicabsdesign.UserSelection;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 12/05/2016.
 */
public class DriverHome extends Fragment {


    TextView txt_driver_check_task;
    ProgressBar progressBar;
    AlertDialog alertDialog;
    AlertDialog alertbox;

    private ProgressDialog progressDialog;

    ScheduledExecutorService scheduledExecutorService;

    Button btn_driver_job_refresh;

    JSONObject jArray;

    public static String driver_id, booking_data;
    public static String driver_confirm_booking_id, driver_picktown_code, driver_droptown_code, driver_picktown_address, driver_droptown_address, driver_booking_date, driver_booking_time, driver_booking_amount, driver_booking_status, driver_payment_update;
    public static String driver_pickup_latitude, driver_pickup_longitude, driver_drop_latitude, driver_drop_longitude, driver_dbooking_id;
    public static String driver_accepted, driver_picked, driver_stop1, driver_stop2, driver_stop3, driver_completed;
    public static String driver_booking_nameonboard, driver_booking_flightdetails, driver_booking_comment;

    String Url ="http://hicabs-system.com/Api/getdriversingletask";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_driver, container, false);
        DriverMainActivity.userHomeShow = true;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("hicabs assigned you a job");

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setConfirmdata();

            }
        });
        alertbox = alertDialogBuilder.create();


        /*
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressBar.setIndeterminate(true);
                setConfirmdata();
                handler.postDelayed(this, 30000); //now is every 2 minutes
            }
        }, 30000);
        */

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        txt_driver_check_task = (TextView) v.findViewById(R.id.txt_driver_check_booking);
        btn_driver_job_refresh = (Button) v.findViewById(R.id.btn_driver_job_refresh);

        driver_id = SplashActivity.sh.getString("login_id", null);

        progressBar.setIndeterminate(true);
        if(driver_id == null)
        {
            progressBar.setIndeterminate(false);
            txt_driver_check_task.setText("New Job not available");
            Toast.makeText(getActivity(), "New Job not available", Toast.LENGTH_SHORT).show();

        }else {

            setConfirmdata();
        }

        btn_driver_job_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setIndeterminate(true);
                setConfirmdata();
            }
        });

        if(DriverMainActivity.userHomeShow){

            final Handler handler = new Handler();
            Timer timer = new Timer();
            TimerTask doAsynchronousTask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            try {
                                //Toast.makeText(getActivity(),"Hello WOrld", Toast.LENGTH_SHORT).show();
                                checkConfirmdata();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                            }
                        }
                    });
                }
            };
            timer.schedule(doAsynchronousTask, 0, 60000); //execute in every 60000 ms

        }



        return v;
    }


    /** get driver task information from server **/
    @SuppressLint("LongLogTag")
    public void setConfirmdata()
    {
        System.out.println("inside to get Data for Confirm Page");

        RequestParams params = new RequestParams();


        params.put("driver", driver_id);
        System.out.print(driver_id);
        Log.e("Driver Id is", driver_id);

        params.put("status", "no");
        Log.e("Driver Id is", "no");

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

                    driver_confirm_booking_id = jArray.getString("bookingid");
                    Log.i("Confirm Add Booking Id is", driver_confirm_booking_id);

                    driver_dbooking_id = jArray.getString("dbookingid");
                    Log.i("Confirm Add Booking Id is", driver_dbooking_id);

                    driver_picktown_code = jArray.getString("pickupcity");
                    Log.i("Picktown code is", driver_picktown_code);

                    driver_droptown_code = jArray.getString("dropcity");
                    Log.i("Droptown code is", driver_droptown_code);

                    driver_picktown_address = jArray.getString("pickupStreet");
                    Log.i("Picktown address is", driver_picktown_address);

                    driver_droptown_address = jArray.getString("dropStreet");
                    Log.i("Booking address is", driver_droptown_address);

                    driver_pickup_latitude = jArray.getString("pickuplatitude");
                    Log.i("Picktown latitude", driver_pickup_latitude);

                    driver_pickup_longitude = jArray.getString("pickuplongitude");
                    Log.i("Picktown longitude", driver_pickup_longitude);

                    driver_drop_latitude = jArray.getString("droplatitude");
                    Log.i("Droptown latitude is", driver_drop_latitude);

                    driver_drop_longitude = jArray.getString("droplongitude");
                    Log.i("Droptown longitude is", driver_drop_longitude);

                    driver_booking_date = jArray.getString("jobdate");
                    Log.i("Booking date is", driver_booking_date);

                    driver_booking_time = jArray.getString("jobtime");
                    Log.i("Booking time is", driver_booking_time);

                    driver_booking_amount = jArray.getString("bookingprice");
                    Log.i("Booking amount is", driver_booking_amount);

                    driver_booking_nameonboard = jArray.getString("nameonboard");
                    Log.i("Booking nameonboard is", driver_booking_nameonboard);

                    driver_booking_flightdetails = jArray.getString("flightdetails");
                    Log.i("Booking flightdetails is", driver_booking_flightdetails);

                    driver_booking_comment = jArray.getString("comment");
                    Log.i("Booking comment is", driver_booking_comment);

                    driver_booking_status = jArray.getString("status");
                    Log.i("Booking status is", driver_booking_status);

                    driver_accepted = jArray.getString("driveraccepted");
                    Log.i("Driver accepted is", driver_accepted);

                    driver_picked = jArray.getString("driverpicked");
                    Log.i("Driver picked is", driver_picked);

                    driver_stop1 = jArray.getString("driverstop");
                    Log.i("Booking stop1 is", driver_stop1);

                    driver_completed = jArray.getString("drivercompleted");
                    Log.i("Booking status is", driver_completed);

                    driver_payment_update = jArray.getString("driverpayment");
                    Log.i("Payment status is", driver_payment_update);


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

                            if(driver_accepted.equals("yes") && driver_picked.equals("no")){

                                Fragment fr;
                                fr = new DriverHomeTaskTwo();
                                FragmentManager fm = getFragmentManager();
                                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.main_content, fr);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                            }

                            else if(driver_accepted.equals("yes") && driver_picked.equals("yes") && driver_completed.equals("no")){
                                Fragment fr;
                                fr = new DriverHomeTaskThree();
                                FragmentManager fm = getFragmentManager();
                                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.main_content, fr);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                            }

                            else if(driver_accepted.equals("yes") && driver_picked.equals("yes") && driver_completed.equals("yes")){
                                Fragment fr;
                                fr = new DriverHomeTaskSeven();
                                FragmentManager fm = getFragmentManager();
                                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.main_content, fr);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                            }

                            else{

                                Fragment fr;
                                fr = new DriverHomeTaskOne();
                                FragmentManager fm = getFragmentManager();
                                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.main_content, fr);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }
                    } else {
                        progressBar.setIndeterminate(false);
                        txt_driver_check_task.setText("New Job not available");
                        Toast.makeText(getActivity(), "New Job not available", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    progressBar.setIndeterminate(false);
                    // TODO: handle exception
                    //Toast tost = new Toast(getActivity());
                    Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();
                    //tost.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                    //Intent i = new Intent(getActivity(), SplashActivity.class);
                    //startActivity(i);
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


    /** get driver task information from server **/
    @SuppressLint("LongLogTag")
    public void checkConfirmdata()
    {
        System.out.println("inside to get Data for Confirm Page");

        RequestParams params = new RequestParams();


        params.put("driver", driver_id);
        System.out.print(driver_id);
        Log.e("Driver Id is", driver_id);

        params.put("status", "no");
        Log.e("Driver Id is", "no");

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

                    driver_confirm_booking_id = jArray.getString("bookingid");
                    Log.i("Confirm Add Booking Id is", driver_confirm_booking_id);

                    driver_dbooking_id = jArray.getString("dbookingid");
                    Log.i("Confirm Add Booking Id is", driver_dbooking_id);

                    driver_picktown_code = jArray.getString("pickupcity");
                    Log.i("Picktown code is", driver_picktown_code);

                    driver_droptown_code = jArray.getString("dropcity");
                    Log.i("Droptown code is", driver_droptown_code);

                    driver_picktown_address = jArray.getString("pickupStreet");
                    Log.i("Picktown address is", driver_picktown_address);

                    driver_droptown_address = jArray.getString("dropStreet");
                    Log.i("Booking address is", driver_droptown_address);

                    driver_pickup_latitude = jArray.getString("pickuplatitude");
                    Log.i("Picktown latitude", driver_pickup_latitude);

                    driver_pickup_longitude = jArray.getString("pickuplongitude");
                    Log.i("Picktown longitude", driver_pickup_longitude);

                    driver_drop_latitude = jArray.getString("droplatitude");
                    Log.i("Droptown latitude is", driver_drop_latitude);

                    driver_drop_longitude = jArray.getString("droplongitude");
                    Log.i("Droptown longitude is", driver_drop_longitude);

                    driver_booking_date = jArray.getString("jobdate");
                    Log.i("Booking date is", driver_booking_date);

                    driver_booking_time = jArray.getString("jobtime");
                    Log.i("Booking time is", driver_booking_time);

                    driver_booking_amount = jArray.getString("bookingprice");
                    Log.i("Booking amount is", driver_booking_amount);

                    driver_booking_nameonboard = jArray.getString("nameonboard");
                    Log.i("Booking nameonboard is", driver_booking_nameonboard);

                    driver_booking_flightdetails = jArray.getString("flightdetails");
                    Log.i("Booking flightdetails is", driver_booking_flightdetails);

                    driver_booking_comment = jArray.getString("comment");
                    Log.i("Booking comment is", driver_booking_comment);

                    driver_booking_status = jArray.getString("status");
                    Log.i("Booking status is", driver_booking_status);

                    driver_accepted = jArray.getString("driveraccepted");
                    Log.i("Driver accepted is", driver_accepted);

                    driver_picked = jArray.getString("driverpicked");
                    Log.i("Driver picked is", driver_picked);

                    driver_stop1 = jArray.getString("driverstop");
                    Log.i("Booking stop1 is", driver_stop1);

                    driver_completed = jArray.getString("drivercompleted");
                    Log.i("Booking status is", driver_completed);

                    driver_payment_update = jArray.getString("driverpayment");
                    Log.i("Payment status is", driver_payment_update);


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

                            if(DriverMainActivity.userHomeShow){

                                alertbox.dismiss();
                                alertbox.show();

                                /*
                                alertbox = new AlertDialog.Builder(getActivity()).setMessage("hicabs assigned you a new job")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                                // do something when the button is clicked
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    setConfirmdata();
                                                    //close();
                                                }
                                            })


                                            .show();


                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                alertDialogBuilder.setMessage("hicabs assigned you a new job");

                                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                    }
                                });
                                alertbox = alertDialogBuilder.create();
                                alertbox.show();*/







                            }





                        } catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }
                    } else {
                        progressBar.setIndeterminate(false);
                        //txt_driver_check_task.setText("New Job not available");
                        //Toast.makeText(getActivity(), "New Job not available", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    progressBar.setIndeterminate(false);
                    // TODO: handle exception
                    //Toast tost = new Toast(getActivity());
                    Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();
                    //tost.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                    //Intent i = new Intent(getActivity(), SplashActivity.class);
                    //startActivity(i);
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




    /*
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.driver_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng saltlake = new LatLng(22.5727182, 88.4209779);
        LatLng rabindrasadan = new LatLng(22.5421339, 88.34418649999999);
        mMap.addMarker(new MarkerOptions().position(saltlake).title("Pickup Location: Salt Lake")).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(rabindrasadan).title("Drop Location: Rabindra Sadan"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(saltlake));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        mMap.addPolyline(new PolylineOptions()
                .add(saltlake, rabindrasadan)
                .width(5)
                .color(Color.BLUE));

    }*/


    /*
    public void ActionStartsHere()
    {
        againStartGPSAndSendFile();
    }*/
/*
    public void againStartGPSAndSendFile()
    {
        new CountDownTimer(61000, 60000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {

                // Display Data by Every Ten Second

                progressBar.setIndeterminate(true);
                setConfirmdata();
            }
            @Override
            public void onFinish()
            {
                ActionStartsHere();
            }

        }.start();
    }*/

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of LoginFragment");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of HomeFragment");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("DEBUG", "OnStop of HomeFragment");
        super.onPause();
    }
}
