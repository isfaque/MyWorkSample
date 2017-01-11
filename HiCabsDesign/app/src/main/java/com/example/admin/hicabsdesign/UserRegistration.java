package com.example.admin.hicabsdesign;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.hicabsdesign.CustomAutoComplete.CustomAutoCompleteTextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by admin on 14/05/2016.
 */
public class UserRegistration extends AppCompatActivity {

    TextView text_hyper;
    ImageView img_user_reg_flag_icon;
    CheckBox terms_and_conditions;
    EditText edit_user_reg_fname, edit_user_reg_lname, edit_user_reg_mobile_no, edit_user_reg_password, edit_user_reg_cpassword, edit_user_reg_email;
    TextInputLayout input_layout_user_reg_fname, input_layout_user_reg_lname, input_layout_user_reg_mobile_no, input_layout_user_reg_password, input_layout_user_reg_cpassword, input_layout_user_reg_email;
    Button btn_user_reg_signup;
    public static String Fname,Lname,Iemail,Ipassword,Iconpassword,Imobile,Icode, Icodename, countryname;
    public static String countrieserror;

    JSONObject jarray;
    AlertDialog alertDialog;
    public static String result, Messages,Code,Mb_Number, R_DeviceId;
    ProgressBar progressBar;
    CustomAutoCompleteTextView autoComplete;

    String url = "http://hicabs-system.com/Webservices/clientregistration";
    final String TAG = "Registration Page Data is";

    String[] countries, flags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);
        img_user_reg_flag_icon = (ImageView) findViewById(R.id.img_user_reg_flag_icon);

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
        autoComplete = (CustomAutoCompleteTextView) findViewById(R.id.auto_user_reg_country_code);

        /** Defining an itemclick event listener for the autocompletetextview */
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                /** Each item in the adapter is a HashMap object.
                 *  So this statement creates the currently clicked hashmap object
                 * */
                HashMap<String, String> hm = (HashMap<String, String>) arg0.getAdapter().getItem(position);

                String setflagicon = hm.get("flag").toLowerCase();
                int flag_name = getResources().getIdentifier(setflagicon, "drawable", getPackageName());
                img_user_reg_flag_icon.setImageResource(flag_name);
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
            img_user_reg_flag_icon.setImageResource(flag_name);
            for(int j=0;j<flags.length;j++){
                if(flags[j].toLowerCase().equals(simCountry)){
                    String mycountrycode = countries[j];
                    autoComplete.setText(mycountrycode);
                }
            }
        }


        input_layout_user_reg_fname = (TextInputLayout)findViewById(R.id.input_layout_user_reg_fname);
        input_layout_user_reg_lname = (TextInputLayout)findViewById(R.id.input_layout_user_reg_lname);
        input_layout_user_reg_mobile_no = (TextInputLayout)findViewById(R.id.input_layout_user_reg_mobile_no);
        input_layout_user_reg_password = (TextInputLayout)findViewById(R.id.input_layout_user_reg_password);
        input_layout_user_reg_cpassword = (TextInputLayout)findViewById(R.id.input_layout_user_reg_cpassword);
        input_layout_user_reg_email = (TextInputLayout)findViewById(R.id.input_layout_user_reg_email);

        edit_user_reg_fname = (EditText)findViewById(R.id.edit_user_reg_fname);
        edit_user_reg_lname = (EditText)findViewById(R.id.edit_user_reg_lname);
        edit_user_reg_mobile_no = (EditText)findViewById(R.id.edit_user_reg_mobile_no);
        edit_user_reg_password = (EditText)findViewById(R.id.edit_user_reg_password);
        edit_user_reg_cpassword = (EditText)findViewById(R.id.edit_user_reg_cpassword);
        edit_user_reg_email = (EditText)findViewById(R.id.edit_user_reg_email);

        terms_and_conditions = (CheckBox)findViewById(R.id.checkbox_user_reg_tc);

        btn_user_reg_signup = (Button)findViewById(R.id.btn_user_reg_signup);
        progressBar =(ProgressBar)findViewById(R.id.progressBar);



        edit_user_reg_fname.addTextChangedListener(new MyTextWatcher(edit_user_reg_fname));
        edit_user_reg_lname.addTextChangedListener(new MyTextWatcher(edit_user_reg_lname));
        edit_user_reg_email.addTextChangedListener(new MyTextWatcher(edit_user_reg_email));
        edit_user_reg_password.addTextChangedListener(new MyTextWatcher(edit_user_reg_password));
        edit_user_reg_cpassword.addTextChangedListener(new MyTextWatcher(edit_user_reg_cpassword));
        edit_user_reg_mobile_no.addTextChangedListener(new MyTextWatcher(edit_user_reg_mobile_no));

        autoComplete.addTextChangedListener(new MyTextWatcher(autoComplete));

        text_hyper = (TextView) findViewById(R.id.txt_hyper_link);
        text_hyper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openDialog();
            }
        });

        btn_user_reg_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Icode = autoComplete.getText().toString().trim();

                //Toast.makeText(getApplicationContext(), Icode, Toast.LENGTH_SHORT).show();

                for(int j=0;j<countries.length;j++){
                    if(countries[j].equals(Icode)){
                        countryname = flags[j].toLowerCase();
                        //Toast.makeText(getApplicationContext(), "countryname: "+countryname, Toast.LENGTH_SHORT).show();
                        countrieserror = "false";
                        break;
                    }else{
                        countrieserror = "error";
                    }
                }


                if(!validateFirstName()) {

                    Toast.makeText(getApplicationContext(), "Please enter first name", Toast.LENGTH_SHORT).show();

                }else if(!validateLastName()) {

                    Toast.makeText(getApplicationContext(), "Please enter last name", Toast.LENGTH_SHORT).show();

                }else if(autoComplete.getText().toString().trim().isEmpty() || countrieserror.trim().equals("error")) {

                    Toast.makeText(getApplicationContext(), "Please enter valid country code", Toast.LENGTH_SHORT).show();

                }else if(!validateMobile()) {

                    Toast.makeText(getApplicationContext(), "Please enter valid mobile no", Toast.LENGTH_SHORT).show();

                }else if(!validatePassword()) {

                    Toast.makeText(getApplicationContext(), "Please enter valid password", Toast.LENGTH_SHORT).show();

                }else if(!validateConfirmPassword()) {

                    Toast.makeText(getApplicationContext(), "Confirm password does not match", Toast.LENGTH_SHORT).show();

                }else if(!terms_and_conditions.isChecked()) {

                    Toast.makeText(getApplicationContext(), "Please accept Terms and Conditions", Toast.LENGTH_SHORT).show();

                }else{

                    //submitForm();
                    progressBar.setIndeterminate(true);

                    Fname = edit_user_reg_fname.getText().toString().trim();
                    Lname = edit_user_reg_lname.getText().toString().trim();
                    Icode = autoComplete.getText().toString().trim();
                    Icodename = countryname;
                    Iemail = edit_user_reg_email.getText().toString().trim();
                    Ipassword = edit_user_reg_password.getText().toString().trim();
                    Iconpassword = edit_user_reg_cpassword.getText().toString().trim();
                    Imobile = edit_user_reg_mobile_no.getText().toString().trim();

                    Log.e("Registration Icode", Icode);
                    Log.e("Registration fisrt name", Fname);

                    hideKeyboard();

                    R_DeviceId = SplashActivity.deviceId;

                    RequestParams params = new RequestParams();

                    params.put("code", Icode);
                    System.out.print(Icode);
                    Log.e("Registration Icode", Icode);

                    params.put("phone", Imobile);
                    System.out.print(Imobile);
                    Log.e("Registration phone", Imobile);

                    params.put("fname", Fname);
                    System.out.print(Fname);
                    Log.e("Registration fisrt name", Fname);

                    params.put("lname", Lname);
                    System.out.print(Lname);
                    Log.e("Registration last name", Lname);

                    params.put("password", Ipassword);
                    System.out.print(Ipassword);

                    params.put("device_id", R_DeviceId);
                    System.out.println(R_DeviceId);
                    Log.e("Registration Device Id:", R_DeviceId);

                    params.put("email", Iemail);
                    System.out.println(Iemail);
                    Log.e("Registration Email Id:", Iemail);

                    params.put("Icodename", Icodename);
                    System.out.println(Icodename);
                    Log.e("Icodename is:", Icodename);

                    AsyncHttpClient client = new AsyncHttpClient();

                    client.post(url, params, new AsyncHttpResponseHandler() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onSuccess(String response) {
                            Log.d("response for Registration connect", response);
                            Log.e("Registration URL", url);
                            try {
                                jarray = new JSONObject(response);

                                result = jarray.getString("registration");
                                Log.i("result", result);

                                Messages = jarray.getString("msg");
                                Log.i("Message is", Messages);

                                Code = jarray.getString("code");
                                Log.i("Code from Registration", Code);

                                Mb_Number = jarray.getString("number");
                                Log.i("Mobile No. from Registration", Mb_Number);

                                Log.e(TAG, "RResult: " + result
                                        + ", RId: " + Messages
                                        + ", RCode: " + Code
                                        + ", RNumber: " + Mb_Number
                                        + ", RDeviceId: " + R_DeviceId);


                            } catch (Exception e) {
                                // TODO: handle exception
                                //Log.i("Exception",e.getMessage());
                            }

                            try {
                                if (result.equals("failed")) {
                                    progressBar.setIndeterminate(false);
                                    btn_user_reg_signup.setEnabled(true);
                                    try {

                                        AlertDialog alertDialog = new AlertDialog.Builder(UserRegistration.this).create();
                                        alertDialog.setTitle("Registration Failed!");
//                                    alertDialog.setMessage(jarray.getString("msg");
                                        alertDialog.setMessage(jarray.getString("msg"));
//                                    alertDialog.setIcon(R.drawable.welcome);

                                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                btn_user_reg_signup.setEnabled(true);
//                                            Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(UserRegistration.this, UserLogin.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                startActivity(i);
                                            }
                                        });

                                        alertDialog.show();
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block

                                        e.printStackTrace();
                                    }
                                    //passwordedit.requestFocus();
                                } else if (result.equals("done")) {
                                    progressBar.setIndeterminate(false);
                                    AlertDialog alertDialog = new AlertDialog.Builder(UserRegistration.this).create();
                                    alertDialog.setTitle("Registration Success!");
//                                    alertDialog.setMessage(jarray.getString("msg");
                                    alertDialog.setMessage(jarray.getString("msg"));
//                                    alertDialog.setIcon(R.drawable.welcome);

                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            btn_user_reg_signup.setEnabled(true);
                                            //Intent i = new Intent(UserRegistration.this, UserLogin.class);
                                            //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                            //startActivity(i);
                                            Intent i = new Intent(UserRegistration.this, UserRegistrationVerification.class);
                                            startActivity(i);
                                            Toast.makeText(getApplicationContext(), "Thank You For Registration!", Toast.LENGTH_SHORT).show();
                                            finish();
//                                            Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    alertDialog.show();


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
                            alertDialog = new AlertDialog.Builder(UserRegistration.this).create();
                            alertDialog.setTitle("Error:");
                            alertDialog.setMessage("Number is not valid or Service not available for this number");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                            btn_user_reg_signup.setEnabled(true);

                        }
                    });

                }


            }
        });


    }

    /** function for hide keyboard **/
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /** function for checking user registration form validation **/
    private void submitForm() {
        if (!validateFirstName()) {
            return;
        }
        if (!validateLastName()) {
            return;
        }
        if (!validateMobile()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        if (!validateConfirmPassword()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
    }

    /** function for open terms and conditions dialog **/
    public void openDialog() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setTitle("Terms and Conditions");
        dialog.setContentView(R.layout.terms_and_condition);
        // Getting a reference to Close button, and close the popup when clicked.
        Button cancel = (Button) dialog.findViewById(R.id.dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /** request focution function **/
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    /** function check mail valid or not **/
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /** function for first name validation in registration form **/
    private boolean validateFirstName() {
        if (edit_user_reg_fname.getText().toString().trim().isEmpty()) {
            input_layout_user_reg_fname.setError(getString(R.string.err_msg_firstname));
            requestFocus(edit_user_reg_fname);
            return false;
        } else {
            input_layout_user_reg_fname.setErrorEnabled(false);
        }
        return true;
    }

    /** function for last name validation in registration form **/
    private boolean validateLastName() {
        if (edit_user_reg_lname.getText().toString().trim().isEmpty()) {

            input_layout_user_reg_lname.setError(getString(R.string.err_msg_lastname));
            requestFocus(edit_user_reg_lname);
            return false;

        } else {
            input_layout_user_reg_lname.setErrorEnabled(false);
        }

        return true;
    }

    /** function for mobile number validation in registration form **/
    private boolean validateMobile() {
        if (edit_user_reg_mobile_no.getText().toString().trim().isEmpty()) {
            input_layout_user_reg_mobile_no.setError(getString(R.string.err_msg_mobile));
            requestFocus(edit_user_reg_mobile_no);
            return false;
        } else {
            if (edit_user_reg_mobile_no.getText().toString().length() < 8) {

                input_layout_user_reg_mobile_no.setError(getString(R.string.err_msg_mobilelength));
                return false;
            } else {

                input_layout_user_reg_mobile_no.setErrorEnabled(false);
            }
        }
        return true;
    }

    /** function for password validation in registration form **/
    private boolean validatePassword() {
        if (edit_user_reg_password.getText().toString().trim().isEmpty()) {
            input_layout_user_reg_password.setError(getString(R.string.err_msg_password));
            requestFocus(edit_user_reg_password);
            return false;
        } else {

            if (edit_user_reg_password.getText().toString().length() < 6) {

                input_layout_user_reg_password.setError(getString(R.string.err_msg_passwordlength));
                return false;
            } else {

                input_layout_user_reg_password.setErrorEnabled(false);
            }
        }

        return true;
    }

    /** function for confirm password validation in registration form **/
    private boolean validateConfirmPassword() {
        if (edit_user_reg_cpassword.getText().toString().trim().isEmpty()) {
            input_layout_user_reg_cpassword.setError(getString(R.string.err_msg_confirm_password));
            requestFocus(edit_user_reg_cpassword);
            return false;
        }
        else {

            if (edit_user_reg_password.getText().toString().equals(edit_user_reg_cpassword.getText().toString())) {

                input_layout_user_reg_cpassword.setErrorEnabled(false);

            } else {
                input_layout_user_reg_cpassword.setError("Password does not match");
                return false;
            }
        }
        return true;
    }

    /** function for email validation in registration mail **/
    private boolean validateEmail() {
        String email = edit_user_reg_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            input_layout_user_reg_email.setError(getString(R.string.err_msg_email));
            requestFocus(edit_user_reg_email);
            return false;
        } else {
            input_layout_user_reg_email.setErrorEnabled(false);
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
            img_user_reg_flag_icon.setImageResource(flag_name);

        }
        return true;
    }


    /** text watcher for user registration form **/
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
                case R.id.edit_user_reg_fname:
                    validateFirstName();
                    break;
                case R.id.edit_user_reg_lname:
                    validateLastName();
                    break;
                case R.id.edit_user_reg_mobile_no:
                    validateMobile();
                    break;
                case R.id.edit_user_reg_password:
                    validatePassword();
                    break;
                case R.id.edit_user_reg_cpassword:
                    validateConfirmPassword();
                    break;
                case R.id.edit_user_reg_email:
                    validateEmail();
                    break;
                case R.id.auto_user_reg_country_code:
                    validateFlag();
                    break;
            }
        }
    }

}
