package com.qoobico.remindme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.qoobico.remindme.GoogleDriveActivity;
import com.qoobico.remindme.R;

public class DriveFragment extends Fragment {
    private static final int LAYOUT = R.layout.drive_fragment;

    private View view;

    public static DriveFragment getInstance() {
        Bundle args = new Bundle();
        DriveFragment fragment = new DriveFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        FloatingActionButton findMe = (FloatingActionButton) view.findViewById(R.id.fabGoogleDrive);

        findMe.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Snackbar.make(view, getString(R.string.lounch_camera), Snackbar.LENGTH_LONG)
                        .setAction(R.string.lounch, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getActivity(), GoogleDriveActivity.class));
                            }
                        }).show();

            }
        });
        return view;
    }
}
