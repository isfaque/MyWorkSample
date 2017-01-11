package com.example.admin.hicabsdesign.UserFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.hicabsdesign.DriverFragments.DriverHomeTaskThree;
import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.SplashActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 26/05/2016.
 */
public class UserSettingProfileUpdate extends Fragment {

    EditText edit_user_profile_edit_fname, edit_user_profile_edit_lname, edit_user_profile_edit_email;
    Button btn_user_profile_edit_cancel, btn_user_profile_edit_update;
    TextInputLayout input_layout_user_profile_edit_fname, input_layout_user_profile_edit_lname, input_layout_user_profile_edit_email;

    AlertDialog alertDialog;

    JSONObject jArray;

    String UrlUpdate ="http://hicabs-system.com/Api/updateuserprofile";

    public static String user_setting_profile_name, user_setting_profile_email, user_login_id, user_fname, user_lname, user_edit_fname, user_edit_lname, user_edit_email, update_message, updated_name, updated_email;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_setting_profile_edit_user, container, false);

        input_layout_user_profile_edit_fname = (TextInputLayout) v.findViewById(R.id.input_layout_user_profile_edit_fname);
        input_layout_user_profile_edit_lname = (TextInputLayout) v.findViewById(R.id.input_layout_user_profile_edit_lname);
        input_layout_user_profile_edit_email = (TextInputLayout) v.findViewById(R.id.input_layout_user_profile_edit_email);

        edit_user_profile_edit_fname = (EditText) v.findViewById(R.id.edit_user_profile_edit_fname);
        edit_user_profile_edit_lname = (EditText) v.findViewById(R.id.edit_user_profile_edit_lname);
        edit_user_profile_edit_email = (EditText) v.findViewById(R.id.edit_user_profile_edit_email);
        btn_user_profile_edit_cancel = (Button) v.findViewById(R.id.btn_user_profile_edit_cancel);
        btn_user_profile_edit_update = (Button) v.findViewById(R.id.btn_user_profile_edit_update);

        user_setting_profile_name = SplashActivity.sh.getString("login_name", null);
        user_setting_profile_email = SplashActivity.sh.getString("login_email", null);
        user_login_id = SplashActivity.sh.getString("login_id", null);

        String split[]= user_setting_profile_name.split(" ");

        user_fname = split[0];
        user_lname = split[1].trim();

        edit_user_profile_edit_fname.setText(user_fname);
        edit_user_profile_edit_lname.setText(user_lname);
        edit_user_profile_edit_email.setText(user_setting_profile_email);

        edit_user_profile_edit_fname.addTextChangedListener(new MyTextWatcher(edit_user_profile_edit_fname));
        edit_user_profile_edit_lname.addTextChangedListener(new MyTextWatcher(edit_user_profile_edit_lname));
        edit_user_profile_edit_email.addTextChangedListener(new MyTextWatcher(edit_user_profile_edit_email));

        btn_user_profile_edit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr;
                fr = new UserSettingProfile();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_user_profile_edit_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateFirstName()) {

                    Toast.makeText(getActivity(), "Please enter first name", Toast.LENGTH_SHORT).show();

                }else if(!validateLastName()) {

                    Toast.makeText(getActivity(), "Please enter last name", Toast.LENGTH_SHORT).show();

                }else{

                    user_edit_fname = edit_user_profile_edit_fname.getText().toString();
                    user_edit_lname = edit_user_profile_edit_lname.getText().toString();
                    user_edit_email = edit_user_profile_edit_email.getText().toString();

                    setStatus();

                }



            }
        });

        return v;
    }

    /** get driver task information from server **/
    @SuppressLint("LongLogTag")
    public void setStatus()
    {
        System.out.println("inside to get Data for Confirm Page");

        RequestParams params = new RequestParams();


        params.put("client", user_login_id);
        System.out.print(user_login_id);
        Log.e("Login id is", user_login_id);

        params.put("fname", user_edit_fname);
        Log.e("First name is", user_edit_fname);

        params.put("lname", user_edit_lname);
        Log.e("First name is", user_edit_lname);

        params.put("email", user_edit_email);
        Log.e("First name is", user_edit_email);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(UrlUpdate, params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {
                Log.d("response for Confirm Page connect", response);
                String str_response = response;
                Log.e("Confirm Page URL", UrlUpdate);
                try {

                    jArray = new JSONObject(response);

                    update_message = jArray.getString("msg");
                    Log.i("Booking Data is", update_message);


                } catch (Exception e) {
                    // TODO: handle exception
                    //Log.i("Exception",e.getMessage());
                }

                try {
                    if (update_message.equals("updated")) {

                        try {

                            update_message = jArray.getString("msg");
                            updated_name = jArray.getString("name");
                            updated_email = jArray.getString("email");

                            Log.i("Confirm Rate in try block is", update_message);
                            Log.i("Updated name", updated_name);

                            SplashActivity.editor.putString("login_name", updated_name);
                            SplashActivity.editor.putString("login_email", updated_email);
                            SplashActivity.editor.commit();

                            Fragment fr;
                            fr = new UserSettingProfile();
                            FragmentManager fm = getFragmentManager();
                            android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.main_content, fr);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Action not updated", Toast.LENGTH_LONG).show();
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

            }
        });

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
                case R.id.edit_user_profile_edit_fname:
                    validateFirstName();
                    break;
                case R.id.edit_user_profile_edit_lname:
                    validateLastName();
                    break;
                case R.id.edit_user_profile_edit_email:
                    validateEmail();
                    break;
            }
        }
    }

    /** function for first name validation in registration form **/
    private boolean validateFirstName() {
        if (edit_user_profile_edit_fname.getText().toString().trim().isEmpty()) {
            input_layout_user_profile_edit_fname.setError(getString(R.string.err_msg_firstname));
            requestFocus(edit_user_profile_edit_fname);
            return false;
        } else {
            input_layout_user_profile_edit_fname.setErrorEnabled(false);
        }
        return true;
    }

    /** function for last name validation in registration form **/
    private boolean validateLastName() {
        if (edit_user_profile_edit_lname.getText().toString().trim().isEmpty()) {

            input_layout_user_profile_edit_lname.setError(getString(R.string.err_msg_lastname));
            requestFocus(edit_user_profile_edit_lname);
            return false;

        } else {
            input_layout_user_profile_edit_lname.setErrorEnabled(false);
        }

        return true;
    }

    /** function for email validation in registration mail **/
    private boolean validateEmail() {
        String email = edit_user_profile_edit_email.getText().toString().trim();

        if (!isValidEmail(email)) {
            input_layout_user_profile_edit_email.setError(getString(R.string.err_msg_email));
            requestFocus(edit_user_profile_edit_email);
            return false;
        } else {
            input_layout_user_profile_edit_email.setErrorEnabled(false);
        }

        return true;
    }

    /** request focution function **/
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    /** function check mail valid or not **/
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
