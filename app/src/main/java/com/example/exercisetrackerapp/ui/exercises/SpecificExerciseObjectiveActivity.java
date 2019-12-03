package com.example.exercisetrackerapp.ui.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.burnedCalories.BurnedCaloriesActivity;
import com.example.exercisetrackerapp.ui.exerciseRoutine.TrackExerciseActivity;


public class SpecificExerciseObjectiveActivity extends AppCompatActivity {

    Button botonTrackExercise;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_exercise);

        botonTrackExercise = (Button) findViewById(R.id.botonTrackExercise);
        botonTrackExercise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchStopWatch();
            }
        });
    }

    private void launchStopWatch() {
        Intent intent = new Intent(this, TrackExerciseActivity.class);
        startActivity(intent);
    }


}


