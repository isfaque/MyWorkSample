package com.example.admin.qbreaker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by admin on 08/06/2016.
 */
public class LoginActivity extends AppCompatActivity {

    Button btn_user_login;
    EditText edit_user_name, edit_user_password;
    TextInputLayout input_layout_user_name, input_layout_user_password;
    Spinner spinner_user_type;

    public static String selected_usertype, selected_username, selected_userpassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
            return; // add this to prevent from doing unnecessary stuffs
        }

        input_layout_user_name = (TextInputLayout) findViewById(R.id.input_layout_user_name);
        input_layout_user_password = (TextInputLayout) findViewById(R.id.input_layout_user_password);

        edit_user_name = (EditText) findViewById(R.id.edit_user_name);
        edit_user_password = (EditText) findViewById(R.id.edit_user_password);

        spinner_user_type = (Spinner) findViewById(R.id.spinner_user_type);
        spinner_user_type.setOnItemSelectedListener(new ItemSelectedListener());

        btn_user_login = (Button) findViewById(R.id.btn_user_login);

        edit_user_name.addTextChangedListener(new MyTextWatcher(edit_user_name));
        edit_user_password.addTextChangedListener(new MyTextWatcher(edit_user_password));

        btn_user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateLogName()){

                    Toast.makeText(getApplicationContext(), "Please enter valid user name", Toast.LENGTH_SHORT).show();

                }else if(!validatePassword()) {

                    Toast.makeText(getApplicationContext(), "Please enter valid password", Toast.LENGTH_SHORT).show();

                }else if(!validateUserType()){

                    Toast.makeText(getApplicationContext(), "Please select valid user type", Toast.LENGTH_SHORT).show();

                }else{

                    selected_usertype = spinner_user_type.getSelectedItem().toString();
                    selected_username = edit_user_name.getText().toString();
                    selected_userpassword = edit_user_password.getText().toString();

                    SplashActivity.editor.putString("loginTest", "true");
                    SplashActivity.editor.putString("loginType", selected_usertype);
                    SplashActivity.editor.putString("login_name", selected_username);
                    SplashActivity.editor.putString("login_password", selected_userpassword);
                    SplashActivity.editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                }



            }
        });
    }


    /** For validate mobile number **/
    private boolean validateLogName() {
        if (edit_user_name.getText().toString().trim().isEmpty()) {
            input_layout_user_name.setError(getString(R.string.err_msg_name));
            requestFocus(edit_user_name);
            return false;
        } else {
//            input_layout_login_mobile.setErrorEnabled(false);
            if (edit_user_name.getText().toString().length() < 4) {

                input_layout_user_name.setError(getString(R.string.err_msg_namelength));
                return false;
            } else {

                input_layout_user_name.setErrorEnabled(false);
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

    /** for validate Usertype **/
    private boolean validateUserType() {
        if (spinner_user_type.getSelectedItem().toString().trim().isEmpty()) {
            return false;
        } else {
            if (spinner_user_type.getSelectedItem().equals("Select User Type")) {

                return false;
            }
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
                case R.id.edit_user_name:
                    validateLogName();
                    break;

                case R.id.edit_user_password:
                    validatePassword();
                    break;

                case R.id.spinner_user_type:
                    validateUserType();
                    break;
            }
        }
    }


    /** Item selected listener for user passenger no spinner  **/
    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            String firstItem = (String) spinner_user_type.getSelectedItem();

            if (firstItem.equals((spinner_user_type.getSelectedItem()))) {
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
