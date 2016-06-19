package com.jeevitharoyapathi.mytodolist.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeevitharoyapathi.mytodolist.R;
import com.jeevitharoyapathi.mytodolist.model.ToDoItem;

public class ToDoDetails_Fragment extends Fragment {

    private ToDoItem mToDoItem;
    public static String TODO_ITEM = "todoItem";
    private TextView etTitle;
    private TextView etDescription;
    private TextView etDueDate;
    private TextView etPriority;
    private MenuItemClickListener mMenuItemClickListener;

    public ToDoDetails_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ToDoDetails_Fragment newInstance(ToDoItem toDoItem) {
        ToDoDetails_Fragment fragment = new ToDoDetails_Fragment();
        Bundle args = new Bundle();
        args.putSerializable(TODO_ITEM, toDoItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mToDoItem = (ToDoItem) getArguments().getSerializable(TODO_ITEM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_edit:
                android.app.DialogFragment editDialogFragment = com.jeevitharoyapathi.mytodolist.fragments.AddDialogFragment.newInstance(mToDoItem,true);
                editDialogFragment.show(getActivity().getFragmentManager().beginTransaction(), "Edit Item");
                return false;

            case R.id.menu_delete:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                mMenuItemClickListener.onDeleteIconClick(mToDoItem.getID());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return false;

            default:
                break;
        }

        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.todo_details_menu, menu);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMenuItemClickListener=(MenuItemClickListener)activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_to_do_detail, container, false);
        etTitle = (TextView) rootView.findViewById(R.id.etTitle);
        etDescription = (TextView) rootView.findViewById(R.id.txtNotes);
        etDueDate = (TextView) rootView.findViewById(R.id.txtdueDate);
        etPriority = (TextView) rootView.findViewById(R.id.spPriority);
        etTitle.setText(mToDoItem.getTitle());
        etDescription.setText(mToDoItem.getNotes());
        etDueDate.setText(mToDoItem.getDueDate());
        etPriority.setText(mToDoItem.getPriorityAsValue());
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public  interface MenuItemClickListener
    {
        void onDeleteIconClick(Integer itemID);
    }

}
