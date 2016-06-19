package com.jeevitharoyapathi.mytodolist.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jeevitharoyapathi.mytodolist.R;
import com.jeevitharoyapathi.mytodolist.adapters.TodoListAdapter;
import com.jeevitharoyapathi.mytodolist.data.ToDoItemsDbHelper;
import com.jeevitharoyapathi.mytodolist.fragments.AddDialogFragment;
import com.jeevitharoyapathi.mytodolist.fragments.ToDoDetails_Fragment;
import com.jeevitharoyapathi.mytodolist.fragments.ToDoList_Fragment;
import com.jeevitharoyapathi.mytodolist.model.ToDoItem;

public class Main_Activity extends AppCompatActivity implements AddDialogFragment.AddItemListener, TodoListAdapter.OnItemClickListener, ToDoDetails_Fragment.MenuItemClickListener {

    protected ToDoItemsDbHelper mToDoItemsDbHelper;
    private ToDoList_Fragment newFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToDoItemsDbHelper = ToDoItemsDbHelper.getInstance(getApplicationContext());
        if (savedInstanceState == null) {
            newFragment = ToDoList_Fragment.newInstance();
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, newFragment, "toDoList").commit();
        }
        //Setting ToolBar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("My ToDo List");
        setSupportActionBar(mToolbar);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    public ToDoItemsDbHelper getToDoItemsDbHelper() {
        if (mToDoItemsDbHelper == null) {
            mToDoItemsDbHelper = ToDoItemsDbHelper.getInstance(getApplicationContext());
        }
        return mToDoItemsDbHelper;
    }

    @Override
    public void onToDoItemAdded(ToDoItem newItem) {
        newFragment.addItem(newItem);
    }

    @Override
    public void onToDoItemEdited(ToDoItem editItem) {
        getToDoItemsDbHelper().updateItem(editItem);
        ToDoDetails_Fragment toDoDetails_fragment = ToDoDetails_Fragment.newInstance(editItem);
        getFragmentManager().beginTransaction().addToBackStack(null).
                replace(R.id.container, toDoDetails_fragment, "toDoDetails")
                .commit();
    }

    @Override
    public void onItemClick(ToDoItem item) {
        ToDoDetails_Fragment toDoDetails_fragment = ToDoDetails_Fragment.newInstance(item);
        getFragmentManager().beginTransaction().addToBackStack(null).
                replace(R.id.container, toDoDetails_fragment, "toDoDetails")
                .commit();
    }

    @Override
    public void onDeleteIconClick(Integer itemId) {
        getToDoItemsDbHelper().deleteItem(itemId);
        newFragment = ToDoList_Fragment.newInstance();
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, newFragment, "toDoList").commit();

    }

}
