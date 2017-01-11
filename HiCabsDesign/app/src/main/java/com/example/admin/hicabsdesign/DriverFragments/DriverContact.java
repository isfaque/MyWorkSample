package com.example.admin.hicabsdesign.DriverFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.hicabsdesign.R;

/**
 * Created by admin on 12/05/2016.
 */
public class DriverContact extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_contact_hicabs, container, false);

        return v;
    }
}
