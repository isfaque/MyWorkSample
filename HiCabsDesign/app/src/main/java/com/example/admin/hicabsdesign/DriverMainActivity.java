package com.example.admin.hicabsdesign;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.hicabsdesign.DriverFragments.DriverContact;
import com.example.admin.hicabsdesign.DriverFragments.DriverHome;
import com.example.admin.hicabsdesign.DriverFragments.DriverLogout;
import com.example.admin.hicabsdesign.DriverFragments.DriverSetting;
import com.example.admin.hicabsdesign.GCMManager.GCMRegistrationIntentService;
import com.example.admin.hicabsdesign.NavigationDrawer.NavItem;
import com.example.admin.hicabsdesign.NavigationDrawer.NavListAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 12/05/2016.
 */
public class DriverMainActivity extends ActionBarActivity {

    DrawerLayout drawerLayout;
    RelativeLayout drawerPane;
    ListView lvNav;

    List<NavItem> listNavItems;
    List<Fragment> listFragments;

    AlertDialog alertDialog;

    AlertDialog alertbox;

    JSONObject jArray;

    String UrlUpdate ="http://hicabs-system.com/Api/drivertokenupdate";

    ActionBarDrawerToggle actionBarDrawerToggle;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    public static boolean userHomeShow = false;

    public static String driver_token_id, driver_login_id, update_message, token_message_s, token_message_f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_driver);

        driver_login_id = SplashActivity.sh.getString("login_id", null);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().endsWith(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                    //Registration Success
                    String token = intent.getStringExtra("token");
                    driver_token_id = token;
                    setStatus();
                    //TokenId = token;
                    //Toast.makeText(getApplicationContext(), "GCM token:" + token, Toast.LENGTH_LONG).show();

                }else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)){
                    //Registration Error
                    //Toast.makeText(getApplicationContext(), "GCM registration error!!", Toast.LENGTH_LONG).show();
                }else {

                }
            }
        };

        //Check status of Google play service in device
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if(ConnectionResult.SUCCESS != resultCode) {
            //check type of error
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                Toast.makeText(getApplicationContext(), "Google Play service is not install/enabled in this device", Toast.LENGTH_LONG).show();
                //$0 notification
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
            }else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service", Toast.LENGTH_LONG).show();
            }
        } else {
            //start service
            Intent intent = new Intent(this, GCMRegistrationIntentService.class);
            startService(intent);
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);
        lvNav = (ListView) findViewById(R.id.nav_list);
        listNavItems = new ArrayList<NavItem>();
        listNavItems.add(new NavItem("New Job", "", R.drawable.booking));
        //listNavItems.add(new NavItem("Contact Hicabs", "", R.drawable.ic_contacts_black_24dp));
        listNavItems.add(new NavItem("Setting", "", R.drawable.settings_2));
        listNavItems.add(new NavItem("Logout", "", R.drawable.logout_2));

        NavListAdapter navListAdapter = new NavListAdapter(getApplicationContext(), R.layout.item_nav_list, listNavItems);

        lvNav.setAdapter(navListAdapter);

        listFragments = new ArrayList<Fragment>();
        listFragments.add(new DriverHome());
        //listFragments.add(new DriverContact());
        listFragments.add(new DriverSetting());
        listFragments.add(new DriverLogout());

        // load first fragment as default:
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, listFragments.get(0)).commit();
        setTitle(listNavItems.get(0).getTitle());
        lvNav.setItemChecked(0, true);
        drawerLayout.closeDrawer(drawerPane);

        // set listener for navigation items:
        lvNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // replace the fragment with the selection correspondingly:
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_content, listFragments.get(position)).commit();
                setTitle(listNavItems.get(position).getTitle());
                lvNav.setItemChecked(position, true);
                drawerLayout.closeDrawer(drawerPane);

            }
        });

        // create listener for drawer layout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_opened, R.string.drawer_closed) {

            @Override
            public void onDrawerOpened(View drawerView) {
                // TODO Auto-generated method stub
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // TODO Auto-generated method stub
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }

        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    /** function for options menu **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;

    }

    /** function for item selected in options menu **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    /** function called if back key pressed **/
    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }*/

    public void onBackPressed(){

        alertbox = new AlertDialog.Builder(this).setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(DriverMainActivity.this, UserSelection.class);
                        Intent intent2 = new Intent(DriverMainActivity.this, DriverLogin.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("Exit me", true);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    /** get driver task information from server **/
    @SuppressLint("LongLogTag")
    public void setStatus()
    {
        System.out.println("inside to get Data for Confirm Page");

        RequestParams params = new RequestParams();


        params.put("client", driver_login_id);
        System.out.print(driver_login_id);
        Log.e("Login id is", driver_login_id);

        params.put("token", driver_token_id);
        Log.e("Token id is", driver_token_id);


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
                            //Toast.makeText(UserMainActivity.this, "Token updated", Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }
                    } else {
                        //Toast.makeText(UserMainActivity.this, "Action not updated", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    Toast tost = new Toast(DriverMainActivity.this);
                    tost.makeText(DriverMainActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                    tost.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                    //Intent i = new Intent(DriverMainActivity.this, SplashActivity.class);
                    //startActivity(i);
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Throwable e) {
                alertDialog = new AlertDialog.Builder(DriverMainActivity.this).create();
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
