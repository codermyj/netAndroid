package com.bignerdranch.android.netproject111.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.netproject111.R;
import com.bignerdranch.android.netproject111.beans.Task;
import com.bignerdranch.android.netproject111.beans.TaskOne;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<TaskOne> mTaskList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView idTv;
        TextView titleTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTv = itemView.findViewById(R.id.task_id);
            titleTv = itemView.findViewById(R.id.task_title);
        }
    }

   public TaskAdapter(List<TaskOne> taskList){
        mTaskList = taskList;
   }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.net_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TaskOne task = mTaskList.get(position);
        holder.idTv.setText(task.getId());
        holder.titleTv.setText(task.getTitle());
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }
}
