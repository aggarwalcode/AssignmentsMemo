package com.memo.assignmentsmemo.activities;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.memo.assignmentsmemo.R;
import com.memo.assignmentsmemo.adapters.SectionsPagerAdapter;
import com.memo.assignmentsmemo.fragments.FragInProgressTaskTab;
import com.memo.assignmentsmemo.fragments.FragCompletedTaskTab;
import com.memo.assignmentsmemo.fragments.FragPendingTaskTab;
import com.memo.assignmentsmemo.fragments.TaskDetails;
import com.memo.assignmentsmemo.modelclass.TaskAttributes;

import java.util.ArrayList;

public class AllTaskActivity extends AppCompatActivity implements FragInProgressTaskTab.OnFragmentInteractionListener,
        FragPendingTaskTab.OnFragmentInteractionListener,
        FragCompletedTaskTab.OnFragmentInteractionListener,TaskDetails.OnFragmentInteractionListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    DatabaseReference databaseReference;
    TaskAttributes taskAttributes;
    ArrayList<TaskAttributes> taskAttributesList = new ArrayList<>();
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task_tabs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.vPcontainer);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        fetchData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void fetchData() {

        databaseReference = FirebaseDatabase.getInstance().getReference("tasks");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskAttributesList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    taskAttributes = postSnapshot.getValue(TaskAttributes.class);
                    taskAttributesList.add(taskAttributes);
                }
                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),taskAttributesList);
                mViewPager.setAdapter(mSectionsPagerAdapter);
                mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_task_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
