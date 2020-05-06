package com.example;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;

import java.util.ArrayList;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder>  {

    private static final String TAG = "recyclerViewAdapter";
    public interface Item_Click_Listener{
        void Item_Click(int position);
    }
    private Item_Click_Listener mListener;


    public void Set_Click_Listener(Item_Click_Listener listener){
        mListener = listener;
    }


    private ArrayList<One_Task> mtasks_list = new ArrayList<>();
    private Context mContext;

    // creating a single adapter
    public recyclerViewAdapter(Context context, ArrayList<One_Task> tasks_list) {
        this.mtasks_list = tasks_list;
        this.mContext = context;}



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.task_text.setText(mtasks_list.get(position).return_task_text());
        holder.image.setImageResource(mtasks_list.get(position).return_im_number());
    }

    @Override
    public int getItemCount() {
        return mtasks_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView task_text;
        RelativeLayout parent_layout;
        public ViewHolder(@NonNull View itemView, final Item_Click_Listener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            task_text = itemView.findViewById(R.id.textView2);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            itemView.setOnClickListener(new View.OnClickListener()
            {@Override
                public void onClick(View view){
                Log.d(TAG, "onClick: click....");
                if (listener != null)
                {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.Item_Click(position);
                    }
                }
            }

            });
        }
    }

}
