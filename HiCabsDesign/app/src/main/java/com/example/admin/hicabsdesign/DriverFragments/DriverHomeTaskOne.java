package com.example.admin.hicabsdesign.DriverFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.hicabsdesign.DriverMainActivity;
import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.SplashActivity;
import com.example.admin.hicabsdesign.UserFragments.UserBooking;
import com.example.admin.hicabsdesign.UserFragments.UserHome;
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
 * Created by admin on 23/05/2016.
 */
public class DriverHomeTaskOne extends Fragment {

    private GoogleMap mMap;

    String Url ="http://hicabs-system.com/Api/drivertaskone";
    JSONObject jArray;
    AlertDialog alertDialog;

    private WebView webView;

    ProgressDialog progressDialog;


    Button btn_driver_task1_accept, btn_driver_task1_reject;
    TextView txt_driver_task1_booking_no, txt_driver_task1_booking_date, txt_driver_task1_booking_time, txt_driver_task1_pickup_town, txt_driver_task1_pickup_street, txt_driver_task1_pickup_details, txt_driver_task1_drop_town, txt_driver_task1_drop_street, txt_driver_task1_amount, txt_driver_task1_status;

    public static String driver_task1_pickup_details, driver_task1_booking_no, driver_task1_booking_date, driver_task1_booking_time, driver_task1_dbooking_no, driver_task1_pickup_town, driver_task1_pickup_street, driver_task1_drop_town, driver_task1_drop_street, driver_task1_amount, driver_task1_status;
    public static String status, driver_id, update_message, token_message_s, token_message_f;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home_task_one_driver, container, false);

        DriverMainActivity.userHomeShow = false;

        btn_driver_task1_accept = (Button) v.findViewById(R.id.btn_driver_task1_accept);

        //Get webview
        webView = (WebView) v.findViewById(R.id.webViewdriver1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        txt_driver_task1_booking_no = (TextView) v.findViewById(R.id.txt_driver_task1_booking_no);
        txt_driver_task1_booking_date = (TextView) v.findViewById(R.id.txt_driver_task1_booking_date);
        txt_driver_task1_booking_time = (TextView) v.findViewById(R.id.txt_driver_task1_booking_time);
        txt_driver_task1_pickup_town = (TextView) v.findViewById(R.id.txt_driver_task1_pickup_town);
        txt_driver_task1_pickup_street = (TextView) v.findViewById(R.id.txt_driver_task1_pickup_street);
        txt_driver_task1_pickup_details = (TextView) v.findViewById(R.id.txt_driver_task1_pickup_details);
        //txt_driver_task1_drop_town = (TextView) v.findViewById(R.id.txt_driver_task1_drop_town);
        //txt_driver_task1_drop_street = (TextView) v.findViewById(R.id.txt_driver_task1_drop_street);
        txt_driver_task1_amount = (TextView) v.findViewById(R.id.txt_driver_task1_amount);
        txt_driver_task1_status = (TextView) v.findViewById(R.id.txt_driver_task1_status);

        driver_task1_booking_no = DriverHome.driver_confirm_booking_id;
        driver_task1_booking_date = DriverHome.driver_booking_date;
        driver_task1_booking_time = DriverHome.driver_booking_time;
        driver_task1_dbooking_no = DriverHome.driver_dbooking_id;
        driver_task1_pickup_town = DriverHome.driver_picktown_code;
        driver_task1_pickup_street = DriverHome.driver_picktown_address;
        driver_task1_drop_town = DriverHome.driver_droptown_code;
        driver_task1_drop_street = DriverHome.driver_droptown_address;
        driver_task1_amount = DriverHome.driver_booking_amount;
        driver_task1_pickup_details = DriverHome.driver_booking_nameonboard+", "+DriverHome.driver_booking_flightdetails+", "+DriverHome.driver_booking_comment;
        driver_task1_status = DriverHome.driver_booking_status;

        txt_driver_task1_booking_no.setText(driver_task1_dbooking_no);
        txt_driver_task1_booking_date.setText(driver_task1_booking_date);
        txt_driver_task1_booking_time.setText(driver_task1_booking_time);
        txt_driver_task1_pickup_town.setText(driver_task1_pickup_town.toUpperCase());
        txt_driver_task1_pickup_street.setText(driver_task1_pickup_street);
        txt_driver_task1_pickup_details.setText(driver_task1_pickup_details);
        //txt_driver_task1_drop_town.setText(driver_task1_drop_town);
        //txt_driver_task1_drop_street.setText(driver_task1_drop_street);
        if(driver_task1_amount.toString().equals("A/C")){
            txt_driver_task1_amount.setText(driver_task1_amount);
        }else{
            txt_driver_task1_amount.setText("€"+driver_task1_amount);
        }

        txt_driver_task1_status.setText(driver_task1_status);

        btn_driver_task1_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                btn_driver_task1_accept.setEnabled(false);

                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }

                status = "yes";
                setConfirmdata();
            }
        });


        return v;
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
        LatLng pickup = new LatLng(Double.parseDouble(DriverHome.driver_pickup_latitude), Double.parseDouble(DriverHome.driver_pickup_longitude));
        //LatLng drop = new LatLng(Double.parseDouble(DriverHome.driver_drop_latitude), Double.parseDouble(DriverHome.driver_drop_longitude));
        mMap.addMarker(new MarkerOptions().position(pickup).title("Pickup Location: "+driver_task1_pickup_town)).showInfoWindow();
        //mMap.addMarker(new MarkerOptions().position(drop).title("Drop Location: "+driver_task1_drop_town));
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


        params.put("booking", driver_task1_booking_no);
        System.out.print(driver_task1_booking_no);
        Log.e("booking id is", driver_task1_booking_no);

        params.put("status", status);
        Log.e("Status is", status);

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

                    update_message = jArray.getString("msg");
                    Log.i("Booking Data is", update_message);
                    token_message_s = jArray.getString("success");
                    Log.i("success message is", token_message_s);
                    token_message_f = jArray.getString("failure");
                    Log.i("failure message is", token_message_f);


                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (update_message.equals("updated")) {

                        try {

                            update_message = jArray.getString("msg");

                            Log.i("Confirm Rate in try block is", update_message);

                            startWebView("http://hicabs-system.com/Bookings/ledchecklive/"+driver_task1_booking_no);

                            /*
                            Fragment fr;
                            fr = new DriverHomeTaskTwo();
                            FragmentManager fm = getFragmentManager();
                            android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.main_content, fr);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();*/
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }
                    } else if(update_message.equals("notupdated")) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                        btn_driver_task1_accept.setEnabled(true);
                        Toast.makeText(getActivity(), "Job assigned to other one", Toast.LENGTH_LONG).show();

                        Fragment fr;
                        fr = new DriverHome();
                        FragmentManager fm = getFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.main_content, fr);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }else{
                        Toast.makeText(getActivity(), "Action not updated", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    btn_driver_task1_accept.setEnabled(true);
                    Toast tost = new Toast(getActivity());
                    tost.makeText(getActivity(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                    tost.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                    //Intent i = new Intent(getActivity(), SplashActivity.class);
                    //startActivity(i);
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Throwable e) {
                btn_driver_task1_accept.setEnabled(true);
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

    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            //ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                /*
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }*/
            }
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(webView, url);
                //Toast.makeText(getActivity(), "Page Loaded Successfully", Toast.LENGTH_SHORT).show();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }

                        btn_driver_task1_accept.setEnabled(true);

                        Fragment fr;
                        fr = new DriverHomeTaskTwo();
                        FragmentManager fm = getFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.main_content, fr);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }, 3000);


            }

        });

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);


        //Load url in webview
        webView.loadUrl(url);


    }
}
