package com.memo.assignmentsmemo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.memo.assignmentsmemo.R;
import com.memo.assignmentsmemo.adapters.FindTaskAdapter;
import com.memo.assignmentsmemo.helperclass.RecyclerItemClickListener;
import com.memo.assignmentsmemo.modelclass.TaskAttributes;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragInProgressTaskTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragInProgressTaskTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragInProgressTaskTab extends Fragment implements TaskDetails.OnFragmentInteractionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static ArrayList<TaskAttributes> TASK_ATTRIBUTES_LIST = new ArrayList<TaskAttributes>();
    boolean mUserVisibleHint = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<TaskAttributes> taskAttributesList = new ArrayList<>();

    RecyclerView rvAllTask;
    FindTaskAdapter mAdapter;
    DatabaseReference databaseReference1;
    private ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    public FragInProgressTaskTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment FragInProgressTaskTab.
     */
    // TODO: Rename and change types and number of parameters
    public static FragInProgressTaskTab newInstance(int sectionNumber, ArrayList<TaskAttributes>taskAttributesList) {
        FragInProgressTaskTab fragment = new FragInProgressTaskTab();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable(String.valueOf(TASK_ATTRIBUTES_LIST),taskAttributesList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserVisibleHint(false);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            taskAttributesList = (ArrayList<TaskAttributes>) getArguments().getSerializable(String.valueOf(TASK_ATTRIBUTES_LIST));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab_all_task, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        rvAllTask = rootView.findViewById(R.id.rvAllTask);
        rvAllTask.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAllTask.addItemDecoration(new DividerItemDecoration(rvAllTask.getContext(), DividerItemDecoration.VERTICAL));
        fetchData();
        return rootView;
    }
//

    private void fetchData() {

        ArrayList<TaskAttributes> taskAttributesSorted = new ArrayList<>();

        for (TaskAttributes taskAttributesListCom:taskAttributesList) {
            try {
                if (taskAttributesListCom.getTaskStatus().toLowerCase().equals("received")) {
                } else if (taskAttributesListCom.getTaskStatus().toLowerCase().equals("confirmed")) {
                } else if (taskAttributesListCom.getTaskStatus().toLowerCase().equals("delivered")) {
                } else taskAttributesSorted.add(taskAttributesListCom);
            }catch (Exception e){}
        }

        mAdapter = new FindTaskAdapter(getContext(),taskAttributesSorted);
        rvAllTask.setAdapter(mAdapter);

        rvAllTask.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), rvAllTask ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        FragmentManager manager = getChildFragmentManager();
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

    @Override
    public void onFragmentInteraction(Uri uri) {

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
