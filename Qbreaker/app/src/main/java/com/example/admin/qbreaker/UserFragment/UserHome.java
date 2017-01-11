package com.example.admin.qbreaker.UserFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.admin.qbreaker.LoginActivity;
import com.example.admin.qbreaker.R;

/**
 * Created by admin on 09/06/2016.
 */
public class UserHome extends Fragment {

    private WebView mWebView;
    private ProgressBar progressBar1;
    private AlertDialog alertbox;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:{
                    webViewGoBack();
                }break;
                case 2:{

                    alertbox = new AlertDialog.Builder(getActivity()).setMessage("Do you want to exit application?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("Exit me", true);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {
                                }
                            })
                            .show();

                }break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_web, container, false);

        mWebView = (WebView) v.findViewById(R.id.activity_main_webview);
        progressBar1 = (ProgressBar) v.findViewById(R.id.progressBar1);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("http://demo.qbreaker.com/");
        mWebView.setWebViewClient(new com.example.admin.qbreaker.WebViewClient.MyAppWebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                //hide loading image
                progressBar1.setVisibility(View.GONE);
                //show webview
                mWebView.setVisibility(View.VISIBLE);
            }});

        mWebView.setOnKeyListener(new View.OnKeyListener(){

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {

                    if(mWebView.canGoBack()){
                        handler.sendEmptyMessage(1);
                        return true;

                    }else{

                        handler.sendEmptyMessage(2);
                        return true;

                    }
                }
                return false;
            }

        });

        return v;

    }

    private void webViewGoBack(){
        mWebView.goBack();
    }


}
