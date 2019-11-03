package com.example.exercisetrackerapp.ui.exerciseRoutine;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Exercise;
import com.example.exercisetrackerapp.data.model.Type;
import com.example.exercisetrackerapp.ui.exercises.ExercisesViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExerciseRoutineFragment extends Fragment {
    List<Exercise> lstExercise;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_exercise, container, false);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_id);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Data from recycler view
        lstExercise = new ArrayList<>();
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));

        /*Exercise itemsData[] = {
                new Exercise("Correr", Type.RUNNING,R.drawable.correr),
                new Exercise("Correr", Type.RUNNING,R.drawable.correr)
        };*/

        // 3. create an adapter
        //MyAdapter mAdapter = new MyAdapter(itemsData);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(lstExercise);
        // 4. set adapter
        //recyclerView.setAdapter(mAdapter);
        recyclerView.setAdapter(myAdapter);

        return rootView;
    }
}
