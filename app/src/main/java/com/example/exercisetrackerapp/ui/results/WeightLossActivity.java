package com.example.exercisetrackerapp.ui.results;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
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

public class WeightLossActivity extends AppCompatActivity {
    //VARIABLES
    private static int range = 365; //UN AÑO

    private TextView textViewPesoInicial,textViewPesoMeta,textViewPesoPerdido;
    public String email="",uid,id,userID, pesoInicial = "0", peso="0",emailAux;

    //GRAFICO
    private LineChart lineChart;
    private LineDataSet lineDataSet;
    private ArrayList<Entry> lineEntries;
    private LineData lineData;

    //BASE DE DATOS
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss);

        textViewPesoInicial  = (TextView) findViewById(R.id.textViewPesoInicial);
        textViewPesoMeta  = (TextView) findViewById(R.id.textViewPesoMeta);
        textViewPesoPerdido  = (TextView) findViewById(R.id.textViewPesoPerdido);

        inicializarFirebase();

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("correo").getValue().toString();
                    if(emailAux.equals(email)){
                        pesoInicial = areaSnapshot.child("peso").getValue().toString();
                        textViewPesoInicial.setText(pesoInicial);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        databaseReference.child("peso").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();
                    if(emailAux.equals(email)){
                        peso = areaSnapshot.child("pesoIdeal").getValue().toString();
                        textViewPesoMeta.setText(peso);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        setGraphic();
    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        if (user != null) {
            email = user.getEmail();
        }
    }

    private void setGraphic() {
        // Enlazamos al XML
        lineChart = this.findViewById(R.id.lineChart);
        lineChart.setNoDataText("Grafica no disponible...");

        // Recolectamos el set de datos
        cleanGraphic();
        setWeight();

        // disable description text
        lineChart.getDescription().setEnabled(false);
        // Unimos los datos al data set
        lineDataSet = new LineDataSet(lineEntries, "Peso Anual");
        //Diseno del gráfico
        lineDataSet.setColor(Color.RED);
        lineDataSet.setCircleColorHole(Color.BLUE);
        lineDataSet.setCircleColor(Color.RED);

        // Asociamos al gráfico
        lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();

        // draw points over time
        lineChart.animateX(1000);
    }

    private void cleanGraphic(){
        lineEntries = new ArrayList<Entry>();

        for(int i=0; i<range; i++) {
            lineEntries.add(new Entry((float) i, (float)0));
        }
    }

    private void setWeight(){
        for(int i=0; i<range; i++) {
            lineEntries.set(i, new Entry((float) i, Float.valueOf(pesoInicial)));
            //lineEntries.get(i).setY(Float.valueOf(pesoInicial));
        }
    }
}
