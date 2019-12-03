package com.example.exercisetrackerapp.ui.results;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import com.example.exercisetrackerapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineDataSet;

public class AverageCaloriesActivity  extends AppCompatActivity {

    private Spinner spinner;

    private LineChart lineChart;
    private LineDataSet lineDataSet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_calories);

        spinner = (Spinner) findViewById(R.id.spinnerExercise);
    }


}
