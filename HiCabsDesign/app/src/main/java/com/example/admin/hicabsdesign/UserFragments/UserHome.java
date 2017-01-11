package com.example.admin.hicabsdesign.UserFragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.UserMainActivity;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 12/05/2016.
 */
public class UserHome extends Fragment implements View.OnClickListener {

    Button btn_user_booking_date, btn_user_booking_time, btn_user_next_bs1;
    EditText edit_user_booking_date, edit_user_booking_time, edit_user_passenger_no;
    Spinner spinner_user_passenger_no;

    private int mYear, mMonth, mDay, mHour, mMinute;
    public static String selected_user_booking_date, selected_user_booking_time, selected_user_passenger_no;
    public static int selected_user_passenger_position;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home_user, container, false);

        // retain this fragment
        setRetainInstance(true);

        //btn_user_booking_date = (Button) v.findViewById(R.id.btn_user_booking_date);
        //btn_user_booking_time = (Button) v.findViewById(R.id.btn_user_booking_time);
        edit_user_booking_date = (EditText) v.findViewById(R.id.edit_user_booking_date);
        edit_user_booking_time = (EditText) v.findViewById(R.id.edit_user_booking_time);
        btn_user_next_bs1 = (Button) v.findViewById(R.id.btn_user_next_bs1);



        edit_user_booking_date.setOnClickListener(this);
        edit_user_booking_time.setOnClickListener(this);

        spinner_user_passenger_no = (Spinner) v.findViewById(R.id.spinner_user_passenger_no);
        spinner_user_passenger_no.setOnItemSelectedListener(new ItemSelectedListener());

        if(selected_user_booking_date != null || selected_user_booking_date != "")
        {
            edit_user_booking_date.setText(selected_user_booking_date);
            edit_user_booking_time.setText(selected_user_booking_time);
            spinner_user_passenger_no.setSelection (selected_user_passenger_position);
        }

        btn_user_next_bs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //((UserMainActivity) getActivity()).sendNotification("hicabs assigned you a new job");


                selected_user_booking_date = edit_user_booking_date.getText().toString();
                selected_user_booking_time = edit_user_booking_time.getText().toString();

                selected_user_passenger_no = spinner_user_passenger_no.getSelectedItem().toString();
                selected_user_passenger_position = spinner_user_passenger_no.getSelectedItemPosition();

                if(selected_user_booking_date.equals("")) {
                    Toast.makeText(getActivity(), "Please enter booking date", Toast.LENGTH_SHORT).show();
                }else if(selected_user_booking_time.equals("")){
                    Toast.makeText(getActivity(), "Please enter booking time", Toast.LENGTH_SHORT).show();
                }else if(selected_user_passenger_no.equals("")){
                    Toast.makeText(getActivity(), "Please enter passenger number", Toast.LENGTH_SHORT).show();
                }else {

                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String currentdatetime = df.format(Calendar.getInstance().getTime());
                    String mybookingdatetime = selected_user_booking_date+" "+selected_user_booking_time+":00";
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = df.parse(currentdatetime);
                        date2 = df.parse(mybookingdatetime);
                        if (date1.compareTo(date2)<0)
                        {

                            edit_user_booking_date.setText("");
                            edit_user_booking_time.setText("");

                            spinner_user_passenger_no.setAdapter(null);

                            Log.d("MyHome", "BUTTON PRESSED");

                            Fragment fr;
                            fr = new UserHomePickup();
                            FragmentManager fm = getFragmentManager();
                            android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.main_content, fr);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }else{

                            Toast.makeText(getActivity(), "Please enter future time", Toast.LENGTH_SHORT).show();

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }

            }
        });

        return v;
    }

    /** onclick function for user booking date and booking time button **/
    @Override
    public void onClick(View v) {

        if (v == edit_user_booking_date) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            int year = c.get(Calendar.YEAR);
            String currentYear = String.valueOf(year);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    edit_user_booking_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                }
            }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

            if(currentYear.toString().equals("2016")){
                datePickerDialog.getDatePicker().setMaxDate(1483142400000L);
            }else if(currentYear.toString().equals("2017")){
                datePickerDialog.getDatePicker().setMaxDate(1514699352000L);
            }else if(currentYear.toString().equals("2018")){
                datePickerDialog.getDatePicker().setMaxDate(1546256304000L);
            }else if(currentYear.toString().equals("2019")){
                datePickerDialog.getDatePicker().setMaxDate(1577813256000L);
            }

            datePickerDialog.show();

        }
        if (v == edit_user_booking_time) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    //edit_user_booking_time.setText(hourOfDay + ":" + minute);
                    edit_user_booking_time.setText(String.format("%02d:%02d", hourOfDay, minute));
                }
            }, mHour, mMinute, true);
            timePickerDialog.show();
        }
    }

    /** Item selected listener for user passenger no spinner  **/
    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            String firstItem = (String) spinner_user_passenger_no.getSelectedItem();

            if (firstItem.equals((spinner_user_passenger_no.getSelectedItem()))) {
                // ToDo when first item is selected

            } else {
                Toast.makeText(parent.getContext(),
                        "Number Of Passenger is Selected : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                // Todo when item is selected by the user}}

            }

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }



}
