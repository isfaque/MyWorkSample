package com.example.admin.hicabsdesign;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.admin.hicabsdesign.CustomAutoComplete.CustomAutoCompleteTextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 10/06/2016.
 */
public class DriverForgetPassword extends AppCompatActivity {

    Button btn_driver_forget_pwd;
    ImageView img_driver_forget_pwd_flag_icon;
    EditText edit_driver_forget_pwd_mobile_no;
    TextInputLayout input_layout_driver_forget_pwd_mobile_no;
    JSONObject jarray;
    AlertDialog alertDialog;
    ProgressBar progressBar;
    CustomAutoCompleteTextView autoComplete;

    String [] flags;
    String [] countries;

    String url = "http://hicabs-system.com/Api/driverforgetpasssword";
    final String TAG = "Login Page Data is";

    public static String selected_driver_forget_pwd_country_code, selected_driver_forget_pwd_mobile_no, driver_forget_pwd_device_id, user_forget_pwd_Name, driver_forget_pwd_result, driver_forget_pwd_ID, driver_forget_pwd_Msg, driver_forget_pwd_Alldata;
    public static String driver_forget_pwd_profile_id, driver_forget_pwd_profile_name, driver_forget_pwd_profile_email, driver_forget_pwd_profile_code, driver_forget_pwd_profile_mobile_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_forget_password);

        img_driver_forget_pwd_flag_icon = (ImageView) findViewById(R.id.img_driver_forget_pwd_flag_icon);

        countries = getResources().getStringArray(R.array.CountryCodes);
        flags = getResources().getStringArray(R.array.CountryNames);
        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<countries.length;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            String flagc = flags[i].toLowerCase();
            int flag_name = getResources().getIdentifier(flagc, "drawable", getPackageName());
            hm.put("txt", countries[i]);
            hm.put("flag", flags[i]);
            hm.put("flag_icon", Integer.toString(flag_name));
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"flag","txt","flag_icon"};

        // Ids of views in listview_layout
        int[] to = {R.id.flag,R.id.txt,R.id.flag_icon};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.custom_autocomplete_layout, from, to);

        // Getting a reference to CustomAutoCompleteTextView of activity_main.xml layout file
        autoComplete = (CustomAutoCompleteTextView) findViewById(R.id.input_driver_forget_pwd_std_code);

        /** Defining an itemclick event listener for the autocompletetextview */
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                /** Each item in the adapter is a HashMap object.
                 *  So this statement creates the currently clicked hashmap object
                 * */
                HashMap<String, String> hm = (HashMap<String, String>) arg0.getAdapter().getItem(position);
                selected_driver_forget_pwd_country_code = hm.get("txt");

                String setflagicon = hm.get("flag").toLowerCase();
                int flag_name = getResources().getIdentifier(setflagicon, "drawable", getPackageName());
                img_driver_forget_pwd_flag_icon.setImageResource(flag_name);

            }
        };

        /** Setting the itemclick event listener */
        autoComplete.setOnItemClickListener(itemClickListener);

        /** Setting the adapter to the listView */
        autoComplete.setAdapter(adapter);
        autoComplete.setThreshold(1);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String simCountry = tm.getSimCountryIso();

        if(!simCountry.trim().equals("")){
            int flag_name = getResources().getIdentifier(simCountry, "drawable", getPackageName());
            img_driver_forget_pwd_flag_icon.setImageResource(flag_name);
            for(int j=0;j<flags.length;j++){
                if(flags[j].toLowerCase().equals(simCountry)){
                    String mycountrycode = countries[j];
                    autoComplete.setText(mycountrycode);
                }
            }
        }


        input_layout_driver_forget_pwd_mobile_no = (TextInputLayout) findViewById(R.id.input_layout_driver_forget_pwd_mobile_no);

        edit_driver_forget_pwd_mobile_no = (EditText) findViewById(R.id.edit_driver_forget_pwd_mobile_no);

        btn_driver_forget_pwd = (Button)findViewById(R.id.btn_driver_forget_pwd);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        edit_driver_forget_pwd_mobile_no.addTextChangedListener(new MyTextWatcher(edit_driver_forget_pwd_mobile_no));
        autoComplete.addTextChangedListener(new MyTextWatcher(autoComplete));


        btn_driver_forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoComplete.getText().toString().trim().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please enter valid country code", Toast.LENGTH_SHORT).show();

                }else if(!validateLogMobile()){

                    Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();

                }else{

                    //submitForm();
                    progressBar.setIndeterminate(true);

                    selected_driver_forget_pwd_country_code = autoComplete.getText().toString();
                    System.out.println(selected_driver_forget_pwd_country_code);
                    Log.e("Login Code", selected_driver_forget_pwd_country_code);

                    selected_driver_forget_pwd_mobile_no = edit_driver_forget_pwd_mobile_no.getText().toString();
                    Log.e("Login Mobile No.", selected_driver_forget_pwd_mobile_no);

                    setLoginData();

                    hideKeyboard();
                    progressBar.setIndeterminate(false);

                }
            }
        });


    }


    /** For validate mobile number **/
    private boolean validateLogMobile() {
        if (edit_driver_forget_pwd_mobile_no.getText().toString().trim().isEmpty()) {
            input_layout_driver_forget_pwd_mobile_no.setError(getString(R.string.err_msg_mobile));
            requestFocus(edit_driver_forget_pwd_mobile_no);
            return false;
        } else {
//            input_layout_login_mobile.setErrorEnabled(false);
            if (edit_driver_forget_pwd_mobile_no.getText().toString().length() < 8) {

                input_layout_driver_forget_pwd_mobile_no.setError(getString(R.string.err_msg_mobilelength));
                return false;
            } else {

                input_layout_driver_forget_pwd_mobile_no.setErrorEnabled(false);
            }
        }

        return true;
    }


    /** for validate flag icon **/
    private boolean validateFlag() {

        int countrycodeposition;
        countrycodeposition = Arrays.asList(countries).indexOf(autoComplete.getText().toString());
        if(countrycodeposition < 0){
            String setflagicon = "flag_icon_002";
            int flag_name = getResources().getIdentifier(setflagicon, "drawable", getPackageName());
            img_driver_forget_pwd_flag_icon.setImageResource(flag_name);

        }
        return true;
    }

    /** request focus function **/
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    /** function for hide keyboard **/
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /** text watcher for login form **/
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edit_driver_forget_pwd_mobile_no:
                    validateLogMobile();
                    break;

                case R.id.input_driver_forget_pwd_std_code:
                    validateFlag();
                    break;
            }
        }
    }


    /** sends login credential in server and get user login information from server **/
    public void setLoginData() {

        RequestParams params = new RequestParams();

        params.put("phone", selected_driver_forget_pwd_mobile_no);
        Log.e("Login Param phone", selected_driver_forget_pwd_mobile_no);

        params.put("code", selected_driver_forget_pwd_country_code);
        Log.e("Login Param Code", selected_driver_forget_pwd_country_code);



        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {

                Log.d("response for connect", response);
                Log.e("Login URL", url);

                try {
                    jarray = new JSONObject(response);

                    driver_forget_pwd_result = jarray.getString("sms");
                    Log.i("result", driver_forget_pwd_result);

                    driver_forget_pwd_Msg = jarray.getString("msg");

                    driver_forget_pwd_profile_code = jarray.getString("code");

                    driver_forget_pwd_profile_mobile_number = jarray.getString("number");


                    driver_forget_pwd_Alldata = jarray.getString("sent");
                    Log.i("Show All Data is", driver_forget_pwd_Alldata);

                    driver_forget_pwd_Msg = jarray.getString("msg");
                    Log.i("Message is", driver_forget_pwd_Msg);

                    Log.e(TAG, "LResult: " + driver_forget_pwd_result
                            + ", LCode: " + driver_forget_pwd_profile_code
                            + ", LNumber: " + driver_forget_pwd_profile_mobile_number
                            + ", LMessage: " + driver_forget_pwd_Msg
                            + ", LDeviceId: " + driver_forget_pwd_device_id);

                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (driver_forget_pwd_result.equals("done")) {
                        progressBar.setIndeterminate(false);
                        edit_driver_forget_pwd_mobile_no.setText("");
                        input_layout_driver_forget_pwd_mobile_no.setErrorEnabled(false);
                        autoComplete.setText("");
                        Toast.makeText(getApplicationContext(), driver_forget_pwd_Msg, Toast.LENGTH_SHORT).show();
                        btn_driver_forget_pwd.setEnabled(true);
                        Intent jk = new Intent(DriverForgetPassword.this, DriverForgetPasswordOTP.class);
                        //jk.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(jk);
                    } else if(driver_forget_pwd_result.equals("failed")){
                        progressBar.setIndeterminate(false);
                        //edit_user_forget_pwd_mobile_no.setText("");
                        //input_layout_user_forget_pwd_mobile_no.setErrorEnabled(false);
                        Toast.makeText(getApplicationContext(), driver_forget_pwd_Msg, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    progressBar.setIndeterminate(false);
                    Toast tost = new Toast(getApplicationContext());
                    tost.makeText(getApplicationContext(), "Please check Login data fields ", Toast.LENGTH_SHORT).show();
                    tost.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                    Intent i = new Intent(getApplicationContext(), SplashActivity.class);
                    startActivity(i);
                    e.printStackTrace();

                }
            }


            @Override
            public void onFailure(Throwable e) {
                progressBar.setIndeterminate(false);
                alertDialog = new AlertDialog.Builder(DriverForgetPassword.this).create();
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("Please check Internet connection or Server is not responding");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.show();
                btn_driver_forget_pwd.setEnabled(true);

            }
        });
    }
}
