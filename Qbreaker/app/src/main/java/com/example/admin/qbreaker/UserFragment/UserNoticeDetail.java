package com.example.admin.qbreaker.UserFragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.qbreaker.LoginActivity;
import com.example.admin.qbreaker.MainActivity;
import com.example.admin.qbreaker.R;

/**
 * Created by Mascot on 6/12/2016.
 */
public class UserNoticeDetail extends Fragment {

    Button btn_user_notice_date, btn_notice_notification;
    TextView txt_user_notice_title, txt_user_notice_detail;

    public static String user_noticedetail_date, user_noticedetail_title, user_noticedetail_detail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notice_detail, container, false);

        MainActivity.isUserAboutShown=false;
        MainActivity.isUserContactShown=false;
        MainActivity.isUserNoticeShown=false;
        MainActivity.isUserNoticeDetailShown=true;

        btn_user_notice_date = (Button) v.findViewById(R.id.btn_user_noticedetail_date);
        txt_user_notice_title = (TextView) v.findViewById(R.id.txt_user_noticedetail_title);
        txt_user_notice_detail = (TextView) v.findViewById(R.id.txt_user_noticedetail_detail);

        btn_notice_notification = (Button) v.findViewById(R.id.btn_notice_notification);

        user_noticedetail_date = UserNotice.user_notice_date;
        user_noticedetail_title = UserNotice.user_notice_title;
        user_noticedetail_detail = UserNotice.user_notice_detail;


        btn_user_notice_date.setText("Date: "+user_noticedetail_date);
        txt_user_notice_title.setText(user_noticedetail_title);
        txt_user_notice_detail.setText(user_noticedetail_detail);


        btn_notice_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mydetail = user_noticedetail_detail;
                String mytitle = user_noticedetail_title;
                ((MainActivity)getActivity()).sendMyBigTextStyleNotification(mydetail, mytitle);
            }
        });


        return v;
    }

}
