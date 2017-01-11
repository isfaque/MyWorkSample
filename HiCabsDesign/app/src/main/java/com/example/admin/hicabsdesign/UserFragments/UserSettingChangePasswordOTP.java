package com.example.admin.hicabsdesign.UserFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.SplashActivity;
import com.example.admin.hicabsdesign.UserForgetPassword;
import com.example.admin.hicabsdesign.UserLogin;
import com.example.admin.hicabsdesign.UserMainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by admin on 08/06/2016.
 */
public class UserSettingChangePasswordOTP extends Fragment {

    EditText edit_user_change_pwd_otp, edit_user_change_pwd_password, edit_user_change_pwd_cpassword;
    TextInputLayout input_layout_user_change_pwd_otp, input_layout_user_change_pwd_password, input_layout_user_change_pwd_cpassword;
    ProgressBar progressBar;
    Button btn_user_change_pwd_change_password, btn_user_change_pwd_send_otp;

    JSONObject jarray;
    AlertDialog alertDialog;

    String url = "http://hicabs-system.com/Api/allowresetpassword";
    String urlPassword = "http://hicabs-system.com/Api/passwordreset";
    String urlOTP = "http://hicabs-system.com/Api/forgetpasssword";
    final String TAG = "Login Page Data is";

    public static String selected_user_change_pwd_otp, selected_user_change_pwd_password, selected_user_change_pwd_cpassword, user_change_pwd_otp_result, user_change_pwd_otp_Msg, user_change_pwd_otp_profile_code, user_change_pwd_otp_profile_mobile_number, user_change_pwd_otp_Alldata;
    public static String user_change_pwd_reset_result, user_change_pwd_reset_Msg, user_change_pwd_reset_profile_code, user_change_pwd_reset_profile_mobile_number, user_change_pwd_reset_Alldata;

    public static String user_send_otp_pwd_result,user_send_otp_pwd_Msg, user_send_otp_pwd_profile_code, user_send_otp_pwd_profile_mobile_number, user_send_otp_pwd_Alldata, user_send_otp_pwd_device_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting_change_pwd_user, container, false);

        //sendOTP();

        input_layout_user_change_pwd_otp = (TextInputLayout) v.findViewById(R.id.input_layout_user_change_pwd_otp);
        input_layout_user_change_pwd_password = (TextInputLayout) v.findViewById(R.id.input_layout_user_change_pwd_password);
        input_layout_user_change_pwd_cpassword = (TextInputLayout) v.findViewById(R.id.input_layout_user_change_pwd_cpassword);

        edit_user_change_pwd_otp = (EditText) v.findViewById(R.id.edit_user_change_pwd_otp);
        edit_user_change_pwd_password = (EditText) v.findViewById(R.id.edit_user_change_pwd_password);
        edit_user_change_pwd_cpassword = (EditText) v.findViewById(R.id.edit_user_change_pwd_cpassword);

        btn_user_change_pwd_change_password = (Button) v.findViewById(R.id.btn_user_change_pwd_change_password);
        btn_user_change_pwd_send_otp = (Button) v.findViewById(R.id.btn_user_change_pwd_send_otp);

        progressBar =(ProgressBar) v.findViewById(R.id.progressBar);

        edit_user_change_pwd_password.addTextChangedListener(new MyTextWatcher(edit_user_change_pwd_password));
        edit_user_change_pwd_cpassword.addTextChangedListener(new MyTextWatcher(edit_user_change_pwd_cpassword));

        btn_user_change_pwd_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_user_change_pwd_otp.getText().toString().trim().isEmpty() && edit_user_change_pwd_otp.getText().toString().length() < 4) {

                    Toast.makeText(getActivity(), "Please enter OTP", Toast.LENGTH_SHORT).show();

                }else if(!validatePassword()) {

                    Toast.makeText(getActivity(), "Please enter valid password", Toast.LENGTH_SHORT).show();

                }else if(!validateConfirmPassword()) {

                    Toast.makeText(getActivity(), "Confirm password does not match", Toast.LENGTH_SHORT).show();

                }else {

                    progressBar.setIndeterminate(true);
                    selected_user_change_pwd_otp = edit_user_change_pwd_otp.getText().toString();
                    selected_user_change_pwd_password = edit_user_change_pwd_password.getText().toString();
                    selected_user_change_pwd_cpassword = edit_user_change_pwd_cpassword.getText().toString();
                    setLoginData();
                }
            }
        });

        btn_user_change_pwd_send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_user_change_pwd_send_otp.setEnabled(false);
                sendOTP();
            }
        });

        return v;
    }

    /** request focution function **/
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    /** function for password validation in registration form **/
    private boolean validatePassword() {
        if (edit_user_change_pwd_password.getText().toString().trim().isEmpty()) {
            input_layout_user_change_pwd_password.setError(getString(R.string.err_msg_password));
            requestFocus(edit_user_change_pwd_password);
            return false;
        } else {

            if (edit_user_change_pwd_password.getText().toString().length() < 6) {

                input_layout_user_change_pwd_password.setError(getString(R.string.err_msg_passwordlength));
                return false;
            } else {

                input_layout_user_change_pwd_password.setErrorEnabled(false);
            }
        }

        return true;
    }

    /** function for confirm password validation in registration form **/
    private boolean validateConfirmPassword() {
        if (edit_user_change_pwd_cpassword.getText().toString().trim().isEmpty()) {
            input_layout_user_change_pwd_cpassword.setError(getString(R.string.err_msg_confirm_password));
            requestFocus(edit_user_change_pwd_cpassword);
            return false;
        }
        else {

            if (edit_user_change_pwd_password.getText().toString().equals(edit_user_change_pwd_cpassword.getText().toString())) {

                input_layout_user_change_pwd_cpassword.setErrorEnabled(false);

            } else {
                input_layout_user_change_pwd_cpassword.setError("Password does not match");
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
                case R.id.edit_user_change_pwd_password:
                    validatePassword();
                    break;
                case R.id.edit_user_change_pwd_cpassword:
                    validateConfirmPassword();
                    break;
            }
        }
    }


    /** sends login credential in server and get user login information from server **/
    public void setLoginData() {

        RequestParams params = new RequestParams();

        params.put("otp", selected_user_change_pwd_otp);
        Log.e("Login Param OTP", selected_user_change_pwd_otp);

        params.put("phone", SplashActivity.sh.getString("login_mobile_number", null));
        Log.e("Login Param phone", SplashActivity.sh.getString("login_mobile_number", null));

        params.put("code", SplashActivity.sh.getString("login_country_code", null));
        Log.e("Login Param Code", SplashActivity.sh.getString("login_country_code", null));



        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {

                Log.d("response for connect", response);
                Log.e("Login URL", url);

                try {
                    jarray = new JSONObject(response);

                    user_change_pwd_otp_result = jarray.getString("otp");
                    Log.i("result", user_change_pwd_otp_result);

                    user_change_pwd_otp_Msg = jarray.getString("msg");

                    user_change_pwd_otp_profile_code = jarray.getString("code");

                    user_change_pwd_otp_profile_mobile_number = jarray.getString("number");


                    user_change_pwd_otp_Alldata = jarray.getString("sent");
                    Log.i("Show All Data is", user_change_pwd_otp_Alldata);


                    Log.e(TAG, "LResult: " + user_change_pwd_otp_result
                            + ", LCode: " + user_change_pwd_otp_profile_code
                            + ", LNumber: " + user_change_pwd_otp_profile_mobile_number
                            + ", LMessage: " + user_change_pwd_otp_Msg);

                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (user_change_pwd_otp_result.equals("match")) {
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getActivity(), user_change_pwd_otp_Msg, Toast.LENGTH_SHORT).show();
                        setPassword();
                        btn_user_change_pwd_change_password.setEnabled(true);
                    } else if(user_change_pwd_otp_result.equals("expired")){
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getActivity(), user_change_pwd_otp_Msg, Toast.LENGTH_LONG).show();
                    } else if(user_change_pwd_otp_result.equals("failed")){
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getActivity(), user_change_pwd_otp_Msg, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    progressBar.setIndeterminate(false);
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
                progressBar.setIndeterminate(false);
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
                btn_user_change_pwd_change_password.setEnabled(true);

            }
        });
    }

    /** sends login credential in server and get user login information from server **/
    public void setPassword() {

        RequestParams params = new RequestParams();

        params.put("password", selected_user_change_pwd_password);
        Log.e("Login Param password", selected_user_change_pwd_password);

        params.put("confirmpassword", selected_user_change_pwd_cpassword);
        Log.e("Login Param cpassword", selected_user_change_pwd_cpassword);

        params.put("phone", SplashActivity.sh.getString("login_mobile_number", null));
        Log.e("Login Param phone", SplashActivity.sh.getString("login_mobile_number", null));

        params.put("code", SplashActivity.sh.getString("login_country_code", null));
        Log.e("Login Param Code", SplashActivity.sh.getString("login_country_code", null));


        AsyncHttpClient client = new AsyncHttpClient();

        client.get(urlPassword, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {

                Log.d("response for connect", response);
                Log.e("Login URL", url);

                try {
                    jarray = new JSONObject(response);

                    user_change_pwd_reset_result = jarray.getString("reset");
                    Log.i("result", user_change_pwd_reset_result);

                    user_change_pwd_reset_Msg = jarray.getString("msg");

                    user_change_pwd_reset_profile_code = jarray.getString("code");

                    user_change_pwd_reset_profile_mobile_number = jarray.getString("number");


                    user_change_pwd_reset_Alldata = jarray.getString("sent");
                    Log.i("Show All Data is", user_change_pwd_reset_Alldata);


                    Log.e(TAG, "LResult: " + user_change_pwd_reset_result
                            + ", LCode: " + user_change_pwd_reset_profile_code
                            + ", LNumber: " + user_change_pwd_reset_profile_mobile_number
                            + ", LMessage: " + user_change_pwd_reset_Msg);

                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (user_change_pwd_reset_result.equals("done")) {
                        progressBar.setIndeterminate(false);
                        edit_user_change_pwd_password.setText("");
                        edit_user_change_pwd_cpassword.setText("");
                        input_layout_user_change_pwd_password.setErrorEnabled(false);
                        input_layout_user_change_pwd_cpassword.setErrorEnabled(false);
                        Toast.makeText(getActivity(), user_change_pwd_reset_Msg, Toast.LENGTH_SHORT).show();
                        btn_user_change_pwd_change_password.setEnabled(true);
                        Intent i = new Intent(getActivity(), UserMainActivity.class);
                        startActivity(i);
                    } else if(user_change_pwd_reset_result.equals("failed")){
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getActivity(), user_change_pwd_reset_Msg, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    progressBar.setIndeterminate(false);
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
                progressBar.setIndeterminate(false);
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
                btn_user_change_pwd_change_password.setEnabled(true);

            }
        });
    }

    /** sends login credential in server and get user login information from server **/
    public void sendOTP() {

        RequestParams params = new RequestParams();

        params.put("phone", SplashActivity.sh.getString("login_mobile_number", null));
        Log.e("Login Param phone", SplashActivity.sh.getString("login_mobile_number", null));

        params.put("code", SplashActivity.sh.getString("login_country_code", null));
        Log.e("Login Param Code", SplashActivity.sh.getString("login_country_code", null));



        AsyncHttpClient client = new AsyncHttpClient();

        client.get(urlOTP, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {

                Log.d("response for connect", response);
                Log.e("Login URL", url);

                try {
                    jarray = new JSONObject(response);

                    user_send_otp_pwd_result = jarray.getString("sms");
                    Log.i("result", user_send_otp_pwd_result);

                    user_send_otp_pwd_Msg = jarray.getString("msg");

                    user_send_otp_pwd_profile_code = jarray.getString("code");

                    user_send_otp_pwd_profile_mobile_number = jarray.getString("number");


                    user_send_otp_pwd_Alldata = jarray.getString("sent");
                    Log.i("Show All Data is", user_send_otp_pwd_Alldata);

                    user_send_otp_pwd_Msg = jarray.getString("msg");
                    Log.i("Message is", user_send_otp_pwd_Msg);

                    Log.e(TAG, "LResult: " + user_send_otp_pwd_result
                            + ", LCode: " + user_send_otp_pwd_profile_code
                            + ", LNumber: " + user_send_otp_pwd_profile_mobile_number
                            + ", LMessage: " + user_send_otp_pwd_Msg
                            + ", LDeviceId: " + user_send_otp_pwd_device_id);

                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (user_send_otp_pwd_result.equals("done")) {
                        progressBar.setIndeterminate(false);
                        Toast.makeText(getActivity(), user_send_otp_pwd_Msg, Toast.LENGTH_SHORT).show();
                    } else if(user_send_otp_pwd_result.equals("failed")){
                        progressBar.setIndeterminate(false);
                        //edit_user_forget_pwd_mobile_no.setText("");
                        //input_layout_user_forget_pwd_mobile_no.setErrorEnabled(false);
                        Toast.makeText(getActivity(), user_send_otp_pwd_Msg, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    progressBar.setIndeterminate(false);
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
                progressBar.setIndeterminate(false);
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

            }
        });
    }
}