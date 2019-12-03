package com.example.exercisetrackerapp.ui.results;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Calorie;
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
import java.util.Calendar;

public class PerformanceActivity extends AppCompatActivity {

    private Spinner spinner;
    private int range;
    float peso, altura, total = 0, calorias, edad;
    Calendar cale = Calendar.getInstance();
    int year = cale.get(Calendar.YEAR);
    String genero;
    TextView basal, cal,totalg;
    ArrayList<Calorie> burnedCalories = new ArrayList<Calorie>() ;
    private LineChart lineChart;
    private LineDataSet lineDataSet;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email, emailAux;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        if (user != null) {
            email = user.getEmail();
        }
        basal = (TextView) findViewById(R.id.textView7);
        cal = (TextView) findViewById(R.id.textView14);
        totalg = (TextView)findViewById(R.id.textViewxf);

        inicializarFirebase();
        getCaloriesBurned();
        getBasalCalories();

        spinner = (Spinner) findViewById(R.id.spinnerExercise);

        setBasalCalories();
        setAverage();
        setTotal();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                range = getRange(spinner.getSelectedItem().toString());
                setGraphic();
                setBasalCalories();
                setAverage();
                setTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
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

    void setGraphic() {
        lineChart = this.findViewById(R.id.lineChart);

        ArrayList<Entry> lineEntries = new ArrayList<Entry>();


        lineEntries.add(new Entry((float) 0, (float) 2));
        lineEntries.add(new Entry((float) 1, (float) 0));


        lineChart.getDescription().setEnabled(false);

        lineDataSet = new LineDataSet(lineEntries, "Calorías gastadas");

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);

        lineChart.animateX(1500);
    }

    void setBasalCalories(){
        if(range==24)
            basal.setText(Float.toString(total));
        else
            basal.setText(Float.toString(total*range));
    }
    void setAverage(){
        if(range==24)
            cal.setText(Float.toString(calorias));
        else
            cal.setText((Float.toString(calorias/range)));

    }
    void setTotal(){
        if(range==24)
            totalg.setText((Float.toString(total + calorias)));
        else
            totalg.setText((Float.toString((total*range)+(calorias/range))));
    }

    void getBasalCalories(){
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("correo").getValue().toString();
                    if (emailAux.equals(email)) {
                        peso = Float.parseFloat(areaSnapshot.child("peso").getValue().toString());
                        altura = Float.parseFloat(areaSnapshot.child("altura").getValue().toString());
                        genero = areaSnapshot.child("genero").getValue().toString();
                        edad = Float.parseFloat(areaSnapshot.child("fecha").child("year").getValue().toString());
                        edad = edad - year;

                        if (genero.equals("Masculino")) {
                            total = (float) ((10 * peso) + (6.25 * altura * 100) - (5 * edad) + 5);
                        } else {
                            total = (float) ((10 * peso) + (6.25 * altura * 100) - (5 * edad) - 161);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void getCaloriesBurned(){

        databaseReference.child("caloriasQuemadas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=1;
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();
                    if (emailAux.equals(email)) {
                        calorias = calorias + Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString());
                        calorias = calorias / i;
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}


