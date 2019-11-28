package com.example.exercisetrackerapp.ui.exerciseRoutine;

//import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Exercise;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Exercise> mData;                   //private Context mContext;
    private int selected = -1;                      //Item selected
    private InfoAdapterInterface adapterInterface;  // Interface Object

    public RecyclerViewAdapter(List<Exercise> mData, InfoAdapterInterface adapterInterface) {
        //this.mContext = mContext;
        this.mData = mData;
        // Initialize your interface to send updates to fragment.
        this.adapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        view = mInflater.inflate(R.layout.cardview_item_exercise, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        myViewHolder.tv_exercise_name.setText(mData.get(i).getName());
        myViewHolder.img_exercise_thumbnail.setImageResource(mData.get(i).getThumbnail());

        final int finalI = i;

        //listener
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(selected == -1) {
                    if (myViewHolder.cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        selected = finalI;
                        myViewHolder.cardView.setCardBackgroundColor(Color.parseColor("#CDDC39"));
                    }
                }
                else {
                    if (myViewHolder.cardView.getCardBackgroundColor().getDefaultColor() != -1) {
                        selected = -1;
                        myViewHolder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
                    }
                    else
                        Toast.makeText(v.getContext(), "Ya tiene seleccionado " + mData.get(selected).getName(), Toast.LENGTH_SHORT).show();
                }
                // Pass data  that is defined in your interface's params
                adapterInterface.OnItemClicked(this.getExercise());
            }

            private String getExercise() {
                if(selected != -1)
                    return mData.get(selected).getName();
                else
                    return "";
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_exercise_name;
        ImageView img_exercise_thumbnail;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_exercise_name = (TextView) itemView.findViewById(R.id.name_exercise_id);
            img_exercise_thumbnail = (ImageView) itemView.findViewById(R.id.exercise_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }

    // Your interface to send data to your fragment
    public interface InfoAdapterInterface{
        void OnItemClicked(String exercise);
    }
}
