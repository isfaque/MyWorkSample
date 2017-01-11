package com.example.mascot.socialtree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebViewFragment;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mascot.socialtree.NavigationDrawer.NavItem;
import com.example.mascot.socialtree.NavigationDrawer.NavListAdapter;
import com.example.mascot.socialtree.UserFragment.UserFacebook;
import com.example.mascot.socialtree.UserFragment.UserFlickr;
import com.example.mascot.socialtree.UserFragment.UserGooglePlus;
import com.example.mascot.socialtree.UserFragment.UserHome;
import com.example.mascot.socialtree.UserFragment.UserLastfm;
import com.example.mascot.socialtree.UserFragment.UserLinkedIn;
import com.example.mascot.socialtree.UserFragment.UserMyspace;
import com.example.mascot.socialtree.UserFragment.UserReddit;
import com.example.mascot.socialtree.UserFragment.UserTwitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mascot on 7/3/2016.
 */
public class MainActivity extends AppCompatActivity implements UserTwitter.OnHeadlineSelectedListener, UserFacebook.OnHeadlineSelectedListener, UserFlickr.OnHeadlineSelectedListener, UserGooglePlus.OnHeadlineSelectedListener, UserLastfm.OnHeadlineSelectedListener, UserLinkedIn.OnHeadlineSelectedListener, UserMyspace.OnHeadlineSelectedListener, UserReddit.OnHeadlineSelectedListener {

    public static String myCurrentUrl;

    public void onArticleSelected(String position) {

        Toast.makeText(this, position, Toast.LENGTH_SHORT)
                .show();
        myCurrentUrl = position;
        // The user selected the headline of an article from the He
        //
        //
        // adlinesFragment
        // Do something here to display that article
    }


    public static boolean isUserHomeShown=false;
    public static boolean isUserFacebookShown=false;
    public static boolean isUserFlickrShown=false;
    public static boolean isUserGooglePlusShown=false;
    public static boolean isUserLastfmShown=false;
    public static boolean isUserLinkedInShown=false;
    public static boolean isUserMyspaceShown=false;
    public static boolean isUserRedditShown=false;
    public static boolean isUserTwitterShown=false;

    DrawerLayout drawerLayout;
    RelativeLayout drawerPane;
    ListView lvNav;
    AlertDialog alertbox;

    TextView txt_user_type;

    List<NavItem> listNavItems;
    List<Fragment> listFragments;

    ActionBarDrawerToggle actionBarDrawerToggle;

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
            return; // add this to prevent from doing unnecessary stuffs
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);
        lvNav = (ListView) findViewById(R.id.nav_list);
        listNavItems = new ArrayList<NavItem>();
        listNavItems.add(new NavItem("Social Circle", "Social sites in circle", R.drawable.social_circle_128));
        listNavItems.add(new NavItem("Facebook", "Connecting Learners", R.drawable.st_facebook));
        listNavItems.add(new NavItem("Twitter", "connect with your friends", R.drawable.st_twitter));
        listNavItems.add(new NavItem("Google+", "interest based social network", R.drawable.st_googleplus));
        listNavItems.add(new NavItem("LinkedIn", "manage your professional identity", R.drawable.st_linkein));
        listNavItems.add(new NavItem("Reddit", "user generated news links", R.drawable.st_reddit));
        listNavItems.add(new NavItem("Myspace", "discover and share", R.drawable.st_myspace));
        listNavItems.add(new NavItem("Flickr", "picture galleries", R.drawable.st_flickr));
        listNavItems.add(new NavItem("Lastfm", "online music catalog", R.drawable.st_lastfm));
        //listNavItems.add(new NavItem("Logout", "", R.drawable.log_icon));

        NavListAdapter navListAdapter = new NavListAdapter(getApplicationContext(), R.layout.item_nav_list, listNavItems);

        lvNav.setAdapter(navListAdapter);

        listFragments = new ArrayList<Fragment>();
        listFragments.add(new UserHome());
        listFragments.add(new UserFacebook());
        listFragments.add(new UserTwitter());
        listFragments.add(new UserGooglePlus());
        listFragments.add(new UserLinkedIn());
        listFragments.add(new UserReddit());
        listFragments.add(new UserMyspace());
        listFragments.add(new UserFlickr());
        listFragments.add(new UserLastfm());
        //listFragments.add(new UserLogout());

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

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        // Create the share Intent
        String playStoreLink = "https://play.google.com/store/apps/details?id=" +
                getPackageName();
        String yourShareText = "Install this app " + playStoreLink;
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain").setText(yourShareText).getIntent();

        setShareIntent(shareIntent);

        return true;
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, myCurrentUrl);

        startActivity(Intent.createChooser(share, "Share Link"));
    }


    /** function for item selected in options menu **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        else
            switch (item.getItemId()) {
                // action with ID action_refresh was selected
                case R.id.menu_item_share:
                    Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                            .show();
                    shareTextUrl();
                    break;

                default:
                    break;
            }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


    /*
    public void onBackPressed(){
        if(isUserHomeShown){

            alertbox = new AlertDialog.Builder(this).setMessage("Do you want to exit application?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
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
    }*/


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {

        if(isUserFacebookShown) {

            if(UserFacebook.canGoBack()) {
                UserFacebook.goBack();
            }else{
                super.onBackPressed();
            }

        }else if(isUserFlickrShown) {

            if(UserFlickr.canGoBack()) {
                UserFlickr.goBack();
            }else{
                super.onBackPressed();
            }

        }else if(isUserGooglePlusShown) {

            if(UserGooglePlus.canGoBack()) {
                UserGooglePlus.goBack();
            }else{
                super.onBackPressed();
            }

        }else if(isUserLastfmShown) {

            if(UserLastfm.canGoBack()) {
                UserLastfm.goBack();
            }else{
                super.onBackPressed();
            }

        }else if(isUserLinkedInShown) {

            if(UserLinkedIn.canGoBack()) {
                UserLinkedIn.goBack();
            }else{
                super.onBackPressed();
            }

        }else if(isUserMyspaceShown) {

            if(UserMyspace.canGoBack()) {
                UserMyspace.goBack();
            }else{
                super.onBackPressed();
            }

        }else if(isUserRedditShown) {

            if(UserReddit.canGoBack()) {
                UserReddit.goBack();
            }else{
                super.onBackPressed();
            }

        }else if(isUserTwitterShown){

            if(UserTwitter.canGoBack()){
                UserTwitter.goBack();
            }else{
                super.onBackPressed();
            }

        }else{

            super.onBackPressed();

        }

        /*
        if(UserFacebook.canGoBack()) {
            UserFacebook.goBack();
        }else if(UserFlickr.canGoBack()) {
            UserFlickr.goBack();
        }else if(UserGooglePlus.canGoBack()) {
            UserGooglePlus.goBack();
        }else if(UserLastfm.canGoBack()) {
            UserLastfm.goBack();
        }else if(UserLinkedIn.canGoBack()) {
            UserLinkedIn.goBack();
        }else if(UserMyspace.canGoBack()) {
            UserMyspace.goBack();
        }else if(UserReddit.canGoBack()) {
            UserReddit.goBack();
        }else if(UserTwitter.canGoBack()){
            UserTwitter.goBack();
        }else{
            Toast.makeText(getApplicationContext(),"Exit in Main activity", Toast.LENGTH_LONG);
            //super.onBackPressed();
        }*/

    }




}
