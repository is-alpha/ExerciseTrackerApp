package com.example.exercisetrackerapp.ui.results;

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

public class CalorieComparisonActivity extends AppCompatActivity {

    private LineChart lineChart,lineChart2;
    private LineDataSet lineDataSet, lineDataSet2;
    private TextView caloriasquemadas;
    private TextView caloriasconsumidas;
    private float calConsum=0,calQuema=0;
    int i;
    private   String email,userID,emailAux;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Float>burnedcalories=new ArrayList<Float>();
    ArrayList<Float>consumecalories=new ArrayList<Float>();

    ArrayList<Entry> lineEntries = new ArrayList<Entry>();
    ArrayList<Entry> lineEntries2 = new ArrayList<Entry>();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_comparison);
        inicializarFirebase();

        if (user != null) {
            email = user.getEmail();
            userID = user.getUid();
        }


        databaseReference.child("calConsumidas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();

                    if(emailAux.equals(email)){
                        calConsum = Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString());
                        calConsum = calConsum + Float.parseFloat(areaSnapshot.child("calExtra").getValue().toString());
                       consumecalories.add(calConsum);

                    }

                }
                calConsum=0;
                for ( i = 0; i<consumecalories.size(); i++){
                    lineEntries2.add(new Entry((float)i,consumecalories.get(i)));
                    calConsum = calConsum + consumecalories.get(i);
                }

                lineDataSet2= new LineDataSet(lineEntries2, "Calorias Consumidas");
                LineData lineData2 = new LineData();
                lineData2.addDataSet(lineDataSet2);
                lineChart2.setData(lineData2);

                caloriasconsumidas.setText(Float.toString(calConsum));

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

                    if(emailAux.equals(email)){
                        calQuema = Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString());

                        burnedcalories.add(calQuema);
                    }
                }
                    calQuema=0;
                for ( i = 0; i<burnedcalories.size(); i++){
                    lineEntries.add(new Entry((float)i,burnedcalories.get(i)));
                    calQuema = calQuema + burnedcalories.get(i);
                }

                    lineDataSet = new LineDataSet(lineEntries, "Calorias Quemadas");
                    LineData lineData = new LineData();
                    lineData.addDataSet(lineDataSet);
                    lineChart.setData(lineData);
                    caloriasquemadas.setText(Float.toString(calQuema));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });





        caloriasconsumidas = this.findViewById(R.id.textView11);
        caloriasquemadas= this.findViewById(R.id.textView13);

        lineChart = this.findViewById(R.id.lineChart);
        lineChart2= this.findViewById(R.id.grafica2);


    }



    private void inicializarFirebase(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}
