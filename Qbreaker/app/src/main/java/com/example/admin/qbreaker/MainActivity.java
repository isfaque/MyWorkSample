package com.example.admin.qbreaker;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.qbreaker.NavigationDrawer.NavItem;
import com.example.admin.qbreaker.NavigationDrawer.NavListAdapter;
import com.example.admin.qbreaker.UserFragment.UserAbout;
import com.example.admin.qbreaker.UserFragment.UserContact;
import com.example.admin.qbreaker.UserFragment.UserHome;
import com.example.admin.qbreaker.UserFragment.UserJobSearch;
import com.example.admin.qbreaker.UserFragment.UserLogout;
import com.example.admin.qbreaker.UserFragment.UserNotice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 08/06/2016.
 */
public class MainActivity extends AppCompatActivity {


    public static boolean isMainActivityShown;
    public static boolean isUserAboutShown=false;
    public static boolean isUserContactShown=false;
    public static boolean isUserNoticeShown=false;
    public static boolean isUserNoticeDetailShown=false;

    DrawerLayout drawerLayout;
    RelativeLayout drawerPane;
    ListView lvNav;
    AlertDialog alertbox;

    TextView txt_user_type;

    List<NavItem> listNavItems;
    List<Fragment> listFragments;

    ActionBarDrawerToggle actionBarDrawerToggle;

    public static String profile_usertype;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile_usertype = SplashActivity.sh.getString("loginType", null);

        txt_user_type = (TextView) findViewById(R.id.txt_user_type);

        txt_user_type.setText("("+profile_usertype+")");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);
        lvNav = (ListView) findViewById(R.id.nav_list);
        listNavItems = new ArrayList<NavItem>();
        listNavItems.add(new NavItem("Qbreaker", "Connecting Learners", R.drawable.home_icon));
        listNavItems.add(new NavItem("Notice Board", "Notices from College", R.drawable.notice_icon));
        listNavItems.add(new NavItem("Job Search", "Search Jobs", R.drawable.job_icon));
        listNavItems.add(new NavItem("About Us", "About Qbreaker", R.drawable.about_icon));
        listNavItems.add(new NavItem("Contact Us", "Contact Qbreaker", R.drawable.contact_icon));
        listNavItems.add(new NavItem("Logout", "", R.drawable.log_icon));

        NavListAdapter navListAdapter = new NavListAdapter(getApplicationContext(), R.layout.item_nav_list, listNavItems);

        lvNav.setAdapter(navListAdapter);

        listFragments = new ArrayList<Fragment>();
        listFragments.add(new UserHome());
        listFragments.add(new UserNotice());
        listFragments.add(new UserJobSearch());
        listFragments.add(new UserAbout());
        listFragments.add(new UserContact());
        listFragments.add(new UserLogout());

        // load first home fragment as default in navigation drawer:
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

    public void onBackPressed(){
        if(isUserNoticeShown || isUserAboutShown || isUserContactShown){

            alertbox = new AlertDialog.Builder(this).setMessage("Do you want to exit application?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        }else if(isUserNoticeDetailShown){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, listFragments.get(1)).commit();
            setTitle(listNavItems.get(1).getTitle());
            lvNav.setItemChecked(1, true);
        }
    }

    /** this function call when you click on back button for exit application **/
    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    /** function shows the exit application dialogue box **/
    /*
    protected void exitByBackKey() {

        alertbox = new AlertDialog.Builder(this).setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        //finish();
    }*/


    public void sendMyBigTextStyleNotification(String mydetail, String mytitle){
        /*
        String msgText = "Jeally Bean Notification example!! "
                + "where you will see three different kind of notification. "
                + "you can even put the very long string here.";*/

        String msgText = mydetail;

        NotificationManager notificationManager = getNotificationManager();
        PendingIntent pi = getPendingIntent();
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(mytitle)
                .setContentText(mytitle)
                .setSmallIcon(R.drawable.cloud_icon)
                .setAutoCancel(true)
                .setContentIntent(pi);
        Notification notification = new Notification.BigTextStyle(builder)
                .bigText(msgText).build();

        notificationManager.notify(0, notification);
    }

    public PendingIntent getPendingIntent() {
        return PendingIntent.getActivity(MainActivity.this, 0, new Intent(MainActivity.this,
                MainActivity.class), 0);
    }

    public NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
}
