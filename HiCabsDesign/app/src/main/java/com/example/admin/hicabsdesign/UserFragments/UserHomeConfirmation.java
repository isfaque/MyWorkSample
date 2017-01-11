package com.example.admin.hicabsdesign.UserFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
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
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.SplashActivity;
import com.example.admin.hicabsdesign.UserLogin;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 13/05/2016.
 */
public class UserHomeConfirmation extends Fragment {

    public static String page_loaded;

    //String Url ="http://dev.hicabs-system.com/Api/booking_notification";
    String Url = "http://hicabs-system.com/Webservices/addbooking";
    TextView txt_user_booking_date, txt_user_booking_time, txt_user_pick_town, txt_user_pick_address, txt_user_drop_town, txt_user_drop_address, txt_user_amount, txt_user_paymentmode, txt_user_passenger_no, txt_user_drivernote;

    Button btn_user_confirm_booking, btn_user_back_confirmation;
    AlertDialog alertDialog;

    JSONObject jObject;
    JSONObject jArray;

    private WebView webView;

    public static String fdate, ftime, ffrom, fto, fpeople, fPickTown, fDropTown, frate, fpayment_mode, fdrivernote, Booking_Id,Login_Id, Confirm_Message,Confirm_Status, Confirm_image_path, Confirm_image_link;
    public static String current_booking_id = "null";
    public static String Confirm_add_booking, Confirm_msg, Confirm_rate, Confirm_id, Confirm_booking_id;
    public static String C_booking_date, C_booking_time, C_pickup_code, C_pickup_id, C_pickup_street, C_client_id, C_passenger_no;
    public static String cant_accept;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home_confirmation_user, container, false);

        //Get webview
        webView = (WebView) v.findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        // retain this fragment
        setRetainInstance(true);

        hideKeyboard();

        txt_user_booking_date = (TextView) v.findViewById(R.id.txt_user_booking_date);
        txt_user_booking_time = (TextView) v.findViewById(R.id.txt_user_booking_time);
        txt_user_pick_town = (TextView) v.findViewById(R.id.txt_user_pick_town);
        txt_user_pick_address = (TextView) v.findViewById(R.id.txt_user_pick_address);
        txt_user_drop_town = (TextView) v.findViewById(R.id.txt_user_drop_town);
        txt_user_drop_address = (TextView) v.findViewById(R.id.txt_user_drop_address);
        txt_user_amount = (TextView) v.findViewById(R.id.txt_user_amount);
        txt_user_paymentmode = (TextView) v.findViewById(R.id.txt_user_paymentmode);
        txt_user_passenger_no = (TextView) v.findViewById(R.id.txt_user_passenger_no);
        txt_user_drivernote = (TextView) v.findViewById(R.id.txt_user_drivernote);

        txt_user_passenger_no.setPaintFlags(txt_user_passenger_no.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btn_user_confirm_booking = (Button) v.findViewById(R.id.btn_user_confirm_booking);
        btn_user_back_confirmation = (Button) v.findViewById(R.id.btn_user_back_confirmation);

        fdate = UserHome.selected_user_booking_date;
        txt_user_booking_date.setText(fdate);

        ftime = UserHome.selected_user_booking_time;
        txt_user_booking_time.setText(ftime);

        ffrom = UserHomePickup.Spickaddress;
        txt_user_pick_address.setText(ffrom);

        fto = UserHomeDrop.Sdropaddress;
        txt_user_drop_address.setText(fto);

        frate = UserHomeDrop.Confirm_rate;

        fpayment_mode = SplashActivity.sh.getString("login_payment_mode", null).trim();

        if(fpayment_mode.equals("credit")){
            txt_user_amount.setText("€"+frate);
            txt_user_paymentmode.setText("(On Account)");
        }else{
            txt_user_amount.setText("€"+frate);
        }



        fdrivernote = UserHomeDrop.DropNote;
        txt_user_drivernote.setText(fdrivernote);

        fpeople = UserHome.selected_user_passenger_no;
        txt_user_passenger_no.setText(fpeople);

        fPickTown = UserHomePickup.AutoPickUp.toUpperCase();
        txt_user_pick_town.setText(fPickTown);

        fDropTown = UserHomeDrop.AutoDrop.toUpperCase();
        txt_user_drop_town.setText(fDropTown);

        btn_user_confirm_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard();
                btn_user_confirm_booking.setEnabled(false);

                //setListSendData();
                setConfirmdata();

            }
        });

        btn_user_back_confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment fr;
                fr = new UserHomeDrop();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });


        return v;
    }

    /** Hide keyboard function **/
    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /** sends booking id in server for booking confirmation **/
    @SuppressLint("LongLogTag")
    public void setListSendData(){

        Booking_Id = UserHomeDrop.Confirm_id;
        current_booking_id = Booking_Id;
        SplashActivity.editor.putString("booking_id", current_booking_id);
        SplashActivity.editor.commit();
        Log.e("Confirm Page Booking_Id is", Booking_Id);

        Login_Id = SplashActivity.sh.getString("login_id", null);

        System.out.println("inside to Send Data from Confirmation Page");

        RequestParams params = new RequestParams();

        params.put("loginid", Login_Id);
        System.out.println(Login_Id);
        Log.e("Confirm Page Login Id is", Login_Id);

        params.put("bookingid", Booking_Id);
        System.out.println(Booking_Id);
        Log.e("Confirm Page Booking Id is", Booking_Id);

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(Url, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {
                Log.d("response for Confirm Page Send Data to Server", response);
                String str_response = response;
                Log.e("Confirm Page URL", Url);
                try {

                    jObject = new JSONObject(str_response);

                    Confirm_Status = jObject.getString("status");
                    Log.i("Confirm Status is", Confirm_Status);

                    Confirm_Message = jObject.getString("msg");
                    Log.i("Confirm Message is", Confirm_Message);



                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (response.equals("0")) {
                        btn_user_confirm_booking.setEnabled(true);

                        //Toast.makeText(getActivity(), "Status is Cancel!", Toast.LENGTH_SHORT).show();

                    } else if (response.equals("1")) {

                        //Toast.makeText(getActivity(), "Status is Complete!", Toast.LENGTH_SHORT).show();
                    } else if (response.equals("2")) {

                        //Toast.makeText(getActivity(), "Status is not accepted!", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    // TODO: handle exception
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
                btn_user_confirm_booking.setEnabled(true);

            }
        });

    }


    /** function sends booking info in server and get calculated amount from server  **/
    @SuppressLint("LongLogTag")
    public void setConfirmdata()
    {
        System.out.println("inside to get Data for Confirm Page");

        C_booking_date =UserHome.selected_user_booking_date;

        C_booking_time =UserHome.selected_user_booking_time;

        C_passenger_no = UserHome.selected_user_passenger_no;

        C_pickup_code =UserHomePickup.Pick_Selected_Code;

        C_pickup_id =UserHomePickup.Pick_Selected_Id;

        C_pickup_street =UserHomePickup.Spickaddress;

        C_client_id = SplashActivity.sh.getString("login_id", null);

        RequestParams params = new RequestParams();

        params.put("pickupcode", C_pickup_code);
        System.out.print(C_pickup_code);
        Log.e("Pickup Page C_pickup_code is", C_pickup_code);

        params.put("pickupid", C_pickup_id);
        System.out.print(C_pickup_id);
        Log.e("Pickup Page C_pickup_id is", C_pickup_id);

        params.put("pickupstreet", C_pickup_street);
//        params.put("pickupstreet", PickupDetailsPage.Spickaddress);
        System.out.print(C_pickup_street);
        Log.e("Pickup Page C_pickup_street is", C_pickup_street);

        params.put("dropcode", UserHomeDrop.Drop_Selected_Code);
//        System.out.print(Town_Code_drop);
        Log.e("DropOff page C_drop_code is", UserHomeDrop.Drop_Selected_Code);

        params.put("dropid", UserHomeDrop.Drop_Selected_Id);
        System.out.print(UserHomeDrop.Drop_Id);
        Log.e("Drop Page C_drop_id is", UserHomeDrop.Drop_Selected_Id);

        params.put("dropstreet", UserHomeDrop.Sdropaddress);
        System.out.print(UserHomeDrop.Sdropaddress);
        Log.e("DropOff page Sdropaddress is", UserHomeDrop.Sdropaddress);

        params.put("dropnote", UserHomeDrop.DropNote);
        System.out.print(UserHomeDrop.DropNote);
        Log.e("DropOff page Sdropaddress is", UserHomeDrop.DropNote);


        params.put("bookingtime", C_booking_time);
        System.out.print(C_booking_time);
        Log.e("DropOff page C_booking_time is", C_booking_time);

        params.put("datepicker", C_booking_date);
        System.out.print(C_booking_date);
        Log.e("DropOff page C_booking_date is", C_booking_date);

        params.put("passenger", C_passenger_no);
        System.out.print(C_passenger_no);
        Log.e("C_passenger_no is", C_passenger_no);

        params.put("client", C_client_id);
//        params.put("client","1");
        System.out.print(C_client_id);
        Log.e("DropOff page Login Id is", C_client_id);

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(Url, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {
                Log.d("response for Confirm Page connect", response);
                String str_response = response;
                Log.e("Confirm Page URL", Url);
                try {

                    jArray = new JSONObject(response);

                    Confirm_add_booking = jArray.getString("addbooking");
                    Log.i("Confirm Add Booking Id is", Confirm_add_booking);

                    Confirm_msg = jArray.getString("msg");
                    Log.i("Confrim Message is", Confirm_msg);

                    Confirm_rate = jArray.getString("rate");
                    Log.i("Confirm Rate is", Confirm_rate);

                    Confirm_id = jArray.getString("id");
                    Log.i("Confirm Id is", Confirm_id);

                    Confirm_booking_id = jArray.getString("Bookingid");
                    Log.i("Confirm Booking Id is", Confirm_booking_id);

                    Confirm_image_path = jArray.getString("imagepath");
                    Log.i("Confirm image path is", Confirm_image_path);

                    Confirm_image_link = jArray.getString("imagelink");
                    Log.i("Confirm image path is", Confirm_image_link);



                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (Confirm_add_booking.equals("done")) {


                        try {

                            Confirm_rate = jArray.getString("rate");
//                            Drate =Confirm_rate.getBytes().toString();

                            Log.i("Confirm Rate in try block is", Confirm_rate);

//                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//                            alertDialog.setTitle("Drop-up Destination is!");
////                                    alertDialog.setMessage(jarray.getString("msg");
//                            alertDialog.setMessage(e.getString("town"));
////                                    alertDialog.setIcon(R.drawable.welcome);
//
//
//                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    btnSubmit.setEnabled(true);

                            UserHome.selected_user_booking_date = "";
                            UserHome.selected_user_booking_time = "";
                            UserHome.selected_user_passenger_no = "";
                            UserHome.selected_user_passenger_position = 0;
                            UserHomePickup.Spickaddress = "";
                            UserHomePickup.AutoPickUp = "";
                            UserHomeDrop.Sdropaddress = "";
                            UserHomeDrop.AutoDrop = "";
                            UserHomeDrop.DropNote = "";

                            startWebView("http://hicabs-system.com/Bookings/listingslive/"+Confirm_id);

                            /*
                            Fragment fr;
                            fr = new UserHome();
                            FragmentManager fm = getFragmentManager();
                            android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.main_content, fr);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //Toast.makeText(getActivity(), "Thank You For Booking!", Toast.LENGTH_SHORT).show();

                            Toast toast = new Toast(getActivity());
                            View view = getActivity().getLayoutInflater().inflate(R.layout.toast_view, null);
                            toast.setView(view);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.show();*/
//                                }
//                            });
//
//                            alertDialog.show();
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }
                    }else if(Confirm_add_booking.equals("inrange")){
                        btn_user_confirm_booking.setEnabled(true);
                        Toast.makeText(getActivity(), Confirm_msg, Toast.LENGTH_LONG).show();

                    }else if(Confirm_add_booking.equals("failed")){
                        btn_user_confirm_booking.setEnabled(true);
                        Toast.makeText(getActivity(), "Your booking failed, Please try again later", Toast.LENGTH_LONG).show();

                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    btn_user_confirm_booking.setEnabled(true);
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
                btn_user_confirm_booking.setEnabled(true);

            }
        });

    }


    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

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

                        btn_user_confirm_booking.setEnabled(true);

                        //openDialog();

                        Fragment fr;
                        fr = new UserHome();
                        FragmentManager fm = getFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.main_content, fr);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //Toast.makeText(getActivity(), "Thank You For Booking!", Toast.LENGTH_SHORT).show();


                        Toast toast = new Toast(getActivity());
                        View view2 = getActivity().getLayoutInflater().inflate(R.layout.toast_view, null);
                        toast.setView(view2);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                    }
                }, 3000);




/*
                try{



                    //page_loaded = "true";

                    //Toast.makeText(getActivity(), "Page Loaded Successfully", Toast.LENGTH_LONG);
                    /*
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;

                        Toast.makeText(getActivity(), "Page Loaded Successfully", Toast.LENGTH_LONG);
                    }*//*
                }catch(Exception exception){
                    exception.printStackTrace();
                }*/
            }

        });

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);

        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        */

        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

        //Load url in webview
        webView.loadUrl(url);


    }


    /** function for open thank you dialog **/
    public void openDialog() {
        final Dialog dialog = new Dialog(getActivity()); // Context, this, etc.
        dialog.setTitle("Thank you for Booking");
        dialog.setContentView(R.layout.fragment_home_thanku_popup);

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(false).cacheInMemory(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP

        //your image url
        //String url = "http://stacktips.com/wp-content/uploads/2014/05/UniversalImageLoader-620x405.png";
        //String url = "http://icons.iconarchive.com/icons/uiconstock/flat-file-type/512/swf-icon.png";
        String url = Confirm_image_path;

        ImageLoader imageLoader = ImageLoader.getInstance();

        MemoryCacheUtils.removeFromCache(url, ImageLoader.getInstance().getMemoryCache());
        //DiscCacheUtils.removeFromCache(url, ImageLoader.getInstance().getDiscCache());

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisc(false).resetViewBeforeLoading(false)
                .showImageForEmptyUri(R.drawable.ic_cast_off_light)
                .showImageOnFail(R.drawable.ic_cast_disabled_light)
                .showImageOnLoading(R.drawable.default_image).build();

//initialize image view
        ImageView imageView = (ImageView) dialog.findViewById(R.id.img_user_thanku);

//download and display image from url
        imageLoader.displayImage(url, imageView, options);

        // Getting a reference to Close button, and close the popup when clicked.
        ImageButton cancel = (ImageButton) dialog.findViewById(R.id.btn_user_thanku_exit);
        ImageView thanku_img = (ImageView) dialog.findViewById(R.id.img_user_thanku);

        thanku_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(Confirm_image_link);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }
}
