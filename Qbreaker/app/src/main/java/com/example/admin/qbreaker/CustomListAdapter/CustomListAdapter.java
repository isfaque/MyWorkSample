package com.example.admin.qbreaker.CustomListAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.qbreaker.R;

import java.util.List;

/**
 * Created by Mascot on 6/11/2016.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    //private final String[] limits;
    //private final String[] limits1;
    private final List<String> noticelist_notice_date;
    private final List<String> noticelist_notice_title;
    private final List<String> noticelist_notice_detail;
    // private final Integer[] imgid;

    public CustomListAdapter(Activity context, List<String> noticelist_notice_date, List<String> noticelist_notice_title, List<String> noticelist_notice_detail) {
        super(context, R.layout.mylist, noticelist_notice_date);
        // TODO Auto-generated constructor stub

        this.context=context;
        //this.limits=limits;
        // this.limits1=limits1;
        this.noticelist_notice_date=noticelist_notice_date;
        this.noticelist_notice_title=noticelist_notice_title;
        this.noticelist_notice_detail=noticelist_notice_detail;
        //this.imgid=imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txt_noticelist_notice_date = (TextView) rowView.findViewById(R.id.txt_user_notice_date);
        TextView txt_noticelist_notice_title = (TextView) rowView.findViewById(R.id.txt_user_notice_title);
        TextView txt_noticelist_notice_detail = (TextView) rowView.findViewById(R.id.txt_user_notice_detail);

        txt_noticelist_notice_date.setText(noticelist_notice_date.get(position));
        txt_noticelist_notice_title.setText(noticelist_notice_title.get(position));
        txt_noticelist_notice_detail.setText(noticelist_notice_detail.get(position));

        return rowView;

    };
}
