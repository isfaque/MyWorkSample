package com.example.admin.hicabsdesign.UserFragments;

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

import com.example.admin.hicabsdesign.DriverFragments.DriverHome;
import com.example.admin.hicabsdesign.DriverFragments.DriverHomeTaskTwo;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by admin on 16/05/2016.
 */
public class UserBookingStatus extends Fragment {

    private GoogleMap mMap;

    String UrlCancel ="http://hicabs-system.com/Api/usercancelbooking";
    AlertDialog alertDialog;

    ProgressDialog progressDialog;

    private WebView webView;

    JSONObject jArray;

    Button btn_user_booking_cancel;
    TextView txt_user_booking_status, txt_user_booking_id, txt_user_booking_date, txt_user_booking_time, txt_user_pick_city, txt_user_pick_address, txt_user_drop_city, txt_user_drop_address, txt_user_booking_amount;
    public static String user_booking_status, user_booking_id, user_dbooking_id, user_booking_date, user_booking_time, user_pick_city, user_pick_address, user_drop_city, user_drop_address, user_booking_amount;
    public static String update_hours, update_id, update_message;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_booking_status_user, container, false);

        //Get webview
        webView = (WebView) v.findViewById(R.id.webViewbookingcancel);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        txt_user_booking_status = (TextView) v.findViewById(R.id.txt_user_booking_status);
        txt_user_booking_id = (TextView) v.findViewById(R.id.txt_user_booking_id);
        txt_user_booking_date = (TextView) v.findViewById(R.id.txt_user_booking_date);
        txt_user_booking_time = (TextView) v.findViewById(R.id.txt_user_booking_time);
        txt_user_pick_city = (TextView) v.findViewById(R.id.txt_user_pick_city);
        txt_user_pick_address = (TextView) v.findViewById(R.id.txt_user_pick_address);
        txt_user_drop_city = (TextView) v.findViewById(R.id.txt_user_drop_city);
        txt_user_drop_address = (TextView) v.findViewById(R.id.txt_user_drop_address);
        txt_user_booking_amount = (TextView) v.findViewById(R.id.txt_user_booking_amount);

        btn_user_booking_cancel = (Button) v.findViewById(R.id.btn_user_booking_cancel);

        user_booking_status = UserBookingList.user_booking_status;
        user_booking_id = UserBookingList.user_booking_id;
        user_dbooking_id = UserBookingList.user_dbooking_id;
        user_booking_date = UserBookingList.user_booking_date;
        user_booking_time = UserBookingList.user_booking_time;
        user_pick_city = UserBookingList.user_picktown_city;
        user_pick_address = UserBookingList.user_picktown_address;
        user_drop_city = UserBookingList.user_droptown_city;
        user_drop_address = UserBookingList.user_droptown_address;
        user_booking_amount = UserBookingList.user_booking_amount;

        txt_user_booking_status.setText("Status: "+user_booking_status);
        txt_user_booking_id.setText("Booking ID: "+user_dbooking_id);
        txt_user_booking_date.setText("Booking Date: "+user_booking_date);
        txt_user_booking_time.setText("Booking Time: "+user_booking_time);
        txt_user_pick_city.setText("Pickup Town: "+user_pick_city);
        txt_user_pick_address.setText("Pickup Address: "+user_pick_address);
        txt_user_drop_city.setText("Drop Town: "+user_drop_city);
        txt_user_drop_address.setText("Drop Address: "+user_drop_address);
        txt_user_booking_amount.setText("Booking Amount: €"+user_booking_amount);

        btn_user_booking_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_user_booking_cancel.setEnabled(false);

                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }

                setCancel();

            }
        });



        return v;
    }

    /*
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.user_map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        // Add a marker in Sydney and move the camera
        LatLng pickup = new LatLng(Double.parseDouble(UserBookingList.user_pickup_latitude), Double.parseDouble(UserBookingList.user_pickup_longitude));
        LatLng drop = new LatLng(Double.parseDouble(UserBookingList.user_drop_latitude), Double.parseDouble(UserBookingList.user_drop_longitude));
        mMap.addMarker(new MarkerOptions().position(pickup).title("Pickup Location: "+UserBookingList.user_picktown_city)).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(drop).title("Drop Location: "+UserBookingList.user_droptown_city));
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
    public void setCancel()
    {
        System.out.println("inside to get Data for Confirm Page");

        RequestParams params = new RequestParams();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentdatetime = df.format(Calendar.getInstance().getTime());


        params.put("bookingid", user_booking_id);
        System.out.print(user_booking_id);
        Log.e("booking id is", user_booking_id);

        params.put("bookingdate", user_booking_date);
        System.out.print(user_booking_date);
        Log.e("booking date is", user_booking_date);

        params.put("bookingtime", user_booking_time);
        System.out.print(user_booking_time);
        Log.e("booking time is", user_booking_time);

        params.put("currentdatetime", currentdatetime);
        System.out.print(currentdatetime);
        Log.e("Current date time is", currentdatetime);

        params.put("status", "cancel");
        Log.e("Status is", "cancel");

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(UrlCancel, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {
                Log.d("response for Confirm Page connect", response);
                String str_response = response;
                Log.e("Confirm Page URL", UrlCancel);
                try {

                    jArray = new JSONObject(response);

                    update_message = jArray.getString("msg");
                    Log.i("Booking Data is", update_message);

                    update_hours = jArray.getString("hours");
                    Log.i("Booking Data is", update_hours);

                    update_id = jArray.getString("id");
                    Log.i("Booking Data is", update_id);


                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (update_message.equals("done")) {

                        try {

                            update_hours = jArray.getString("hours");

                            Log.i("Confirm Rate in try block is", update_hours);

                            startWebView("http://hicabs-system.com/Bookings/checkcancellive/"+user_booking_id);

                            //Toast.makeText(getActivity(), "Your booking canceled", Toast.LENGTH_LONG).show();



                        } catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }

                    }else if(update_message.equals("beforetime")){

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                        btn_user_booking_cancel.setEnabled(true);

                        Toast.makeText(getActivity(), "You can't cancel your booking before 1 hour", Toast.LENGTH_LONG).show();

                    } else {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                        btn_user_booking_cancel.setEnabled(true);

                        Toast.makeText(getActivity(), "Action not updated", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    // TODO: handle exception

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    btn_user_booking_cancel.setEnabled(true);

                    Toast tost = new Toast(getActivity());
                    tost.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    tost.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                    //Intent i = new Intent(getActivity(), SplashActivity.class);
                    //startActivity(i);
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

                        btn_user_booking_cancel.setEnabled(true);

                        Toast.makeText(getActivity(), "Your booking canceled", Toast.LENGTH_LONG).show();

                        Fragment fr;
                        fr = new UserBooking();
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
