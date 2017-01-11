package com.example.mascot.socialtree.UserFragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mascot.socialtree.MainActivity;
import com.example.mascot.socialtree.R;

/**
 * Created by Mascot on 7/3/2016.
 */
public class UserHome extends Fragment {

    ImageView img_btn_one, img_btn_two, img_btn_three, img_btn_four, img_btn_five, img_btn_six, img_btn_seven, img_btn_eight, img_btn_centre;
    float i;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        MainActivity.isUserHomeShown=true;
        MainActivity.isUserFacebookShown=false;
        MainActivity.isUserFlickrShown=false;
        MainActivity.isUserGooglePlusShown=false;
        MainActivity.isUserLastfmShown=false;
        MainActivity.isUserLinkedInShown=false;
        MainActivity.isUserMyspaceShown=false;
        MainActivity.isUserRedditShown=false;
        MainActivity.isUserTwitterShown=false;

        img_btn_one = (ImageView) v.findViewById(R.id.img_btn_one);
        img_btn_two = (ImageView) v.findViewById(R.id.img_btn_two);
        img_btn_three = (ImageView) v.findViewById(R.id.img_btn_three);
        img_btn_four = (ImageView) v.findViewById(R.id.img_btn_four);
        img_btn_five = (ImageView) v.findViewById(R.id.img_btn_five);
        img_btn_six = (ImageView) v.findViewById(R.id.img_btn_six);
        img_btn_seven = (ImageView) v.findViewById(R.id.img_btn_seven);
        img_btn_eight = (ImageView) v.findViewById(R.id.img_btn_eight);

        img_btn_centre = (ImageView) v.findViewById(R.id.img_btn_centre);

        img_btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr;
                fr = new UserLinkedIn();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        img_btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr;
                fr = new UserGooglePlus();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        img_btn_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr;
                fr = new UserFacebook();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        img_btn_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr;
                fr = new UserTwitter();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        img_btn_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr;
                fr = new UserReddit();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        img_btn_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr;
                fr = new UserMyspace();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        img_btn_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr;
                fr = new UserLastfm();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        img_btn_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr;
                fr = new UserFlickr();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        img_btn_centre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img_btn_one.setRotation(30);
                        img_btn_two.setRotation(30);
                        img_btn_three.setRotation(30);
                        img_btn_four.setRotation(30);
                        img_btn_five.setRotation(30);
                        img_btn_six.setRotation(30);
                        img_btn_seven.setRotation(30);
                        img_btn_eight.setRotation(30);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                img_btn_one.setRotation(60);
                                img_btn_two.setRotation(60);
                                img_btn_three.setRotation(60);
                                img_btn_four.setRotation(60);
                                img_btn_five.setRotation(60);
                                img_btn_six.setRotation(60);
                                img_btn_seven.setRotation(60);
                                img_btn_eight.setRotation(60);

                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        img_btn_one.setRotation(90);
                                        img_btn_two.setRotation(90);
                                        img_btn_three.setRotation(90);
                                        img_btn_four.setRotation(90);
                                        img_btn_five.setRotation(90);
                                        img_btn_six.setRotation(90);
                                        img_btn_seven.setRotation(90);
                                        img_btn_eight.setRotation(90);

                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                img_btn_one.setRotation(120);
                                                img_btn_two.setRotation(120);
                                                img_btn_three.setRotation(120);
                                                img_btn_four.setRotation(120);
                                                img_btn_five.setRotation(120);
                                                img_btn_six.setRotation(120);
                                                img_btn_seven.setRotation(120);
                                                img_btn_eight.setRotation(120);

                                                final Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        img_btn_one.setRotation(150);
                                                        img_btn_two.setRotation(150);
                                                        img_btn_three.setRotation(150);
                                                        img_btn_four.setRotation(150);
                                                        img_btn_five.setRotation(150);
                                                        img_btn_six.setRotation(150);
                                                        img_btn_seven.setRotation(150);
                                                        img_btn_eight.setRotation(150);

                                                        final Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                img_btn_one.setRotation(180);
                                                                img_btn_two.setRotation(180);
                                                                img_btn_three.setRotation(180);
                                                                img_btn_four.setRotation(180);
                                                                img_btn_five.setRotation(180);
                                                                img_btn_six.setRotation(180);
                                                                img_btn_seven.setRotation(180);
                                                                img_btn_eight.setRotation(180);

                                                                final Handler handler = new Handler();
                                                                handler.postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        img_btn_one.setRotation(210);
                                                                        img_btn_two.setRotation(21);
                                                                        img_btn_three.setRotation(210);
                                                                        img_btn_four.setRotation(210);
                                                                        img_btn_five.setRotation(210);
                                                                        img_btn_six.setRotation(210);
                                                                        img_btn_seven.setRotation(210);
                                                                        img_btn_eight.setRotation(210);

                                                                        final Handler handler = new Handler();
                                                                        handler.postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                img_btn_one.setRotation(240);
                                                                                img_btn_two.setRotation(240);
                                                                                img_btn_three.setRotation(240);
                                                                                img_btn_four.setRotation(240);
                                                                                img_btn_five.setRotation(240);
                                                                                img_btn_six.setRotation(240);
                                                                                img_btn_seven.setRotation(240);
                                                                                img_btn_eight.setRotation(240);

                                                                                final Handler handler = new Handler();
                                                                                handler.postDelayed(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        img_btn_one.setRotation(270);
                                                                                        img_btn_two.setRotation(270);
                                                                                        img_btn_three.setRotation(270);
                                                                                        img_btn_four.setRotation(270);
                                                                                        img_btn_five.setRotation(270);
                                                                                        img_btn_six.setRotation(270);
                                                                                        img_btn_seven.setRotation(270);
                                                                                        img_btn_eight.setRotation(270);

                                                                                        final Handler handler = new Handler();
                                                                                        handler.postDelayed(new Runnable() {
                                                                                            @Override
                                                                                            public void run() {
                                                                                                img_btn_one.setRotation(300);
                                                                                                img_btn_two.setRotation(300);
                                                                                                img_btn_three.setRotation(300);
                                                                                                img_btn_four.setRotation(300);
                                                                                                img_btn_five.setRotation(300);
                                                                                                img_btn_six.setRotation(300);
                                                                                                img_btn_seven.setRotation(300);
                                                                                                img_btn_eight.setRotation(300);

                                                                                                final Handler handler = new Handler();
                                                                                                handler.postDelayed(new Runnable() {
                                                                                                    @Override
                                                                                                    public void run() {
                                                                                                        img_btn_one.setRotation(330);
                                                                                                        img_btn_two.setRotation(330);
                                                                                                        img_btn_three.setRotation(330);
                                                                                                        img_btn_four.setRotation(330);
                                                                                                        img_btn_five.setRotation(330);
                                                                                                        img_btn_six.setRotation(330);
                                                                                                        img_btn_seven.setRotation(330);
                                                                                                        img_btn_eight.setRotation(330);

                                                                                                        final Handler handler = new Handler();
                                                                                                        handler.postDelayed(new Runnable() {
                                                                                                            @Override
                                                                                                            public void run() {
                                                                                                                img_btn_one.setRotation(360);
                                                                                                                img_btn_two.setRotation(360);
                                                                                                                img_btn_three.setRotation(360);
                                                                                                                img_btn_four.setRotation(360);
                                                                                                                img_btn_five.setRotation(360);
                                                                                                                img_btn_six.setRotation(360);
                                                                                                                img_btn_seven.setRotation(360);
                                                                                                                img_btn_eight.setRotation(360);

                                                                                                            }
                                                                                                        }, 50);
                                                                                                    }
                                                                                                }, 50);
                                                                                            }
                                                                                        }, 50);
                                                                                    }
                                                                                }, 50);
                                                                            }
                                                                        }, 50);
                                                                    }
                                                                }, 50);
                                                            }
                                                        }, 50);
                                                    }
                                                }, 50);
                                            }
                                        }, 50);
                                    }
                                }, 50);
                            }
                        }, 50);
                    }
                }, 50);




                /*
                img_btn_one.setRotation(30);
                img_btn_one.setRotation(60);
                img_btn_one.setRotation(90);
                img_btn_one.setRotation(120);
                img_btn_one.setRotation(150);
                img_btn_one.setRotation(180);
                img_btn_one.setRotation(210);
                img_btn_one.setRotation(240);
                img_btn_one.setRotation(270);
                img_btn_one.setRotation(300);
                img_btn_one.setRotation(330);
                img_btn_one.setRotation(360);*/




            }
        });

        return v;
    }

    public void onResume(){
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Social Circle");

    }
}
