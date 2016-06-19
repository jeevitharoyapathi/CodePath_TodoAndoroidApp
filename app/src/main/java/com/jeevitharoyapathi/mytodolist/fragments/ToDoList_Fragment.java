package com.jeevitharoyapathi.mytodolist.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeevitharoyapathi.mytodolist.R;
import com.jeevitharoyapathi.mytodolist.adapters.TodoListAdapter;
import com.jeevitharoyapathi.mytodolist.data.ToDoItemsDbHelper;
import com.jeevitharoyapathi.mytodolist.model.ToDoItem;

import java.util.List;


public class ToDoList_Fragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TodoListAdapter mTodoListAdapter;
    private TodoListAdapter.OnItemClickListener mListener;
    private FloatingActionButton mFloatingActionButton;
    private ToDoItemsDbHelper mToDoItemsDbHelper;
    private List<ToDoItem> mToDoItemList;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ToDoList_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToDoList_Fragment newInstance() {
        ToDoList_Fragment fragment = new ToDoList_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ToDoList_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //retain this fragment , so it won't be destroyed when the configuration changes
        setRetainInstance(true);
        mToDoItemsDbHelper = ToDoItemsDbHelper.getInstance(getActivity().getApplicationContext());
        mToDoItemList = mToDoItemsDbHelper.getAllItems();
        mTodoListAdapter = new TodoListAdapter(getActivity(), mToDoItemList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayoutManager mLayoutMgr;

        View rootView = inflater.inflate(R.layout.fragment_todo_list_, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.toDoList);
        mLayoutMgr = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutMgr);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.add_fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.DialogFragment AddDialogFragment = com.jeevitharoyapathi.mytodolist.fragments.AddDialogFragment.newInstance(new ToDoItem(),false);
                AddDialogFragment.show(getActivity().getFragmentManager().beginTransaction(), "Add Item");
            }
        });
        mTodoListAdapter.setOnClickListener(mListener);
        mRecyclerView.setAdapter(mTodoListAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener=(TodoListAdapter.OnItemClickListener)activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void addItem(ToDoItem newItem) {
        mToDoItemsDbHelper.insertItem(newItem);
        mToDoItemList = mToDoItemsDbHelper.getAllItems();
        mTodoListAdapter.setDataSet(mToDoItemList);
        mTodoListAdapter.notifyDataSetChanged();
    }
}
