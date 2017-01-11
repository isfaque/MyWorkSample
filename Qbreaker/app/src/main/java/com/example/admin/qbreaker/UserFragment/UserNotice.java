package com.example.admin.qbreaker.UserFragment;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.qbreaker.CustomListAdapter.CustomListAdapter;
import com.example.admin.qbreaker.LoginActivity;
import com.example.admin.qbreaker.MainActivity;
import com.example.admin.qbreaker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 09/06/2016.
 */
public class UserNotice extends Fragment {

    ListView list;
    private AlertDialog alertbox;

    List<String> noticelist_notice_date = new ArrayList<String>();
    List<String> noticelist_notice_title = new ArrayList<String>();
    List<String> noticelist_notice_detail = new ArrayList<String>();

    public static String user_notice_date, user_notice_title, user_notice_detail;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notice, container, false);

        MainActivity.isUserAboutShown=false;
        MainActivity.isUserContactShown=false;
        MainActivity.isUserNoticeShown=true;
        MainActivity.isUserNoticeDetailShown=false;

        noticelist_notice_date.clear();
        noticelist_notice_title.clear();
        noticelist_notice_detail.clear();

        list=(ListView) v.findViewById(R.id.list);

        String[] noticelist_date = getResources().getStringArray(R.array.noticelist_date);
        String[] noticelist_title = getResources().getStringArray(R.array.noticelist_title);
        String[] noticelist_detail = getResources().getStringArray(R.array.noticelist_detail);

        for(int i=0;i<noticelist_date.length;i++){

            String date = noticelist_date[i];
            String title = noticelist_title[i];
            String detail = noticelist_detail[i];
            noticelist_notice_date.add(date);
            noticelist_notice_title.add(title);
            noticelist_notice_detail.add(detail);
        }

        CustomListAdapter adapter=new CustomListAdapter(getActivity(), noticelist_notice_date, noticelist_notice_title, noticelist_notice_detail);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem1= noticelist_notice_date.get(position);


                user_notice_date = noticelist_notice_date.get(position);
                user_notice_title = noticelist_notice_title.get(position);
                user_notice_detail = noticelist_notice_detail.get(position);

                Fragment fr;
                fr = new UserNoticeDetail();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();





            }
        });

        return v;
    }

}
