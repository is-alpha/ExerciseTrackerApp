package com.example.exercisetrackerapp.ui.results;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import com.example.exercisetrackerapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class AverageCaloriesActivity  extends AppCompatActivity {

    private Spinner spinner;

    private LineChart lineChart;
    private LineDataSet lineDataSet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_calories);

        spinner = (Spinner) findViewById(R.id.spinnerExercise);

        // Enlazamos al XML
        lineChart = this.findViewById(R.id.lineChart);

        // Recolectamos el set de datos
        ArrayList<Entry> lineEntries = new ArrayList<Entry>();

        // -----  MOMENTANEO  --------------------------
        lineEntries.add(new Entry((float) 0,(float)2));
        lineEntries.add(new Entry((float) 1,(float)0));

        // Unimos los datos al data set
        lineDataSet = new LineDataSet(lineEntries, "Platzi");

        // Asociamos al gráfico
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);
    }


}
