package com.example.exercisetrackerapp.ui.exercises;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Exercise;
import com.example.exercisetrackerapp.data.model.Type;

import java.util.ArrayList;
import java.util.List;

public class ExercisesActivity extends AppCompatActivity {
    List<Exercise> lstExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        lstExercise = new ArrayList<>();

        //MOMENTANEO
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));

        RecyclerView myrv =  (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstExercise);

        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);
    }
}
