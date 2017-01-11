package com.example.mascot.socialtree.UserFragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mascot.socialtree.MainActivity;
import com.example.mascot.socialtree.R;

/**
 * Created by Mascot on 7/3/2016.
 */
public class UserTwitter extends Fragment {

    String currentUrl;

    OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(String position);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    final String LOG_TAG = "myLogs";

    private static WebView mWebView;
    private ProgressBar progressBar1;
    private AlertDialog alertbox;

    /*
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:{
                    webViewGoBack();
                }break;
                case 2:{

                    Fragment fr;
                    fr = new UserHome();
                    FragmentManager fm = getFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.main_content, fr);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }break;
            }
        }
    };*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_twitter, container, false);

        MainActivity.isUserHomeShown=false;
        MainActivity.isUserFacebookShown=false;
        MainActivity.isUserFlickrShown=false;
        MainActivity.isUserGooglePlusShown=false;
        MainActivity.isUserLastfmShown=false;
        MainActivity.isUserLinkedInShown=false;
        MainActivity.isUserMyspaceShown=false;
        MainActivity.isUserRedditShown=false;
        MainActivity.isUserTwitterShown=true;

        mCallback.onArticleSelected("Hello World Twitter");

        mWebView = (WebView) v.findViewById(R.id.activity_main_webview);
        progressBar1 = (ProgressBar) v.findViewById(R.id.progressBar1);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://mobile.twitter.com/");
        mWebView.setWebViewClient(new com.example.mascot.socialtree.WebViewClient.WebViewClientTwitter(){
            @Override
            public void onPageFinished(WebView view, String url) {
                //hide loading image
                progressBar1.setVisibility(View.GONE);
                //show webview
                mWebView.setVisibility(View.VISIBLE);

                mCallback.onArticleSelected(url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("when you click on any interlink on webview that time you got url :-" + url);
                mCallback.onArticleSelected(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

        });

       /*
        mWebView.setOnKeyListener(new View.OnKeyListener(){


            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Toast.makeText(getActivity(), "key pressed", Toast.LENGTH_SHORT).show();
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {

                    //Toast.makeText(getActivity(), "only Back pressed", Toast.LENGTH_SHORT).show();

                    if(mWebView.canGoBack()){
                        Toast.makeText(getActivity(), "Back pressed webview", Toast.LENGTH_SHORT).show();
                        handler.sendEmptyMessage(1);
                        return true;

                    }else{
                        Toast.makeText(getActivity(), "Back pressed not", Toast.LENGTH_SHORT).show();
                        handler.sendEmptyMessage(2);
                        return true;

                    }

                }


                return false;
            }

        });*/

        /*mWebView.setWebViewClient(new MyWebViewClient());*/



        return v;

    }

    public void onResume(){
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Twitter");

    }

    /*
    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mCallback.onArticleSelected(url);

            System.out.println("your current url when webpage loading.." + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mCallback.onArticleSelected(url);
            System.out.println("your current url when webpage loading.. finish" + url);
            super.onPageFinished(view, url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onLoadResource(view, url);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            System.out.println("when you click on any interlink on webview that time you got url :-" + url);
            mCallback.onArticleSelected(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }*/

    private void webViewGoBack(){
        mWebView.goBack();
    }


    public static boolean canGoBack(){
        return mWebView.canGoBack();
    }

    public static void goBack(){
        mWebView.goBack();
    }
}
