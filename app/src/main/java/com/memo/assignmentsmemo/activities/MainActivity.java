package com.memo.assignmentsmemo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.memo.assignmentsmemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addTask(View view) {
        Intent intent = new Intent(this,CreateTask.class);
        startActivity(intent);
    }

    public void findTask(View view) {
        Intent intent = new Intent(this,FindTasks.class);
        startActivity(intent);
    }

    public void tasksCompleted(View view) {
        Intent intent = new Intent(this,AllTaskActivity.class);
        startActivity(intent);
    }
}
