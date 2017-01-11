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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.SplashActivity;
import com.example.admin.hicabsdesign.UserLogin;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 13/05/2016.
 */
public class UserHomeDrop extends Fragment {

    EditText edit_user_drop_address, edit_user_drop_note;
    AutoCompleteTextView auto_user_drop_city;
    Button btn_user_next_bs3, btn_user_back_bs3;

    /** Earlier Url
    String Url = "http://projects.aarnaitsolutions.com/hicab/Webservices/addbooking";
     */

    String Url = "http://hicabs-system.com/Webservices/addbookingrate";


    String url = "http://hicabs-system.com/Webservices/citylist";
    private static final String TAG_CITY = "city";
    final String TAG = "DropOff Page Data is";

    public static String Drop_Id, Town_drop, Town_Code_drop, Sdropaddress, AutoDrop, Drop_Selected_Id, Drop_Selected_Code, DropNote;
    public static String C_booking_date, C_booking_time, C_pickup_code, C_pickup_id, C_pickup_street, C_client_id, C_passenger_no;
    public static String Confirm_add_booking, Confirm_msg, Confirm_rate, Confirm_id, Confirm_booking_id;

    JSONArray city;
    JSONObject e,jArray;

    List<String> responseIdDrop = new ArrayList<String>();
    List<String>  responseListDrop = new ArrayList<String>();
    List<String>  responseCodeDrop = new ArrayList<String>();

    ArrayAdapter<String> adapter1;
    AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home_drop_user, container, false);

        edit_user_drop_address = (EditText) v.findViewById(R.id.edit_user_drop_address);
        edit_user_drop_note = (EditText) v.findViewById(R.id.edit_user_drop_note);
        auto_user_drop_city = (AutoCompleteTextView) v.findViewById(R.id.auto_user_drop_city);
        btn_user_next_bs3 = (Button) v.findViewById(R.id.btn_user_next_bs3);
        btn_user_back_bs3 = (Button) v.findViewById(R.id.btn_user_back_bs3);

        setListData();

        if(AutoDrop != null || AutoDrop != "")
        {
            edit_user_drop_address.setText(Sdropaddress);
            auto_user_drop_city.setText(AutoDrop);
            edit_user_drop_note.setText(DropNote);
        }

        btn_user_next_bs3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sdropaddress = edit_user_drop_address.getText().toString();
                AutoDrop = auto_user_drop_city.getText().toString();
                DropNote = edit_user_drop_note.getText().toString();

                final long changeTime = 1000L;
                btn_user_next_bs3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int cityindexposition;
                        cityindexposition = responseListDrop.indexOf(AutoDrop);
                        if(cityindexposition < 0){
                            Toast.makeText(getActivity(), "Please select correct City", Toast.LENGTH_SHORT).show();
                        }else {
                            Drop_Selected_Id = responseIdDrop.get(cityindexposition);
                            Drop_Selected_Code = responseCodeDrop.get(cityindexposition);

                            Log.d(TAG, "Index Position: " + cityindexposition);

                            Log.d("Drop Off Page", "BUTTON PRESSED");

                            setConfirmdata();

                        }

                    }
                }, changeTime);



            }
        });

        btn_user_back_bs3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr;
                fr = new UserHomePickup();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });



        return v;
    }

    /** Function for get city code list from server **/
    public void setListData()
    {
        System.out.println("inside to get citylist of Drop Off Page");

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, null, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {
                Log.d("response for DropOff Page connect", response);
                String str_response = response;
                Log.e("DropOff Page URL", url);

                try {

                    JSONObject jsonObj = new JSONObject(str_response);

                    // Getting JSON Array node
                    city = jsonObj.getJSONArray(TAG_CITY);

                    // looping through All City
//                    for (int i = 0; i < city.length(); i++) {
                    for (int i = 0; i < city.length(); i++) {
                        e = city.getJSONObject(i);

                        Drop_Id = e.getString("id");
                        Log.i("Drop_Id is", Drop_Id);
                        responseIdDrop.add(Drop_Id);

                        Town_drop = e.getString("town");
                        Log.i("Town_one_drop is", Town_drop);

                        responseListDrop.add(Town_drop);

                        Log.e("Drop City list is", String.valueOf(responseListDrop));

                        adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, responseListDrop);
                        auto_user_drop_city.setAdapter(adapter1);
                        auto_user_drop_city.setThreshold(1);
                        auto_user_drop_city.clearComposingText();

                        Town_Code_drop = e.getString("townCode");
                        Log.i("TownCode from Drop Page", Town_Code_drop);
                        responseCodeDrop.add(Town_Code_drop);

                        // show the values in our logcat
                        Log.e(TAG, "DropId: " + Drop_Id
                                + ", DropAddress: " + Town_drop
                                + ", DropTownCode: " + Town_Code_drop);

                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }

                try {
                    if (Drop_Id.equals("1")) {
                        btn_user_next_bs3.setEnabled(true);
                        try {

                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Drop-up Destination is!");

                            alertDialog.setMessage(e.getString("town"));

                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    btn_user_next_bs3.setEnabled(true);
                                }
                            });

                            alertDialog.show();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }
                        //passwordedit.requestFocus();
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
                btn_user_next_bs3.setEnabled(true);

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

        params.put("dropcode", Drop_Selected_Code);
//        System.out.print(Town_Code_drop);
        Log.e("DropOff page C_drop_code is", Drop_Selected_Code);

        params.put("dropid", Drop_Selected_Id);
        System.out.print(Drop_Id);
        Log.e("Drop Page C_drop_id is", Drop_Selected_Id);

        params.put("dropstreet", Sdropaddress);
        System.out.print(Sdropaddress);
        Log.e("DropOff page Sdropaddress is", Sdropaddress);

        params.put("dropnote", DropNote);
        System.out.print(DropNote);
        Log.e("DropOff page Sdropaddress is", DropNote);


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

                    Confirm_add_booking = jArray.getString("addbookingrate");
                    Log.i("Confirm Add Booking Id is", Confirm_add_booking);

                    Confirm_msg = jArray.getString("msg");
                    Log.i("Confrim Message is", Confirm_msg);

                    Confirm_rate = jArray.getString("rate");
                    Log.i("Confirm Rate is", Confirm_rate);


                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (Confirm_add_booking.equals("done")) {
                        btn_user_next_bs3.setEnabled(true);

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

                            Fragment fr;
                            fr = new UserHomeConfirmation();
                            FragmentManager fm = getFragmentManager();
                            android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.main_content, fr);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                            edit_user_drop_address.setText("");
                            auto_user_drop_city.setText("");
//                                }
//                            });
//
//                            alertDialog.show();
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }
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
                btn_user_next_bs3.setEnabled(true);

            }
        });

    }

}
