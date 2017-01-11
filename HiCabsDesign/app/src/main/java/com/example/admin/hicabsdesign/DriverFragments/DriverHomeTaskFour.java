package com.example.admin.hicabsdesign.DriverFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.hicabsdesign.DriverMainActivity;
import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.SplashActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 24/05/2016.
 */
public class DriverHomeTaskFour extends Fragment{

    private GoogleMap mMap;

    String Url ="http://hicabs-system.com/Api/getdriversingletask";
    String UrlStop ="http://hicabs-system.com/Api/drivertaskfour";
    String UrlComplete ="http://hicabs-system.com/Api/drivertasksix";

    AlertDialog alertDialog;

    JSONObject jArray;
    Button btn_driver_task4_stop2, btn_driver_task4_completed;
    TextView txt_driver_task4_booking_no, txt_driver_task4_pickup_town, txt_driver_task4_pickup_street, txt_driver_task4_drop_town, txt_driver_task4_drop_street, txt_driver_task4_amount, txt_driver_task4_status;

    public static String driver_id, booking_data, status, update_message;
    public static String driver_confirm_booking_id, driver_picktown_code, driver_droptown_code, driver_picktown_address, driver_droptown_address, driver_booking_date, driver_booking_time, driver_booking_amount, driver_booking_status;
    public static String driver_pickup_latitude, driver_pickup_longitude, driver_drop_latitude, driver_drop_longitude;
    public static String driver_accepted, driver_picked, driver_stop1, driver_stop2, driver_stop3, driver_completed;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_task_four_driver, container, false);

        DriverMainActivity.userHomeShow = false;

        driver_id = SplashActivity.sh.getString("login_id", null);

        setConfirmdata();

        txt_driver_task4_booking_no = (TextView) v.findViewById(R.id.txt_driver_task4_booking_no);
        txt_driver_task4_pickup_town = (TextView) v.findViewById(R.id.txt_driver_task4_pickup_town);
        txt_driver_task4_pickup_street = (TextView) v.findViewById(R.id.txt_driver_task4_pickup_street);
        txt_driver_task4_drop_town = (TextView) v.findViewById(R.id.txt_driver_task4_drop_town);
        txt_driver_task4_drop_street = (TextView) v.findViewById(R.id.txt_driver_task4_drop_street);
        txt_driver_task4_amount = (TextView) v.findViewById(R.id.txt_driver_task4_amount);
        txt_driver_task4_status = (TextView) v.findViewById(R.id.txt_driver_task4_status);

        btn_driver_task4_stop2 = (Button) v.findViewById(R.id.btn_driver_task4_stop2);
        btn_driver_task4_completed = (Button) v.findViewById(R.id.btn_driver_task4_completed);

        btn_driver_task4_stop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "yes";
                setStop();
            }
        });

        btn_driver_task4_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "yes";
                setComplete();
            }
        });


        return v;
    }

    /*
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.driver_task4_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng pickup = new LatLng(Double.parseDouble(DriverHome.driver_pickup_latitude), Double.parseDouble(DriverHome.driver_pickup_longitude));
        LatLng drop = new LatLng(Double.parseDouble(DriverHome.driver_drop_latitude), Double.parseDouble(DriverHome.driver_drop_longitude));
        mMap.addMarker(new MarkerOptions().position(pickup).title("Pickup Location: "+driver_picktown_code)).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(drop).title("Drop Location: "+driver_droptown_code));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pickup));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        mMap.addPolyline(new PolylineOptions()
                .add(pickup, drop)
                .width(5)
                .color(Color.BLUE));

    }*/


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

                    driver_accepted = jArray.getString("driveraccepted");
                    Log.i("Driver accepted is", driver_accepted);

                    driver_picked = jArray.getString("driverpicked");
                    Log.i("Driver picked is", driver_picked);

                    driver_stop1 = jArray.getString("driverstop1");
                    Log.i("Booking stop1 is", driver_stop1);

                    driver_stop2 = jArray.getString("driverstop2");
                    Log.i("Booking stop2 is", driver_stop2);

                    driver_stop3 = jArray.getString("driverstop3");
                    Log.i("Booking stop3 is", driver_stop3);

                    driver_completed = jArray.getString("drivercompleted");
                    Log.i("Booking status is", driver_completed);


                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (booking_data.equals("yes")) {

                        try {

                            booking_data = jArray.getString("data");

                            Log.i("Confirm Rate in try block is", booking_data);

                            txt_driver_task4_booking_no.setText(driver_confirm_booking_id);
                            txt_driver_task4_pickup_town.setText(driver_picktown_code);
                            txt_driver_task4_pickup_street.setText(driver_picktown_address);
                            txt_driver_task4_drop_town.setText(driver_droptown_code);
                            txt_driver_task4_drop_street.setText(driver_droptown_address);
                            if(driver_booking_amount.toString().equals("A/C")){
                                txt_driver_task4_amount.setText(driver_booking_amount);
                            }else{
                                txt_driver_task4_amount.setText("€"+driver_booking_amount);
                            }

                            txt_driver_task4_status.setText("Stop1 Activated");


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }
                    } else {

                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
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
    public void setStop()
    {
        System.out.println("inside to get Data for Confirm Page");

        RequestParams params = new RequestParams();


        params.put("booking", driver_confirm_booking_id);
        System.out.print(driver_confirm_booking_id);
        Log.e("booking id is", driver_confirm_booking_id);

        params.put("status", status);
        Log.e("Status is", status);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(UrlStop, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {
                Log.d("response for Confirm Page connect", response);
                String str_response = response;
                Log.e("Confirm Page URL", Url);
                try {

                    jArray = new JSONObject(response);

                    update_message = jArray.getString("msg");
                    Log.i("Booking Data is", update_message);


                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (update_message.equals("updated")) {

                        try {

                            update_message = jArray.getString("msg");

                            Log.i("Confirm Rate in try block is", update_message);

                            Fragment fr;
                            fr = new DriverHomeTaskFive();
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
                        Toast.makeText(getActivity(), "Action not updated", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
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
    public void setComplete()
    {
        System.out.println("inside to get Data for Confirm Page");

        RequestParams params = new RequestParams();


        params.put("booking", driver_confirm_booking_id);
        System.out.print(driver_confirm_booking_id);
        Log.e("booking id is", driver_confirm_booking_id);

        params.put("status", status);
        Log.e("Status is", status);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(UrlComplete, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {
                Log.d("response for Confirm Page connect", response);
                String str_response = response;
                Log.e("Confirm Page URL", Url);
                try {

                    jArray = new JSONObject(response);

                    update_message = jArray.getString("msg");
                    Log.i("Booking Data is", update_message);


                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (update_message.equals("updated")) {

                        try {

                            update_message = jArray.getString("msg");

                            Log.i("Confirm Rate in try block is", update_message);

                            Fragment fr;
                            fr = new DriverHomeTaskSeven();
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
                        Toast.makeText(getActivity(), "Action not updated", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
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
