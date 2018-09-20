package com.memo.assignmentsmemo.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.memo.assignmentsmemo.R;
import com.memo.assignmentsmemo.activities.EditTask;
import com.memo.assignmentsmemo.modelclass.TaskAttributes;

import java.io.Externalizable;
import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final TaskAttributes TASK_ATTRIBUTES = null;


    // TODO: Rename and change types of parameters
    public TaskAttributes taskAtb;

    private OnFragmentInteractionListener mListener;

    public TaskDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TaskDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskDetails newInstance(TaskAttributes taskAttributes) {
        TaskDetails fragment = new TaskDetails();
        Bundle args = new Bundle();
        args.putSerializable(String.valueOf(TASK_ATTRIBUTES), taskAttributes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskAtb = (TaskAttributes) getArguments().getSerializable(String.valueOf(TASK_ATTRIBUTES));
            //mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_details, container, false);

        TextView emailView, nameView,orderIdView,countryView,taskStatus,
                currencyView,amountView,wordCountView,invoiceAmtView,
                dateView,fBaseIdVIew,txn_id_td,remarks;

        Button updateTask;

        emailView = view.findViewById(R.id.email);
        nameView = view.findViewById(R.id.name);
        orderIdView = view.findViewById(R.id.orderId);
        countryView = view.findViewById(R.id.country);
        currencyView = view.findViewById(R.id.currencySpinner);
        amountView = view.findViewById(R.id.amount);
        wordCountView = view.findViewById(R.id.wordCount);
        invoiceAmtView = view.findViewById(R.id.invoiceAmt);
        dateView = view.findViewById(R.id.date_created);
        fBaseIdVIew = view.findViewById(R.id.fBaseId);
        taskStatus = view.findViewById(R.id.taskStatus);
        updateTask = view.findViewById(R.id.updateTask);
        txn_id_td = view.findViewById(R.id.txn_id_td);
        remarks = view.findViewById(R.id.remarks_td);

        emailView.setText(taskAtb.getEmail());
        nameView.setText(taskAtb.getName());
        orderIdView.setText(taskAtb.getOrderID());
        countryView.setText(taskAtb.getCountry());
        currencyView.setText(taskAtb.getCurrency());
        amountView.setText (String.valueOf(taskAtb.getAmount()));
        wordCountView.setText(String.valueOf(taskAtb.getWordCount()));
        invoiceAmtView.setText(String.valueOf(taskAtb.getInvoiceAmt()));
        dateView.setText(taskAtb.getTimestamp());
        fBaseIdVIew.setText(taskAtb.getfBaseId());
        taskStatus.setText(taskAtb.getTaskStatus());
        txn_id_td.setText(taskAtb.getTxn_id());
        remarks.setText(taskAtb.getRemarks());

        updateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String FBASE_ID = "fbase_id";
                Intent intent = new Intent(TaskDetails.this.getActivity(), EditTask.class);
                intent.putExtra("FBASE_ID", taskAtb.getfBaseId());
                startActivity(intent);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
