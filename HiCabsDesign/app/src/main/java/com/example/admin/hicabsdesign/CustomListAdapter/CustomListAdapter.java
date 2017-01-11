package com.example.admin.hicabsdesign.CustomListAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.hicabsdesign.R;

import java.util.List;

/**
 * Created by admin on 25/05/2016.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    //private final String[] limits;
    //private final String[] limits1;
    private final List<String> bookinglist_bookingid;
    private final List<String> bookinglist_dbookingid;
    private final List<String> bookinglist_bookingstatus;
    private final List<String> bookinglist_pickupcity;
    private final List<String> bookinglist_dropcity;
    private final List<String> bookinglist_bookingdate;
    private final List<String> bookinglist_bookingtime;
    private final List<String> bookinglist_bookingprice;
    // private final Integer[] imgid;

    public CustomListAdapter(Activity context, List<String> bookinglist_bookingid, List<String> bookinglist_dbookingid, List<String> bookinglist_bookingstatus,List<String> bookinglist_pickupcity, List<String> bookinglist_dropcity, List<String> bookinglist_bookingdate, List<String> bookinglist_bookingtime, List<String> bookinglist_bookingprice) {
        super(context, R.layout.mylist, bookinglist_bookingid);
        // TODO Auto-generated constructor stub

        this.context=context;
        //this.limits=limits;
        // this.limits1=limits1;
        this.bookinglist_bookingid=bookinglist_bookingid;
        this.bookinglist_dbookingid=bookinglist_dbookingid;
        this.bookinglist_bookingstatus=bookinglist_bookingstatus;
        this.bookinglist_pickupcity=bookinglist_pickupcity;
        this.bookinglist_dropcity=bookinglist_dropcity;
        this.bookinglist_bookingdate=bookinglist_bookingdate;
        this.bookinglist_bookingtime=bookinglist_bookingtime;
        this.bookinglist_bookingprice=bookinglist_bookingprice;
        //this.imgid=imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txt_bookinglist_bookingid = (TextView) rowView.findViewById(R.id.txt_user_bookinglist_bookingid);
        TextView txt_bookinglist_bookingstatus = (TextView) rowView.findViewById(R.id.txt_user_bookinglist_bookingstatus);
        TextView txt_bookinglist_pickupcity = (TextView) rowView.findViewById(R.id.txt_user_bookinglist_pickupcity);
        TextView txt_bookinglist_dropcity = (TextView) rowView.findViewById(R.id.txt_user_bookinglist_dropcity);
        TextView txt_bookinglist_bookingdate = (TextView) rowView.findViewById(R.id.txt_user_bookinglist_bookingdate);
        TextView txt_bookinglist_bookingtime = (TextView) rowView.findViewById(R.id.txt_user_bookinglist_bookingtime);
        TextView txt_bookinglist_bookingprice = (TextView) rowView.findViewById(R.id.txt_user_bookinglist_bookingprice);

        txt_bookinglist_bookingid.setText("Booking ID: "+bookinglist_dbookingid.get(position));
        txt_bookinglist_bookingstatus.setText(bookinglist_bookingstatus.get(position));
        txt_bookinglist_pickupcity.setText("Pickup City: "+bookinglist_pickupcity.get(position));
        txt_bookinglist_dropcity.setText("Drop City: "+bookinglist_dropcity.get(position));
        txt_bookinglist_bookingdate.setText("Booking Date: "+bookinglist_bookingdate.get(position));
        txt_bookinglist_bookingtime.setText("Booking Time: "+bookinglist_bookingtime.get(position));
        txt_bookinglist_bookingprice.setText("€"+bookinglist_bookingprice.get(position));

        return rowView;

    };
}
