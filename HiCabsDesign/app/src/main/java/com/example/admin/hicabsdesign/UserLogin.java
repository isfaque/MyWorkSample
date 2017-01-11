package com.example.admin.hicabsdesign;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.hicabsdesign.CustomAutoComplete.CustomAutoCompleteTextView;
import com.example.admin.hicabsdesign.GCMManager.GCMRegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 11/05/2016.
 */
public class UserLogin extends AppCompatActivity {

    Button btn_user_login, btn_user_signup;
    TextView txt_user_login_forget_password;
    ImageView img_user_login_flag_icon;
    EditText edit_user_mobile_no, edit_user_password;
    TextInputLayout input_layout_user_mobile_no, input_layout_user_password;
    JSONObject jarray;
    AlertDialog alertDialog;
    ProgressBar progressBar;
    CustomAutoCompleteTextView autoComplete;

    String [] flags;
    String [] countries;

    private static final int PERMISSION_REQUEST_CODE = 1;
    private View view;



    //String url = "http://dev.hicabs-system.com/Webservices/login_user";
    String url = "http://hicabs-system.com/Api/login";
    final String TAG = "Login Page Data is";

    public static String selected_user_country_code, selected_user_mobile_no, selected_user_password, device_id, Name, Email, result, ID, Msg, Alldata;
    public static String profile_id, profile_name, profile_email, profile_code, profile_mobile_number, profile_payment_mode, user_token_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        if (!checkPermission()) {

            requestPermission();

        }

        img_user_login_flag_icon = (ImageView) findViewById(R.id.img_user_login_flag_icon);

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
        autoComplete = (CustomAutoCompleteTextView) findViewById(R.id.input_user_std_code);

        /** Defining an itemclick event listener for the autocompletetextview */
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                /** Each item in the adapter is a HashMap object.
                 *  So this statement creates the currently clicked hashmap object
                 * */
                HashMap<String, String> hm = (HashMap<String, String>) arg0.getAdapter().getItem(position);
                selected_user_country_code = hm.get("txt");

                String setflagicon = hm.get("flag").toLowerCase();
                int flag_name = getResources().getIdentifier(setflagicon, "drawable", getPackageName());
                img_user_login_flag_icon.setImageResource(flag_name);

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
            img_user_login_flag_icon.setImageResource(flag_name);
            for(int j=0;j<flags.length;j++){
                if(flags[j].toLowerCase().equals(simCountry)){
                    String mycountrycode = countries[j];
                    autoComplete.setText(mycountrycode);
                }
            }
        }

        input_layout_user_mobile_no = (TextInputLayout) findViewById(R.id.input_layout_user_mobile_no);
        input_layout_user_password = (TextInputLayout) findViewById(R.id.input_layout_user_password);

        edit_user_mobile_no = (EditText) findViewById(R.id.edit_user_mobile_no);
        edit_user_password = (EditText) findViewById(R.id.edit_user_password);

        txt_user_login_forget_password = (TextView) findViewById(R.id.txt_user_login_forget_password);

        btn_user_login = (Button)findViewById(R.id.btn_user_login);
        btn_user_signup = (Button)findViewById(R.id.btn_user_signup);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);



        edit_user_mobile_no.addTextChangedListener(new MyTextWatcher(edit_user_mobile_no));
        edit_user_password.addTextChangedListener(new MyTextWatcher(edit_user_password));
        autoComplete.addTextChangedListener(new MyTextWatcher(autoComplete));

        btn_user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            if(autoComplete.getText().toString().trim().isEmpty()) {

                Toast.makeText(getApplicationContext(), "Please enter valid country code", Toast.LENGTH_SHORT).show();

            }else if(!validateLogMobile()){

                Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();

            }else if(!validatePassword()){

                Toast.makeText(getApplicationContext(), "Please enter valid password", Toast.LENGTH_SHORT).show();

            }else{

                //submitForm();
                progressBar.setIndeterminate(true);

                selected_user_country_code = autoComplete.getText().toString();
                System.out.println(selected_user_country_code);
                Log.e("Login Code", selected_user_country_code);

                selected_user_mobile_no = edit_user_mobile_no.getText().toString();
                Log.e("Login Mobile No.", selected_user_mobile_no);

                selected_user_password = edit_user_password.getText().toString();
                Log.e("Login Password", selected_user_password);



                setLoginData();

                hideKeyboard();

            }

            }
        });

        btn_user_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserLogin.this, UserRegistration.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        txt_user_login_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLogin.this, UserForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    /** sends login credential in server and get user login information from server **/
    public void setLoginData() {

        device_id = SplashActivity.deviceId;

        RequestParams params = new RequestParams();

        params.put("phone", selected_user_mobile_no);
        Log.e("Login Param phone", selected_user_mobile_no);


        params.put("password", selected_user_password);
        Log.e("Login Param Password", selected_user_password);

        params.put("code", selected_user_country_code);
        Log.e("Login Param Code", selected_user_country_code);

        params.put("device_id", device_id);
        Log.e("Login param Device Id", device_id);

        //params.put("token_id", user_token_id);
        //Log.e("Login token id", user_token_id);


        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {

                Log.d("response for connect", response);
                Log.e("Login URL", url);

                try {
                    jarray = new JSONObject(response);

                    result = jarray.getString("login");
                    Log.i("result", result);

                    ID = jarray.getString("id");
                    profile_id = ID;
                    Log.e("ID from login", ID);

                    Email = jarray.getString("email");
                    profile_email = Email;
                    Log.i("Email is", Email);

                    Name = jarray.getString("name");
                    profile_name = Name;
                    Log.i("Name is", Name);

                    profile_code = jarray.getString("code");

                    profile_mobile_number = jarray.getString("number");

                    profile_payment_mode = jarray.getString("paymentmode");


                    Alldata = jarray.getString("sent");
                    Log.i("Show All Data is", Alldata);

                    Msg = jarray.getString("msg");
                    Log.i("Message is", Msg);

                    Log.e(TAG, "LResult: " + result
                            + ", LId: " + ID
                            + ", LName: " + Name
                            + ", LMessage: " + Msg
                            + ", LDeviceId: " + device_id);

                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (result.equals("done")) {
                        SplashActivity.editor.putString("loginTest", "true");
                        SplashActivity.editor.putString("loginType", "user");
                        SplashActivity.editor.putString("login_id", profile_id);
                        SplashActivity.editor.putString("login_name", profile_name);
                        SplashActivity.editor.putString("login_email", profile_email);
                        SplashActivity.editor.putString("login_country_code", profile_code);
                        SplashActivity.editor.putString("login_mobile_number", profile_mobile_number);
                        SplashActivity.editor.putString("login_payment_mode", profile_payment_mode);
                        SplashActivity.editor.putString("login_device_id", SplashActivity.deviceId);
                        SplashActivity.editor.commit();

                        progressBar.setIndeterminate(false);
                        edit_user_mobile_no.setText("");
                        input_layout_user_mobile_no.setErrorEnabled(false);
                        edit_user_password.setText("");
                        input_layout_user_password.setErrorEnabled(false);
                        autoComplete.setText("");
                        Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                        btn_user_login.setEnabled(true);
                        Intent jk = new Intent(UserLogin.this, UserMainActivity.class);
                        jk.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        jk.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(jk);
                    } else if (result.equals("wrong password")) {
                        progressBar.setIndeterminate(false);
                        edit_user_password.setText("");
                        input_layout_user_password.setErrorEnabled(false);
                        Toast.makeText(getApplicationContext(), "Entered Wrong Password", Toast.LENGTH_SHORT).show();
                    } else if(result.equals("mobile not registerd")){
                        progressBar.setIndeterminate(false);
                        edit_user_mobile_no.setText("");
                        input_layout_user_mobile_no.setErrorEnabled(false);
                        Toast.makeText(getApplicationContext(), "Your Mobile number not registered", Toast.LENGTH_LONG).show();
                    } else if(result.equals("not verified")){
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getApplicationContext(), "Mobile number is not verified", Toast.LENGTH_LONG).show();
                        Intent jk = new Intent(UserLogin.this, UserLoginVerification.class);
                        startActivity(jk);
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    progressBar.setIndeterminate(false);
                    Toast tost = new Toast(getApplicationContext());
                    tost.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                    tost.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                    //Intent i = new Intent(getApplicationContext(), SplashActivity.class);
                    //startActivity(i);
                    e.printStackTrace();

                }
            }


            @Override
            public void onFailure(Throwable e) {
                progressBar.setIndeterminate(false);
                alertDialog = new AlertDialog.Builder(UserLogin.this).create();
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("Please check Internet connection or Server is not responding");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.show();
                btn_user_login.setEnabled(true);

            }
        });
    }

    /** For validate mobile number **/
    private boolean validateLogMobile() {
        if (edit_user_mobile_no.getText().toString().trim().isEmpty()) {
            input_layout_user_mobile_no.setError(getString(R.string.err_msg_mobile));
            requestFocus(edit_user_mobile_no);
            return false;
        } else {
//            input_layout_login_mobile.setErrorEnabled(false);
            if (edit_user_mobile_no.getText().toString().length() < 8) {

                input_layout_user_mobile_no.setError(getString(R.string.err_msg_mobilelength));
                return false;
            } else {

                input_layout_user_mobile_no.setErrorEnabled(false);
            }
        }

        return true;
    }

    /** for validate password **/
    private boolean validatePassword() {
        if (edit_user_password.getText().toString().trim().isEmpty()) {
            input_layout_user_password.setError(getString(R.string.err_msg_password));
            requestFocus(edit_user_password);
            return false;
        } else {
            if (edit_user_password.getText().toString().length() < 6) {

                input_layout_user_password.setError(getString(R.string.err_msg_passwordlength));
                return false;
            } else {

                input_layout_user_password.setErrorEnabled(false);
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
                img_user_login_flag_icon.setImageResource(flag_name);

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
                case R.id.edit_user_mobile_no:
                    validateLogMobile();
                    break;

                case R.id.edit_user_password:
                    validatePassword();
                    break;

                case R.id.input_user_std_code:
                    validateFlag();
                    break;
            }
        }
    }

    /** set error message if mobile and password not validating **/
    private void submitForm() {
        if (!validateLogMobile()) {
            if (edit_user_mobile_no.getText().toString().equals(edit_user_mobile_no)) {
                input_layout_user_mobile_no.setErrorEnabled(false);
            }
            return;
        }

        if (!validatePassword()) {
        }

        if (edit_user_password.getText().toString().equals(edit_user_password)) {
            input_layout_user_password.setErrorEnabled(false);
        }
        return;
    }


    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private void requestPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(UserLogin.this, Manifest.permission.CALL_PHONE)){

            Toast.makeText(getApplicationContext(),"Calling permission allows us to call. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(UserLogin.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Snackbar.make(view,"Permission Granted, Now you can Call.",Snackbar.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"Permission Granted, Now you can Call.",Toast.LENGTH_LONG).show();

                } else {

                    //Snackbar.make(view,"Permission Denied, You cannot Call.",Snackbar.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"Permission Denied, You cannot Call.",Toast.LENGTH_LONG).show();

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
