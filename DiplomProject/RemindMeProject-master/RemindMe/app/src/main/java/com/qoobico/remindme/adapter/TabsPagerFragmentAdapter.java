package com.qoobico.remindme.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qoobico.remindme.R;
import com.qoobico.remindme.fragment.ChatFragment;
import com.qoobico.remindme.fragment.DriveFragment;
import com.qoobico.remindme.fragment.FindFragment;
import com.qoobico.remindme.fragment.SightsFragment;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] tabs;
    Context mContext;

    public TabsPagerFragmentAdapter(FragmentManager fm,Context context) {

        super(fm);
        mContext = context;
        tabs = new String[] {
                context.getResources().getString(R.string.tool_bar_item_find_me),
                context.getResources().getString(R.string.tool_bar_item_sights),
                context.getResources().getString(R.string.tool_bar_item_chat),
                context.getResources().getString(R.string.tool_bar_item_drive)
        };
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FindFragment.getInstance();

            case 1:
                return SightsFragment.getInstance();

            case 2:
                return ChatFragment.getInstance();
            case 3:
                return DriveFragment.getInstance();
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
