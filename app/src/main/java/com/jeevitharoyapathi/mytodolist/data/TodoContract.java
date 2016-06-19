package com.jeevitharoyapathi.mytodolist.data;

import android.provider.BaseColumns;

/**
 * Created by jeevitha.royapathi on 6/4/16.
 */
public class TodoContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public TodoContract() {}

    /* Inner class that defines the table contents */
    public static abstract class ToDoList implements BaseColumns {
        public static final String TABLE_NAME = "toDoList";
        public static final String COLUMN_NAME_ENTRY_ID = "ID";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NOTES = "notes";
        public static final String COLUMN_PRIORITY ="priority";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_DUE_DATE = "duedate";
    }
}
