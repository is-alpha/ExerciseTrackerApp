package com.example.exercisetrackerapp.ui.exercises;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Exercise;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Exercise> mData;

    public RecyclerViewAdapter(Context mContext, List<Exercise> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_exercise, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_exercise_name.setText(mData.get(i).getName());
        myViewHolder.img_exercise_thumbnail.setImageResource(mData.get(i).getThumbnail());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_exercise_name;
        ImageView img_exercise_thumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_exercise_name = (TextView) itemView.findViewById(R.id.name_exercise_id);
            img_exercise_thumbnail = (ImageView) itemView.findViewById(R.id.exercise_img_id);
        }
    }
}
