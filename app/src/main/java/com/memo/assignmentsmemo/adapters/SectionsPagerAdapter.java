package com.memo.assignmentsmemo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.memo.assignmentsmemo.fragments.FragInProgressTaskTab;
import com.memo.assignmentsmemo.fragments.FragCompletedTaskTab;
import com.memo.assignmentsmemo.fragments.FragPendingTaskTab;
import com.memo.assignmentsmemo.modelclass.TaskAttributes;

import java.util.ArrayList;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    ArrayList<TaskAttributes> taskAttributesList;

    public SectionsPagerAdapter(FragmentManager fm, ArrayList<TaskAttributes> taskAttributesList) {
        super(fm);
        this.taskAttributesList = taskAttributesList;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 2:
                FragCompletedTaskTab fragCompletedTaskTab = new FragCompletedTaskTab().newInstance(position+1,taskAttributesList);
                return fragCompletedTaskTab;
            case 1:
                FragPendingTaskTab fragPendingTaskTab = new FragPendingTaskTab().newInstance(position+1,taskAttributesList);
                return fragPendingTaskTab;
            case 0:
                FragInProgressTaskTab fragInProgressTaskTab = new FragInProgressTaskTab().newInstance(position+1,taskAttributesList);
                return fragInProgressTaskTab;
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}