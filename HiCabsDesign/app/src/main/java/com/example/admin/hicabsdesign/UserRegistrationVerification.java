package com.example.admin.hicabsdesign;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by admin on 09/06/2016.
 */
public class UserRegistrationVerification extends AppCompatActivity {

    Button btn_user_reg_send_otp, btn_user_reg_activate;
    EditText edit_user_reg_otp;
    TextInputLayout input_layout_user_reg_otp;

    JSONObject jarray;
    AlertDialog alertDialog;
    ProgressBar progressBar;

    String url = "http://hicabs-system.com/Api/mobilenumberverification";
    String urlOTP = "http://hicabs-system.com/Api/resendtheactivationcode";
    final String TAG = "Login Page Data is";

    public static String user_reg_send_otp_pwd_result,user_reg_send_otp_pwd_Msg, user_reg_send_otp_pwd_profile_code, user_reg_send_otp_pwd_profile_mobile_number, user_reg_send_otp_pwd_Alldata, user_reg_send_otp_pwd_device_id;

    public static String selected_user_reg_otp, user_reg_otp_result, user_reg_otp_Msg, user_reg_otp_profile_code, user_reg_otp_profile_mobile_number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_verification);

        edit_user_reg_otp = (EditText) findViewById(R.id.edit_user_reg_otp);
        input_layout_user_reg_otp = (TextInputLayout) findViewById(R.id.input_layout_user_reg_otp);

        progressBar =(ProgressBar)findViewById(R.id.progressBar);

        btn_user_reg_send_otp = (Button) findViewById(R.id.btn_user_reg_send_otp);
        btn_user_reg_activate = (Button) findViewById(R.id.btn_user_reg_activate);

        btn_user_reg_send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_user_reg_send_otp.setEnabled(false);
                sendOTP();
            }
        });

        btn_user_reg_activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_user_reg_otp.getText().toString().trim().isEmpty() && edit_user_reg_otp.getText().toString().length() < 4){

                    Toast.makeText(getApplicationContext(), "Please enter valid OTP", Toast.LENGTH_SHORT).show();

                }else{

                    progressBar.setIndeterminate(true);
                    selected_user_reg_otp = edit_user_reg_otp.getText().toString();
                    setLoginData();

                }



            }
        });
    }


    public void sendOTP() {

        RequestParams params = new RequestParams();

        params.put("phone", UserRegistration.Imobile);
        Log.e("Login Param phone", UserRegistration.Imobile);

        params.put("code", UserRegistration.Icode);
        Log.e("Login Param Code", UserRegistration.Icode);



        AsyncHttpClient client = new AsyncHttpClient();

        client.get(urlOTP, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {

                Log.d("response for connect", response);
                Log.e("Login URL", urlOTP);

                try {
                    jarray = new JSONObject(response);

                    user_reg_send_otp_pwd_result = jarray.getString("otp");
                    Log.i("result", user_reg_send_otp_pwd_result);

                    user_reg_send_otp_pwd_Msg = jarray.getString("msg");

                    user_reg_send_otp_pwd_profile_code = jarray.getString("code");

                    user_reg_send_otp_pwd_profile_mobile_number = jarray.getString("number");


                    Log.e(TAG, "LResult: " + user_reg_send_otp_pwd_result
                            + ", LCode: " + user_reg_send_otp_pwd_profile_code
                            + ", LNumber: " + user_reg_send_otp_pwd_profile_mobile_number
                            + ", LMessage: " + user_reg_send_otp_pwd_Msg);

                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (user_reg_send_otp_pwd_result.equals("send")) {
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getApplicationContext(), user_reg_send_otp_pwd_Msg, Toast.LENGTH_SHORT).show();
                    } else if(user_reg_send_otp_pwd_result.equals("failed")){
                        progressBar.setIndeterminate(false);
                        //edit_user_forget_pwd_mobile_no.setText("");
                        //input_layout_user_forget_pwd_mobile_no.setErrorEnabled(false);
                        Toast.makeText(getApplicationContext(), user_reg_send_otp_pwd_Msg, Toast.LENGTH_LONG).show();
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
                alertDialog = new AlertDialog.Builder(UserRegistrationVerification.this).create();
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


    public void setLoginData() {

        RequestParams params = new RequestParams();

        params.put("otp", selected_user_reg_otp);
        Log.e("Login Param OTP", selected_user_reg_otp);

        params.put("phone", UserRegistration.Imobile);
        Log.e("Login Param phone", UserRegistration.Imobile);

        params.put("code", UserRegistration.Icode);
        Log.e("Login Param Code", UserRegistration.Icode);



        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {

                Log.d("response for connect", response);
                Log.e("Login URL", url);

                try {
                    jarray = new JSONObject(response);

                    user_reg_otp_result = jarray.getString("otp");
                    Log.i("result", user_reg_otp_result);
                    //Log.e("result", user_forget_pwd_otp_result1);

                    user_reg_otp_Msg = jarray.getString("msg");

                    user_reg_otp_profile_code = jarray.getString("code");

                    user_reg_otp_profile_mobile_number = jarray.getString("number");


                    //user_forget_pwd_otp_Alldata = jarray.getString("sent");
                    //Log.i("Show All Data is", user_forget_pwd_otp_Alldata);


                    Log.e(TAG, "LResult: " + user_reg_otp_result
                            + ", LCode: " + user_reg_otp_profile_code
                            + ", LNumber: " + user_reg_otp_profile_mobile_number
                            + ", LMessage: " + user_reg_otp_Msg);

                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (user_reg_otp_result.equals("match")) {
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getApplicationContext(), user_reg_otp_Msg, Toast.LENGTH_SHORT).show();
                        btn_user_reg_activate.setEnabled(true);
                        Intent jk = new Intent(UserRegistrationVerification.this, UserLogin.class);
                        jk.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(jk);
                    } else{
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getApplicationContext(), user_reg_otp_Msg, Toast.LENGTH_LONG).show();
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
                alertDialog = new AlertDialog.Builder(UserRegistrationVerification.this).create();
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("Please check Internet connection or Server is not responding");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.show();
                btn_user_reg_activate.setEnabled(true);

            }
        });
    }
}
