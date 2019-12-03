package com.example.exercisetrackerapp.ui.results;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AverageCaloriesActivity  extends AppCompatActivity {

    private Spinner spinner;
/*
    private LineChart lineChart;
    private LineDataSet lineDataSet;
    */
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email, emailAux;
    float peso, altura, total=0,calorias,i=1,edad;
    Calendar cale= Calendar.getInstance();
    int year= cale.get(Calendar.YEAR);
    String genero;
    TextView basal,cal;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_calories);

        if (user != null) {
            email = user.getEmail();

        }
        basal = (TextView) findViewById(R.id.textView7);
        cal = (TextView) findViewById(R.id.textView14);
        inicializarFirebase();
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
                        if (genero.equals("Masculino")){
                            total = (float) ((10*peso) + (6.25*altura*100)- (5*edad ) + 5);
                        }
                        else{
                            total= (float) ((10*peso)  + (6.25*altura*100) - (5*edad) -161);

                        }
                        basal.setText(Float.toString(total));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        databaseReference.child("caloriasQuemadas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();
                    if (emailAux.equals(email)) {

                        calorias = calorias + Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString());
                        calorias = calorias/i;
                        cal.setText(Float.toString(calorias));
                        i++;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        spinner = (Spinner) findViewById(R.id.spinnerExercise);

       /* // Enlazamos al XML
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
        lineChart.setData(lineData);*/
    }
    private void inicializarFirebase () {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}
