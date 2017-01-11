package com.example.admin.hicabsdesign.UserFragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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
import com.example.admin.hicabsdesign.UserMainActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 13/05/2016.
 */
public class UserHomePickup extends Fragment {

    String url = "http://hicabs-system.com/Webservices/citylist";
    private static final String TAG_CITY = "city";
    final String TAG = "PickUp Page Data is";

    AlertDialog alertDialog;

    EditText edit_user_pickup_address;
    Button btn_user_next_bs2, btn_user_back_bs2;
    public static AutoCompleteTextView auto_user_pickup_city;

    public static String Pick_Id, Town_one, Town_Code, Spickaddress, AutoPickUp, Pick_Selected_Id, Pick_Selected_Code;

    JSONArray city;
    JSONObject e;

    ArrayAdapter<String> adapter;

    List<String>  responseList = new ArrayList<String>();
    List<String> responseId = new ArrayList<String>();
    List<String>  responseCode = new ArrayList<String>();

    int PLACE_PICKER_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home_pickup_user, container, false);

        edit_user_pickup_address = (EditText) v.findViewById(R.id.edit_user_pickup_address);
        auto_user_pickup_city = (AutoCompleteTextView) v.findViewById(R.id.auto_user_pickup_city);
        auto_user_pickup_city.clearComposingText();

        setListData();

        if(AutoPickUp != null || AutoPickUp != "")
        {
            edit_user_pickup_address.setText(Spickaddress);
            auto_user_pickup_city.setText(AutoPickUp);
        }

        btn_user_next_bs2 = (Button) v.findViewById(R.id.btn_user_next_bs2);
        btn_user_back_bs2 = (Button) v.findViewById(R.id.btn_user_back_bs2);

        btn_user_next_bs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spickaddress = edit_user_pickup_address.getText().toString();
                AutoPickUp = auto_user_pickup_city.getText().toString();

                final long changeTime = 1000L;
                btn_user_next_bs2.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        int cityindexposition;
                        cityindexposition = responseList.indexOf(AutoPickUp);

                        if(cityindexposition < 0){
                            Toast.makeText(getActivity(), "Please select correct City", Toast.LENGTH_SHORT).show();
                        }else {
                            Pick_Selected_Id = responseId.get(cityindexposition);
                            Pick_Selected_Code = responseCode.get(cityindexposition);

                            Log.d(TAG, "Index Position: " + cityindexposition);

                            Log.d("PickupDetailsPage", "BUTTON PRESSED");

                            Fragment fr;
                            fr = new UserHomeDrop();
                            FragmentManager fm = getFragmentManager();
                            android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.main_content, fr);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }
                }, changeTime);




            }
        });

        btn_user_back_bs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*

                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(getActivity());
                    // Start the Intent by requesting a result, identified by a request code.
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil
                            .getErrorDialog(e.getConnectionStatusCode(), getActivity(), 0);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(getActivity(), "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }

                */


                Fragment fr;
                fr = new UserHome();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return v;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // BEGIN_INCLUDE(activity_result)
        if (requestCode == PLACE_PICKER_REQUEST) {
            // This result is from the PlacePicker dialog.


            if (resultCode == Activity.RESULT_OK) {



                /* User has picked a place, extract data.
                   Data is extracted from the returned intent by retrieving a Place object from
                   the PlacePicker.
                 */
                final Place place = PlacePicker.getPlace(data, getActivity());

                String toastMsg = String.format("Place: %s\nAddress: %s", place.getName(),place.getAddress());
                //Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();

                /* A Place object contains details about that place, such as its name, address
                and phone number. Extract the name, address, phone number, place ID and place types.
                 */
                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();
                final CharSequence phone = place.getPhoneNumber();
                final String placeId = place.getId();
                String attribution = PlacePicker.getAttributions(data);
                if(attribution == null){
                    attribution = "";
                }


                // Print data to debug log
                Log.d(TAG, "Place selected: " + placeId + " (" + name.toString() + ")");


            } else {
                // User has not selected a place, hide the card.
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        // END_INCLUDE(activity_result)
    }

    /** get city code list from server  **/
    public void setListData()
    {
        System.out.println("inside to get citylist of Pickup Page");

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, null, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {
                Log.d("response for PickupDetails Page connect", response);
                String str_response = response;
                Log.e("PickupDetails Page URL", url);

                //List<String>  responseList = new ArrayList<String>();

                try {

                    JSONObject jsonObj = new JSONObject(str_response);

                    // Getting JSON Array node
                    city = jsonObj.getJSONArray(TAG_CITY);

                    // looping through All City
//                    for (int i = 0; i < city.length(); i++) {
                    for (int i = 0; i < city.length(); i++) {
                        e = city.getJSONObject(i);

                        Pick_Id = e.getString("id");
                        Log.i("Pick_Id is", Pick_Id);

                        responseId.add(Pick_Id);

                        Town_one = e.getString("town");
                        Log.i("Town_one is", Town_one);

                        responseList.add(Town_one);

                        Log.e("Pick City list is", String.valueOf(responseList));

                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, responseList);
                        auto_user_pickup_city.setAdapter(adapter);
                        auto_user_pickup_city.setThreshold(1);

                        Town_Code = e.getString("townCode");
                        Log.i("TownCode from Pickup Page", Town_Code);
                        responseCode.add(Town_Code);

                        // show the values in our logcat
                        Log.e(TAG, "PickId: " + Pick_Id
                                + ", PickAddress: " + Town_one
                                + ", PickTownCode: " + Town_Code);

                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (Pick_Id.equals("1")) {
                        btn_user_next_bs2.setEnabled(true);
                        try {

                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Pick-up Source is!");
//                                    alertDialog.setMessage(jarray.getString("msg");
                            alertDialog.setMessage(e.getString("town"));
//                                    alertDialog.setIcon(R.drawable.welcome);

                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    btn_user_next_bs2.setEnabled(true);

                                    Fragment fr;
                                    fr = new UserHomeDrop();
                                    FragmentManager fm = getFragmentManager();
                                    android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                    fragmentTransaction.replace(R.id.main_content, fr);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

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
                    tost.makeText(getActivity(), "Please check Pickup data fields ", Toast.LENGTH_SHORT).show();
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
                btn_user_next_bs2.setEnabled(true);

            }
        });

    }

}
