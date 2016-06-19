package com.jeevitharoyapathi.mytodolist.model;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by jeevitha.royapathi on 6/5/16.
 */
public class ToDoItem implements Serializable {
    private Integer mID;
    private String mTitle;
    private String mNotes;
    private String mStatus;
    private String mPriority;
    private String mDueDate;
    public static final String[] PRIORITY_LEVELS = {"Low Priority", "Normal priority", "High priority"};

    public void setID(Integer ID) {
        mID = ID;
    }

    public Integer getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getPriority() {
        return mPriority;
    }

    public void setPriority(String priority) {
        mPriority = priority;
    }

    public String getDueDate() {
        return mDueDate;
    }

    public void setDueDate(String dueDate) {
        mDueDate = dueDate;
    }

    public String getPriorityAsValue()
    {
        Integer index=Integer.parseInt(mPriority);
        if(index<PRIORITY_LEVELS.length)
        {
            return PRIORITY_LEVELS[index];
        }
        return "";
    }

    public int getPriorityAsInterger()
    {
        Integer priority=0;
        if(mPriority != null && !mPriority.isEmpty())
        {
            try
            {
                priority=Integer.parseInt(mPriority);
            }
            catch (Exception e)
            {
                Log.e(ToDoItem.class.getSimpleName(),"Error while casting to integer");
            }
        }
        return priority;
    }

}
