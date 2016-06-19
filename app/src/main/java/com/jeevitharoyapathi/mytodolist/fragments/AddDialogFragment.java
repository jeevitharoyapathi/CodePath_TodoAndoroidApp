package com.jeevitharoyapathi.mytodolist.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jeevitharoyapathi.mytodolist.R;
import com.jeevitharoyapathi.mytodolist.model.ToDoItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddDialogFragment extends DialogFragment {

    private AddItemListener mAddItemListener;
    private EditText etNotes;
    private Spinner etPriority;
    private EditText etDate;
    private static final String TODO_ITEM = "todoitem";
    private static final String IS_EDIT = "isEdit";
    private static boolean isEdit = false;
    private ToDoItem mToDoItem;
    private final Calendar calendar = Calendar.getInstance();

    public static AddDialogFragment newInstance(ToDoItem item, Boolean isEdit) {
        AddDialogFragment newDialogFragment = new AddDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(TODO_ITEM, item);
        args.putBoolean(IS_EDIT, isEdit);
        newDialogFragment.setArguments(args);
        return newDialogFragment;
    }

    public interface AddItemListener {
        void onToDoItemAdded(ToDoItem newItem);

        void onToDoItemEdited(ToDoItem editItem);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mAddItemListener = (AddItemListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement AddItemListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();
        if (args.getBoolean(IS_EDIT)) {
            mToDoItem = (ToDoItem
                    ) args.getSerializable(TODO_ITEM);
            isEdit = true;
        } else {
            mToDoItem = new ToDoItem();
        }
        LayoutInflater inflater = getActivity().getLayoutInflater();
       final View container = inflater.inflate(R.layout.edit_item, null);
        View view = inflater.inflate(R.layout.dialog_custom_title, null);
        if (isEdit) {
            TextView txtView = (TextView) view.findViewById(R.id.action_bar_title);
            txtView.setText("Edit Item");
        }
        builder.setCustomTitle(view);
        final TextInputLayout txtInputLayout=(TextInputLayout)container.findViewById(R.id.tilTitle);
        final EditText etTitle = txtInputLayout.getEditText();
        etDate = (EditText) container.findViewById(R.id.txtdueDate);
        etNotes = (EditText) container.findViewById(R.id.txtNotes);
        etPriority = (Spinner) container.findViewById(R.id.spPriority);
        builder.setView(container);
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, ToDoItem.PRIORITY_LEVELS);

        etPriority.setAdapter(priorityAdapter);

        etPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mToDoItem.setPriority(new Integer(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setListeners();
        if (isEdit) {
            etTitle.setText(mToDoItem.getTitle());
            etDate.setText(mToDoItem.getDueDate());
            etNotes.setText(mToDoItem.getNotes());
            etPriority.setSelection(mToDoItem.getPriorityAsInterger());
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mToDoItem.setTitle(etTitle.getText().toString());
                mToDoItem.setNotes(etNotes.getText().toString());
                if (etDate.length() != 0) {
                    mToDoItem.setDueDate(getDate(calendar.getTimeInMillis()));
                }
                mToDoItem.setStatus("");
                if (isEdit) {
                    mAddItemListener.onToDoItemEdited(mToDoItem);
                } else {
                    mAddItemListener.onToDoItemAdded(mToDoItem);
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                final Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                etTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            positiveButton.setEnabled(false);
                        } else {
                           positiveButton.setEnabled(true);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        return alertDialog;
    }


    private void setListeners() {
        // A problem without time sets after 1 hour.
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);


        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDate.length() == 0) {
                    etDate.setText(" ");
                }

                DatePickerFragment datePickerFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        etDate.setText(getDate(calendar.getTimeInMillis()).toString());

                    }

                    //Cancels setting date
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etDate.setText(null);
                    }
                };
                //Displays the dialog
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });
    }

    public static String getDate(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);
    }
}
