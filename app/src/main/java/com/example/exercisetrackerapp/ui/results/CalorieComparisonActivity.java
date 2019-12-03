package com.example.exercisetrackerapp.ui.results;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.example.exercisetrackerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CalorieComparisonActivity extends AppCompatActivity {

    private LineChart lineChart,lineChart2;
    private LineDataSet lineDataSet, lineDataSet2;
    private TextView caloriasquemadas;
    private TextView caloriasconsumidas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Float>burnedcalories;

    public void getBurnedCalories(){

        inicializarFirebase();

        databaseReference.child("caloriasQuemadas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    burnedcalories=new ArrayList<Float>();
                    for(DataSnapshot areaSnapshot: dataSnapshot.getChildren()){
                        Float bcalorie= areaSnapshot.child("cantCalorie").getValue(Float.class);
                        burnedcalories.add(bcalorie);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void inicializarFirebase(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_comparison);
        getBurnedCalories();
        caloriasconsumidas = this.findViewById(R.id.textView11);
        caloriasquemadas= this.findViewById(R.id.textView13);

        caloriasquemadas.setText("100");
        caloriasconsumidas.setText("200");

        lineChart = this.findViewById(R.id.lineChart);
        lineChart2= this.findViewById(R.id.grafica2);

        ArrayList<Entry> lineEntries = new ArrayList<Entry>();
        ArrayList<Entry> lineEntries2 = new ArrayList<Entry>();

        lineEntries.add(new Entry((float)0,(float)2));
        lineEntries.add(new Entry((float) 1,(float)0));


        lineEntries2.add(new Entry((float)0,(float)2));
        lineEntries2.add(new Entry((float) 1,(float)0));

        lineDataSet = new LineDataSet(lineEntries, "Platzi");
        lineDataSet2= new LineDataSet(lineEntries2, "Platzi");


        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);

        LineData lineData2 = new LineData();
        lineData2.addDataSet(lineDataSet2);
        lineChart2.setData(lineData2);


    }






}
