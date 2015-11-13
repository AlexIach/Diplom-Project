package com.qoobico.remindme.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qoobico.remindme.R;

public class SightsFragment extends Fragment {
    private static final int LAYOUT = R.layout.sights_fragment;

    private View view;

    public static SightsFragment getInstance() {
        Bundle args = new Bundle();
        SightsFragment fragment = new SightsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }
}
