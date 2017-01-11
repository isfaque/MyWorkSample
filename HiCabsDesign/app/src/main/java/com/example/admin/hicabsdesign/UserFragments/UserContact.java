package com.example.admin.hicabsdesign.UserFragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.hicabsdesign.Manifest;
import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.UserMainActivity;

/**
 * Created by admin on 12/05/2016.
 */
public class UserContact extends Fragment {

    //private static final int PERMISSION_REQUEST_CODES = 1;
    //private View view;

    TextView txt_hicabs_contact_tel;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_contact_hicabs, container, false);

        txt_hicabs_contact_tel = (TextView) v.findViewById(R.id.txt_hicabs_contact_tel);

        txt_hicabs_contact_tel.setAutoLinkMask(Linkify.ALL);
        txt_hicabs_contact_tel.setPaintFlags(txt_hicabs_contact_tel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        txt_hicabs_contact_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                try{
                    String number = ("tel:" + "+35621372137");
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(number));
                    startActivity(intent);
                }

                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getActivity(),"Calling permission denial or Not registered on Network",Toast.LENGTH_SHORT).show();
                }*/

                try {
                    String number = ("tel:" + "+35621372137");
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(number));
                    startActivity(intent);
                } catch(Exception e) {
                    Toast.makeText(getActivity(),"Calling permission denial or Not registered on Network",Toast.LENGTH_SHORT).show();

                    if (!checkPermission()) {

                        requestPermission();

                    }
                }



            }
        });

        return v;
    }



    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private void requestPermission(){

        if (shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)){

            Toast.makeText(getActivity(),"Calling permission allows us to Call. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        }
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODES:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getActivity(),"Permission Granted, Now you can Call.",Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getActivity(),"Permission Denied, You cannot Call.",Toast.LENGTH_LONG).show();

                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }*/



}
