package com.example.admin.hicabsdesign;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by admin on 10/06/2016.
 */
public class DriverForgetPasswordOTP extends AppCompatActivity {

    EditText edit_driver_forget_pwd_otp, edit_driver_forget_pwd_password, edit_driver_forget_pwd_cpassword;
    TextInputLayout input_layout_driver_forget_pwd_otp, input_layout_driver_forget_pwd_password, input_layout_driver_forget_pwd_cpassword;
    ProgressBar progressBar;
    Button btn_driver_forget_pwd_change_password, btn_driver_forget_pwd_send_otp;

    JSONObject jarray;
    AlertDialog alertDialog;

    String url = "http://hicabs-system.com/Api/driverallowresetpassword";
    String urlPassword = "http://hicabs-system.com/Api/driverpasswordreset";
    String urlOTP = "http://hicabs-system.com/Api/driverforgetpasssword";
    final String TAG = "Login Page Data is";

    public static String selected_driver_forget_pwd_otp, selected_driver_forget_pwd_password, selected_driver_forget_pwd_cpassword, driver_forget_pwd_otp_result1, driver_forget_pwd_otp_Msg, driver_forget_pwd_otp_profile_code, driver_forget_pwd_otp_profile_mobile_number, user_forget_pwd_otp_Alldata;

    public static String driver_forget_pwd_reset_result, driver_forget_pwd_reset_Msg, driver_forget_pwd_reset_profile_code, driver_forget_pwd_reset_profile_mobile_number, driver_forget_pwd_reset_Alldata;

    public static String driver_send_otp_pwd_result,driver_send_otp_pwd_Msg, driver_send_otp_pwd_profile_code, driver_send_otp_pwd_profile_mobile_number, driver_send_otp_pwd_Alldata, driver_send_otp_pwd_device_id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_forget_password_otp);

        edit_driver_forget_pwd_otp = (EditText)findViewById(R.id.edit_driver_forget_pwd_otp);
        edit_driver_forget_pwd_password = (EditText)findViewById(R.id.edit_driver_forget_pwd_password);
        edit_driver_forget_pwd_cpassword = (EditText)findViewById(R.id.edit_driver_forget_pwd_cpassword);

        input_layout_driver_forget_pwd_otp = (TextInputLayout)findViewById(R.id.input_layout_driver_forget_pwd_otp);
        input_layout_driver_forget_pwd_password = (TextInputLayout)findViewById(R.id.input_layout_driver_forget_pwd_password);
        input_layout_driver_forget_pwd_cpassword = (TextInputLayout)findViewById(R.id.input_layout_driver_forget_pwd_cpassword);



        btn_driver_forget_pwd_change_password = (Button)findViewById(R.id.btn_driver_forget_pwd_change_password);
        btn_driver_forget_pwd_send_otp = (Button)findViewById(R.id.btn_driver_forget_pwd_send_otp);

        progressBar =(ProgressBar)findViewById(R.id.progressBar);

        edit_driver_forget_pwd_password.addTextChangedListener(new MyTextWatcher(edit_driver_forget_pwd_password));
        edit_driver_forget_pwd_cpassword.addTextChangedListener(new MyTextWatcher(edit_driver_forget_pwd_cpassword));

        btn_driver_forget_pwd_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validatePassword()) {

                    Toast.makeText(getApplicationContext(), "Please enter valid password", Toast.LENGTH_SHORT).show();

                }else if(!validateConfirmPassword()) {

                    Toast.makeText(getApplicationContext(), "Confirm password does not match", Toast.LENGTH_SHORT).show();

                }else {

                    progressBar.setIndeterminate(true);
                    selected_driver_forget_pwd_otp = edit_driver_forget_pwd_otp.getText().toString();
                    selected_driver_forget_pwd_password = edit_driver_forget_pwd_password.getText().toString();
                    selected_driver_forget_pwd_cpassword = edit_driver_forget_pwd_cpassword.getText().toString();
                    setLoginData();
                }
            }
        });

        btn_driver_forget_pwd_send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendOTP();

            }
        });
    }


    /** request focution function **/
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    /** function for password validation in registration form **/
    private boolean validatePassword() {
        if (edit_driver_forget_pwd_password.getText().toString().trim().isEmpty()) {
            input_layout_driver_forget_pwd_password.setError(getString(R.string.err_msg_password));
            requestFocus(edit_driver_forget_pwd_password);
            return false;
        } else {

            if (edit_driver_forget_pwd_password.getText().toString().length() < 6) {

                input_layout_driver_forget_pwd_password.setError(getString(R.string.err_msg_passwordlength));
                return false;
            } else {

                input_layout_driver_forget_pwd_password.setErrorEnabled(false);
            }
        }

        return true;
    }

    /** function for confirm password validation in registration form **/
    private boolean validateConfirmPassword() {
        if (edit_driver_forget_pwd_cpassword.getText().toString().trim().isEmpty()) {
            input_layout_driver_forget_pwd_cpassword.setError(getString(R.string.err_msg_confirm_password));
            requestFocus(edit_driver_forget_pwd_cpassword);
            return false;
        }
        else {

            if (edit_driver_forget_pwd_password.getText().toString().equals(edit_driver_forget_pwd_cpassword.getText().toString())) {

                input_layout_driver_forget_pwd_cpassword.setErrorEnabled(false);

            } else {
                input_layout_driver_forget_pwd_cpassword.setError("Password does not match");
                return false;
            }
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
                case R.id.edit_driver_forget_pwd_password:
                    validatePassword();
                    break;
                case R.id.edit_driver_forget_pwd_cpassword:
                    validateConfirmPassword();
                    break;
            }
        }
    }


    /** sends login credential in server and get user login information from server **/
    public void setLoginData() {

        RequestParams params = new RequestParams();

        params.put("otp", selected_driver_forget_pwd_otp);
        Log.e("Login Param OTP", selected_driver_forget_pwd_otp);

        params.put("phone", DriverForgetPassword.driver_forget_pwd_profile_mobile_number);
        Log.e("Login Param phone", DriverForgetPassword.driver_forget_pwd_profile_mobile_number);

        params.put("code", DriverForgetPassword.driver_forget_pwd_profile_code);
        Log.e("Login Param Code", DriverForgetPassword.driver_forget_pwd_profile_code);



        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {

                Log.d("response for connect", response);
                Log.e("Login URL", url);

                try {
                    jarray = new JSONObject(response);

                    driver_forget_pwd_otp_result1 = jarray.getString("otp");
                    Log.i("result", driver_forget_pwd_otp_result1);
                    //Log.e("result", user_forget_pwd_otp_result1);

                    driver_forget_pwd_otp_Msg = jarray.getString("msg");

                    driver_forget_pwd_otp_profile_code = jarray.getString("code");

                    driver_forget_pwd_otp_profile_mobile_number = jarray.getString("number");


                    //user_forget_pwd_otp_Alldata = jarray.getString("sent");
                    //Log.i("Show All Data is", user_forget_pwd_otp_Alldata);


                    Log.e(TAG, "LResult: " + driver_forget_pwd_otp_result1
                            + ", LCode: " + driver_forget_pwd_otp_profile_code
                            + ", LNumber: " + driver_forget_pwd_otp_profile_mobile_number
                            + ", LMessage: " + driver_forget_pwd_otp_Msg);

                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (driver_forget_pwd_otp_result1.equals("match")) {
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getApplicationContext(), driver_forget_pwd_otp_Msg, Toast.LENGTH_SHORT).show();
                        setPassword();
                        btn_driver_forget_pwd_change_password.setEnabled(true);
                    } else if(driver_forget_pwd_otp_result1.equals("expired")){
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getApplicationContext(), driver_forget_pwd_otp_Msg, Toast.LENGTH_LONG).show();
                    } else{
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getApplicationContext(), driver_forget_pwd_otp_Msg, Toast.LENGTH_LONG).show();
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
                alertDialog = new AlertDialog.Builder(DriverForgetPasswordOTP.this).create();
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("Please check Internet connection or Server is not responding");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.show();
                btn_driver_forget_pwd_change_password.setEnabled(true);

            }
        });
    }


    /** sends login credential in server and get user login information from server **/
    public void setPassword() {

        RequestParams params = new RequestParams();

        params.put("password", selected_driver_forget_pwd_password);
        Log.e("Login Param password", selected_driver_forget_pwd_password);

        params.put("confirmpassword", selected_driver_forget_pwd_cpassword);
        Log.e("Login Param cpassword", selected_driver_forget_pwd_cpassword);

        params.put("phone", DriverForgetPassword.driver_forget_pwd_profile_mobile_number);
        Log.e("Login Param phone", DriverForgetPassword.driver_forget_pwd_profile_mobile_number);

        params.put("code", DriverForgetPassword.driver_forget_pwd_profile_code);
        Log.e("Login Param Code", DriverForgetPassword.driver_forget_pwd_profile_code);



        AsyncHttpClient client = new AsyncHttpClient();

        client.get(urlPassword, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {

                Log.d("response for connect", response);
                Log.e("Login URL", url);

                try {
                    jarray = new JSONObject(response);

                    driver_forget_pwd_reset_result = jarray.getString("reset");
                    Log.i("result", driver_forget_pwd_reset_result);

                    driver_forget_pwd_reset_Msg = jarray.getString("msg");

                    driver_forget_pwd_reset_profile_code = jarray.getString("code");

                    driver_forget_pwd_reset_profile_mobile_number = jarray.getString("number");


                    driver_forget_pwd_reset_Alldata = jarray.getString("sent");
                    Log.i("Show All Data is", driver_forget_pwd_reset_Alldata);


                    Log.e(TAG, "LResult: " + driver_forget_pwd_reset_result
                            + ", LCode: " + driver_forget_pwd_reset_profile_code
                            + ", LNumber: " + driver_forget_pwd_reset_profile_mobile_number
                            + ", LMessage: " + driver_forget_pwd_reset_Msg);

                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (driver_forget_pwd_reset_result.equals("done")) {
                        progressBar.setIndeterminate(false);
                        edit_driver_forget_pwd_password.setText("");
                        edit_driver_forget_pwd_cpassword.setText("");
                        input_layout_driver_forget_pwd_password.setErrorEnabled(false);
                        input_layout_driver_forget_pwd_cpassword.setErrorEnabled(false);
                        Toast.makeText(getApplicationContext(), driver_forget_pwd_reset_Msg, Toast.LENGTH_SHORT).show();
                        btn_driver_forget_pwd_change_password.setEnabled(true);
                        Intent i = new Intent(getApplicationContext(), UserLogin.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(i);
                    } else if(driver_forget_pwd_reset_result.equals("failed")){
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getApplicationContext(), driver_forget_pwd_reset_Msg, Toast.LENGTH_LONG).show();
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
                alertDialog = new AlertDialog.Builder(DriverForgetPasswordOTP.this).create();
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("Please check Internet connection or Server is not responding");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.show();
                btn_driver_forget_pwd_change_password.setEnabled(true);

            }
        });
    }

    /** sends login credential in server and get user login information from server **/
    public void sendOTP() {

        RequestParams params = new RequestParams();

        params.put("phone", DriverForgetPassword.selected_driver_forget_pwd_mobile_no);
        Log.e("Login Param phone", DriverForgetPassword.selected_driver_forget_pwd_mobile_no);

        params.put("code", DriverForgetPassword.selected_driver_forget_pwd_country_code);
        Log.e("Login Param Code", DriverForgetPassword.selected_driver_forget_pwd_country_code);



        AsyncHttpClient client = new AsyncHttpClient();

        client.get(urlOTP, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {

                Log.d("response for connect", response);
                Log.e("Login URL", url);

                try {
                    jarray = new JSONObject(response);

                    driver_send_otp_pwd_result = jarray.getString("sms");
                    Log.i("result", driver_send_otp_pwd_result);

                    driver_send_otp_pwd_Msg = jarray.getString("msg");

                    driver_send_otp_pwd_profile_code = jarray.getString("code");

                    driver_send_otp_pwd_profile_mobile_number = jarray.getString("number");


                    driver_send_otp_pwd_Alldata = jarray.getString("sent");
                    Log.i("Show All Data is", driver_send_otp_pwd_Alldata);

                    driver_send_otp_pwd_Msg = jarray.getString("msg");
                    Log.i("Message is", driver_send_otp_pwd_Msg);

                    Log.e(TAG, "LResult: " + driver_send_otp_pwd_result
                            + ", LCode: " + driver_send_otp_pwd_profile_code
                            + ", LNumber: " + driver_send_otp_pwd_profile_mobile_number
                            + ", LMessage: " + driver_send_otp_pwd_Msg
                            + ", LDeviceId: " + driver_send_otp_pwd_device_id);

                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (driver_send_otp_pwd_result.equals("done")) {
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getApplicationContext(), driver_send_otp_pwd_Msg, Toast.LENGTH_SHORT).show();
                    } else if(driver_send_otp_pwd_result.equals("failed")){
                        progressBar.setIndeterminate(false);
                        //edit_user_forget_pwd_mobile_no.setText("");
                        //input_layout_user_forget_pwd_mobile_no.setErrorEnabled(false);
                        Toast.makeText(getApplicationContext(), driver_send_otp_pwd_Msg, Toast.LENGTH_LONG).show();
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
                alertDialog = new AlertDialog.Builder(DriverForgetPasswordOTP.this).create();
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
}
