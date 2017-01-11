package com.example.admin.hicabsdesign.UserFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.hicabsdesign.CustomListAdapter.CustomListAdapter;
import com.example.admin.hicabsdesign.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 25/06/2016.
 */
public class UserHistoryList extends Fragment {

    String url = "http://hicabs-system.com/Api/getclientbookinghistory";
    ProgressBar progressBar;

    public static String user_booking_id, user_dbooking_id, user_picktown_city, user_droptown_city, user_picktown_address, user_droptown_address, user_booking_date, user_booking_time, user_booking_amount, user_booking_stop, user_booking_status;
    public static String user_pickup_latitude, user_pickup_longitude, user_drop_latitude, user_drop_longitude;

    List<String> bookinglist_bookingid = new ArrayList<String>();
    List<String> bookinglist_dbookingid = new ArrayList<String>();
    List<String> bookinglist_pickupcity = new ArrayList<String>();
    List<String> bookinglist_pickupaddress = new ArrayList<String>();
    List<String> bookinglist_pickuplatitude = new ArrayList<String>();
    List<String> bookinglist_pickuplongitude = new ArrayList<String>();
    List<String> bookinglist_dropcity = new ArrayList<String>();
    List<String> bookinglist_droplatitude = new ArrayList<String>();
    List<String> bookinglist_droplongitude = new ArrayList<String>();
    List<String> bookinglist_dropaddress = new ArrayList<String>();
    List<String> bookinglist_bookingdate = new ArrayList<String>();
    List<String> bookinglist_bookingtime = new ArrayList<String>();
    List<String> bookinglist_bookingprice = new ArrayList<String>();
    List<String> bookinglist_bookingstop = new ArrayList<String>();
    List<String> bookinglist_bookingstatus = new ArrayList<String>();



    ListView list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.test_bookinglist, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);

        setListData();

        //CustomListAdapter adapter=new CustomListAdapter(getActivity(), limits, imgid);
        list=(ListView) v.findViewById(R.id.list);
        //list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem1= bookinglist_bookingid.get(position);

                user_booking_id = bookinglist_bookingid.get(position);
                user_dbooking_id = bookinglist_dbookingid.get(position);
                user_picktown_city = bookinglist_pickupcity.get(position);
                user_picktown_address = bookinglist_pickupaddress.get(position);
                user_pickup_latitude = bookinglist_pickuplatitude.get(position);
                user_pickup_longitude = bookinglist_pickuplongitude.get(position);
                user_droptown_city =bookinglist_dropcity.get(position);
                user_droptown_address = bookinglist_dropaddress.get(position);
                user_drop_latitude = bookinglist_droplatitude.get(position);
                user_drop_longitude = bookinglist_droplongitude.get(position);
                user_booking_date = bookinglist_bookingdate.get(position);
                user_booking_time = bookinglist_bookingtime.get(position);
                user_booking_amount = bookinglist_bookingprice.get(position);
                user_booking_stop = bookinglist_bookingstop.get(position);
                user_booking_status = bookinglist_bookingstatus.get(position);

                Fragment fr;
                fr = new UserHistoryStatus();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });


        return v;
    }

    public void setListData()
    {

        System.out.println("inside get productlist category listview");

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params= new RequestParams();

        params.put("client", UserHistory.user_login_id);
        Log.e("MyBooking Page Id is", UserHistory.user_login_id);

        params.put("status","yes");
        Log.e("MyBooking status is", "yes");

        client.get(url, params, new AsyncHttpResponseHandler()

        {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {
                Log.i("response for Booking ListView", response);
                String str_response = response;
                try {
                    JSONObject jArray = new JSONObject(str_response);
                    {

                        JSONArray groups_array = jArray.getJSONArray("booking");
                        for (int j = 0; j < groups_array.length(); j++) {
                            JSONObject e = groups_array.getJSONObject(j);
                            Log.i("result", "forloop");

                            String bookingid = e.getString("bookingid");
                            String dbookingid = e.getString("dbookingid");
                            String pickupcity = e.getString("pickupcity");
                            String pickupaddress = e.getString("pickupStreet");
                            String pickuplatitude = e.getString("pickuplatitude");
                            String pickuplongitude = e.getString("pickuplongitude");
                            String dropcity = e.getString("dropcity");
                            String dropaddress = e.getString("dropStreet");
                            String droplatitude = e.getString("droplatitude");
                            String droplongitude = e.getString("droplongitude");
                            String jobdate = e.getString("jobdate");
                            String jobtime = e.getString("jobtime");
                            String bookingprice = e.getString("bookingprice");
                            String bookingstop = e.getString("bookingstop");
                            String bookingstatus = e.getString("status");
                            Log.i("result", bookingid);
                            Log.i("result", pickupcity);
                            Log.i("result", dropcity);
                            Log.i("result", jobdate);
                            Log.i("result", jobtime);
                            Log.i("result", bookingprice);

                            bookinglist_bookingid.add(bookingid);
                            bookinglist_dbookingid.add(dbookingid);
                            bookinglist_pickupcity.add(pickupcity);
                            bookinglist_pickupaddress.add(pickupaddress);
                            bookinglist_pickuplatitude.add(pickuplatitude);
                            bookinglist_pickuplongitude.add(pickuplongitude);
                            bookinglist_dropcity.add(dropcity);
                            bookinglist_dropaddress.add(dropaddress);
                            bookinglist_droplatitude.add(droplatitude);
                            bookinglist_droplongitude.add(droplongitude);
                            bookinglist_bookingdate.add(jobdate);
                            bookinglist_bookingtime.add(jobtime);
                            bookinglist_bookingprice.add(bookingprice);
                            bookinglist_bookingstop.add(bookingstop);
                            bookinglist_bookingstatus.add(bookingstatus);

                        }

                        progressBar.setIndeterminate(false);
                        CustomListAdapter adapter=new CustomListAdapter(getActivity(), bookinglist_bookingid, bookinglist_dbookingid, bookinglist_bookingstatus, bookinglist_pickupcity, bookinglist_dropcity, bookinglist_bookingdate, bookinglist_bookingtime, bookinglist_bookingprice);
                        list.setAdapter(adapter);



                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Throwable e) {

                Toast.makeText(getActivity(), "Please check internet connection ", Toast.LENGTH_LONG).show();

            }

        });



    }
}
