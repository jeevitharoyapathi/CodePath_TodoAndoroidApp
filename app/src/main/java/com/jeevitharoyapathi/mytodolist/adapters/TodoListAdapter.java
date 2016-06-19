package com.jeevitharoyapathi.mytodolist.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeevitharoyapathi.mytodolist.R;
import com.jeevitharoyapathi.mytodolist.model.ToDoItem;

import java.util.List;


public class TodoListAdapter extends  RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    private List<ToDoItem> mDataset;
    private Context mContext;
    private OnItemClickListener mclicklistener;
    TextView vTitle;
    TextView vDescription;
    ImageView vPriority;
    private LruCache<String, Bitmap> mImgMemoryCache;//cache which holds the recently downloaded image

    public TodoListAdapter(Context myContext, List<ToDoItem> myDataset) {
        mContext = myContext;
        mDataset = myDataset;
    }


    public TodoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void setDataSet(List<ToDoItem> dataset)
    {
        mDataset=dataset;
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        mclicklistener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDoItem toDoItem = mDataset.get(position);
        holder.bindMovieData(toDoItem);

    }

    public int getItemCount() {
        return mDataset.size();
    }

    public ToDoItem getItem(int position) {
        return mDataset.get(position);
    }


    // Viewholder of the recycler view
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View v) {
            super(v);
            vTitle=(TextView)v.findViewById(R.id.txtTitle);
            vDescription=(TextView)v.findViewById(R.id.txtNotes);
            vPriority=(ImageView)v.findViewById(R.id.imgPriority);

            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                  if(mclicklistener != null) {
                        mclicklistener.onItemClick(getItem(getPosition()));
                  }
                }

            });
        }

        public void bindMovieData(ToDoItem toDoItem) {
            vTitle.setText(toDoItem.getTitle());
            vDescription.setText(toDoItem.getNotes());
            Integer priority= toDoItem.getPriorityAsInterger();
            switch (priority)
            {
                case  1:
                    vPriority.setImageResource(R.drawable.ic_medium);
                        break;
                case  2:
                    vPriority.setImageResource(R.drawable.ic_high);
                    break;
                default:
                    vPriority.setImageResource(R.drawable.ic_low);
                    break;
            }
        }
    }

    //Interface to handle recylerview onClick Events
        public interface OnItemClickListener {
            public void onItemClick(ToDoItem item);
        }
}
