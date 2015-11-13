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
import com.qoobico.remindme.NameActivity;
import com.qoobico.remindme.R;

public class ChatFragment extends Fragment {
    private static final int LAYOUT = R.layout.chat_fragment;

    private View view;

    public static ChatFragment getInstance() {
        Bundle args = new Bundle();
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        FloatingActionButton findMe = (FloatingActionButton) view.findViewById(R.id.fabChat);

        findMe.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Snackbar.make(view, getString(R.string.enter_chat), Snackbar.LENGTH_LONG)
                    .setAction(R.string.enter, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(), NameActivity.class));
                        }
                    }).show();

                        }
        });
        return view;
    }
}
