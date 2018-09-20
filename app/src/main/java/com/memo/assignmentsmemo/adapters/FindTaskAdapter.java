package com.memo.assignmentsmemo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.memo.assignmentsmemo.R;
import com.memo.assignmentsmemo.modelclass.TaskAttributes;

import java.util.ArrayList;
import java.util.List;

public class FindTaskAdapter extends RecyclerView.Adapter<FindTaskAdapter.TaskViewHolder> {

    public Context context;

    public static ArrayList<TaskAttributes> mTaskAtr;

    public FindTaskAdapter() {
    }

    public FindTaskAdapter(Context context, ArrayList<TaskAttributes> taskAttributesList) {
        this.context = context;
        mTaskAtr=taskAttributesList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.find_task_layout, viewGroup, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {

        TaskAttributes taskAttributes = mTaskAtr.get(i);

        taskViewHolder.name.setText(taskAttributes.getName());
        taskViewHolder.email.setText(taskAttributes.getEmail());
        taskViewHolder.status.setText(taskAttributes.getTaskStatus());
        taskViewHolder.sirealNo.setText(String.valueOf(i+1));

        taskViewHolder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mTaskAtr.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView email;
        public TextView name,status,sirealNo;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.emailView);
            name = itemView.findViewById(R.id.name_view);
            status = itemView.findViewById(R.id.status);
            sirealNo = itemView.findViewById(R.id.sirealNo);
        }
    }
}
