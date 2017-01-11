package com.example.admin.jerne;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.example.admin.jerne.CustomAutoComplete.CustomAutoCompleteCountry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 17/06/2016.
 */
public class UserSignup extends AppCompatActivity {

    CustomAutoCompleteCountry autoComplete;
    ImageView img_user_signup_flag_icon;

    String [] flags;
    String [] countries;

    public static String selected_user_country_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_signup);
        img_user_signup_flag_icon = (ImageView) findViewById(R.id.img_user_signup_flag_icon);


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
        autoComplete = (CustomAutoCompleteCountry) findViewById(R.id.auto_user_signup_country_code);

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
                img_user_signup_flag_icon.setImageResource(flag_name);

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
            img_user_signup_flag_icon.setImageResource(flag_name);
            for(int j=0;j<flags.length;j++){
                if(flags[j].toLowerCase().equals(simCountry)){
                    String mycountrycode = countries[j];
                    autoComplete.setText(mycountrycode);
                }
            }
        }

        autoComplete.addTextChangedListener(new MyTextWatcher(autoComplete));


    }



    /** for validate flag icon **/
    private boolean validateFlag() {

        int countrycodeposition;
        countrycodeposition = Arrays.asList(countries).indexOf(autoComplete.getText().toString());
        if(countrycodeposition < 0){
            String setflagicon = "flag_icon_002";
            int flag_name = getResources().getIdentifier(setflagicon, "drawable", getPackageName());
            img_user_signup_flag_icon.setImageResource(flag_name);

        }
        return true;
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

                case R.id.auto_user_signup_country_code:
                    validateFlag();
                    break;
            }
        }
    }






}
