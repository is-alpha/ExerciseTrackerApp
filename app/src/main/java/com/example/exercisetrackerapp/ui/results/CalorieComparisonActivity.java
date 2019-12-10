package com.example.exercisetrackerapp.ui.results;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Date;
import com.example.exercisetrackerapp.ui.results.AverageCalories.DateValidator;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private float calConsum=0,calQuema=0;
    int i,range;
    private String email,userID,emailAux;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Float>burnedcalories=new ArrayList<Float>();
    ArrayList<Float>consumecalories=new ArrayList<Float>();
    private Spinner spinner;

    ArrayList<Entry> lineEntries = new ArrayList<Entry>();
    ArrayList<Entry> lineEntries2 = new ArrayList<Entry>();
    Date date;
    boolean registroEnc = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_comparison);
        inicializarFirebase();

        if (user != null) {
            email = user.getEmail();
            userID = user.getUid();
        }

        caloriasconsumidas = this.findViewById(R.id.textView11);
        caloriasquemadas= this.findViewById(R.id.textView13);

        lineChart = this.findViewById(R.id.lineChart);
        lineChart2= this.findViewById(R.id.grafica2);

        spinner = (Spinner) findViewById(R.id.spinnerExercise);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                range = getRange(spinner.getSelectedItem().toString());
                setGraphics();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });



    }

    private void setGraphics(){
        registroEnc = false;
        databaseReference.child("calConsumidas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();

                    if(emailAux.equals(email)){

                        /*date = new Date(areaSnapshot.child("date").child("day").getValue(Integer.class),
                                areaSnapshot.child("date").child("month").getValue(Integer.class),
                                areaSnapshot.child("date").child("year").getValue(Integer.class));
                        date.setHour(areaSnapshot.child("date").child("hour").getValue(Integer.class));
                        if( DateValidator.getCountOfDays(date.format(),"dd/MM/yyyy") <= range) {
                         */   calConsum = 0;
                        calConsum = Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString());
                        calConsum = calConsum + Float.parseFloat(areaSnapshot.child("calExtra").getValue().toString());
                        consumecalories.add(calConsum);
                        //}
                        registroEnc = true;
                    }

                }

                if(registroEnc) {
                    calConsum=0;
                    for ( i = 0; i<consumecalories.size(); i++){
                        lineEntries2.add(new Entry((float)i,consumecalories.get(i)));
                        calConsum = calConsum + consumecalories.get(i);
                    }

                    caloriasconsumidas.setText(Float.toString(calConsum));

                    lineDataSet2= new LineDataSet(lineEntries2, "Calorias Consumidas");
                    LineData lineData2 = new LineData();
                    lineData2.addDataSet(lineDataSet2);
                    lineChart2.setData(lineData2);
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        registroEnc = false;
        databaseReference.child("caloriasQuemadas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();

                    if(emailAux.equals(email)){
                       /* date = new Date(areaSnapshot.child("date").child("day").getValue(Integer.class),
                                areaSnapshot.child("date").child("month").getValue(Integer.class),
                                areaSnapshot.child("date").child("year").getValue(Integer.class));
                        if( DateValidator.getCountOfDays(date.format(),"dd/MM/yyyy") < range) {
                         */ calQuema = Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString());
                        burnedcalories.add(calQuema);
                        //}
                        registroEnc = true;
                    }
                }

                if(registroEnc) {
                    calQuema = 0;
                    for (i = 0; i < burnedcalories.size(); i++) {
                        lineEntries.add(new Entry((float) i, burnedcalories.get(i)));
                        calQuema = calQuema + burnedcalories.get(i);
                    }

                    lineDataSet = new LineDataSet(lineEntries, "Calorias Quemadas");
                    LineData lineData = new LineData();
                    lineData.addDataSet(lineDataSet);
                    lineChart.setData(lineData);
                    caloriasquemadas.setText(Float.toString(calQuema));
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private int getRange(String period) {
        switch (period) {
            case "Hoy":
                return 24;
            case "Semana":
                return 7;
            case "Mes":
                return 30;
            case "Año":
                return 365;
        }
        return 0;
    }

    private void inicializarFirebase(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}