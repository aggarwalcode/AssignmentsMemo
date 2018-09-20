package com.memo.assignmentsmemo.activities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.memo.assignmentsmemo.R;
import com.memo.assignmentsmemo.adapters.FindTaskAdapter;
import com.memo.assignmentsmemo.fragments.TaskDetails;
import com.memo.assignmentsmemo.helperclass.RecyclerItemClickListener;
import com.memo.assignmentsmemo.modelclass.TaskAttributes;


import java.util.ArrayList;
import java.util.List;

public class  FindTasks extends AppCompatActivity implements TaskDetails.OnFragmentInteractionListener{

    RecyclerView rvFindTask;
    FindTaskAdapter mAdapter;
    DatabaseReference databaseReference;
    ArrayList<TaskAttributes> taskAttributesList = new ArrayList<>();
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tasks);

        rvFindTask = findViewById(R.id.rvFindTask);
        rvFindTask.setLayoutManager(new LinearLayoutManager(this));
        rvFindTask.addItemDecoration(new DividerItemDecoration(rvFindTask.getContext(), DividerItemDecoration.VERTICAL));

        databaseReference = FirebaseDatabase.getInstance().getReference("tasks");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskAttributesList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    TaskAttributes taskAttributes = postSnapshot.getValue(TaskAttributes.class);
                    taskAttributesList.add(taskAttributes);
                    mAdapter = new FindTaskAdapter(getApplicationContext(),taskAttributesList);
                    rvFindTask.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        rvFindTask.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), rvFindTask ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        TaskAttributes mTaskAtr = FindTaskAdapter.mTaskAtr.get(position);
                        Fragment fragment = TaskDetails.newInstance(mTaskAtr);
                        transaction.add(R.id.container,fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
