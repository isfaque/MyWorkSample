package com.example.admin.jerne.UserFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.jerne.CustomListAdapter.DataObject;
import com.example.admin.jerne.CustomListAdapter.MyRecyclerViewAdapter;
import com.example.admin.jerne.R;

import java.util.ArrayList;

/**
 * Created by admin on 30/06/2016.
 */
public class UserHomeDeals extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";

    String[] good_deals_title, good_deals_price;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_good_deals, container, false);

        v.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);



        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
                String myposition = String.valueOf(position);
                String mydata = good_deals_title[position];
                Toast.makeText(getActivity(), mydata, Toast.LENGTH_SHORT).show();
            }
        });
    }



    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        good_deals_price = getResources().getStringArray(R.array.noticelist_date);
        good_deals_title = getResources().getStringArray(R.array.noticelist_title);
        for (int index = 0; index < good_deals_price.length; index++) {
            //DataObject obj = new DataObject("Some Primary Text " + index, "Secondary " + index);
            DataObject obj = new DataObject(good_deals_title[index], good_deals_price[index]);
            results.add(index, obj);
        }
        return results;
    }
}
