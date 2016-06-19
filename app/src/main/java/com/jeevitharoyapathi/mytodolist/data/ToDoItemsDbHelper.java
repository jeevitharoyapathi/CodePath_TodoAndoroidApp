/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jeevitharoyapathi.mytodolist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jeevitharoyapathi.mytodolist.data.TodoContract.ToDoList;
import com.jeevitharoyapathi.mytodolist.model.ToDoItem;

import java.util.ArrayList;
import java.util.List;

public class ToDoItemsDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static ToDoItemsDbHelper mInstance;
    static final String DATABASE_NAME = "ToDoApp.db";

    public ToDoItemsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized ToDoItemsDbHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ToDoItemsDbHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TODO_TABLE = "CREATE TABLE " + ToDoList.TABLE_NAME + " (" +
                ToDoList._ID + " INTEGER PRIMARY KEY," +
                ToDoList.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                ToDoList.COLUMN_NOTES + " TEXT, " +
                ToDoList.COLUMN_PRIORITY + " INTEGER, " +
                ToDoList.COLUMN_DUE_DATE + " TEXT, " +
                ToDoList.COLUMN_STATUS + " INTEGER" +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_TODO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ToDoList.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertItem(ToDoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", item.getTitle());
        contentValues.put("notes", item.getNotes());
        contentValues.put("priority", item.getPriority());
        contentValues.put("duedate", item.getDueDate());


        db.insert("toDoList", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from toDoList where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ToDoList.TABLE_NAME);
        return numRows;
    }

    public boolean updateItem(ToDoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", item.getTitle());
        contentValues.put("notes", item.getNotes());
        contentValues.put("priority", item.getPriority());
        contentValues.put("status", item.getStatus());
        contentValues.put("duedate", item.getDueDate());
        db.update("toDoList", contentValues, ToDoList._ID + " = ? ", new String[]{Integer.toString(item.getID())});
        return true;
    }

    public Integer deleteItem(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("toDoList",
                ToDoList._ID + " = ? ",
                new String[]{Integer.toString(id)});
    }

    public List<ToDoItem> getAllItems() {
        List<ToDoItem> toDoItems = new ArrayList<ToDoItem>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from toDoList", null);
        if (res != null && res.moveToFirst()) {
            do {
                ToDoItem toDoItem = new ToDoItem();
                toDoItem.setID(res.getInt(res.getColumnIndex(ToDoList._ID)));
                toDoItem.setTitle(res.getString(res.getColumnIndex(ToDoList.COLUMN_NAME_TITLE)));
                toDoItem.setNotes(res.getString(res.getColumnIndex(ToDoList.COLUMN_NOTES)));
                toDoItem.setPriority(res.getString(res.getColumnIndex(ToDoList.COLUMN_PRIORITY)));
                toDoItem.setStatus(res.getString(res.getColumnIndex(ToDoList.COLUMN_STATUS)));
                toDoItem.setDueDate(res.getString(res.getColumnIndex(ToDoList.COLUMN_DUE_DATE)));

                toDoItems.add(toDoItem);
            } while (res.moveToNext());
        }
        return toDoItems;
    }
}
