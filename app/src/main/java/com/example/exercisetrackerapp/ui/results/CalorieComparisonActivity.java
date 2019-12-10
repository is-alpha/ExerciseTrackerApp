package com.example.exercisetrackerapp.ui.results;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Calorie;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class CalorieComparisonActivity extends AppCompatActivity {

    private LineChart lineChart,lineChart2;
    private LineDataSet lineDataSet, lineDataSet2;
    private TextView caloriasquemadas;
    private TextView caloriasconsumidas;
    private float calConsum=0,calQuema=0, totalCalories;
    int i,range;
    private String email,userID,emailAux;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Float>burnedcalories=new ArrayList<Float>();

    private ArrayList<Calorie> burnedCalorie = new ArrayList<Calorie>();
    private ArrayList<Calorie> caloriesConsumed = new ArrayList<Calorie>();

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

        if (user != null) { email = user.getEmail(); }

        getcalConsumidas();
        getcaloriasQuemadas();

        caloriasconsumidas = this.findViewById(R.id.textView11);
        caloriasquemadas= this.findViewById(R.id.textView13);

        lineChart = this.findViewById(R.id.lineChart);
        lineChart2= this.findViewById(R.id.grafica2);

        spinner = (Spinner) findViewById(R.id.spinnerExercise);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                range = getRange(spinner.getSelectedItem().toString());
                setGraphics2();

                caloriasconsumidas.setText(String.valueOf(calConsum));
                caloriasquemadas.setText(String.valueOf(calQuema));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

    }

    private void setGraphics2(){
        // Recolectamos el set de datos
        cleanGraphics();
        setCaloriasConsumidas();
        setCaloriasQuemadas();

        System.out.println(lineDataSet);
        System.out.println(lineDataSet2);

        // Unimos los datos al data set
        lineDataSet = new LineDataSet(lineEntries, "Calorías consumidas");
        lineDataSet2 = new LineDataSet(lineEntries2, "Calorías gastadas");
        lineDataSet2.setColor(Color.GREEN);
        lineDataSet2.setCircleColorHole(Color.GREEN);
        lineDataSet2.setCircleColor(Color.GREEN);

        // Asociamos al gráfico
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);

        lineData = new LineData();
        lineData.addDataSet(lineDataSet2);
        lineChart2.setData(lineData);

        lineChart.invalidate();

        // draw points over time
        lineChart.animateX(1500);

    }

    private void cleanGraphics(){
        int range2;
        lineEntries = new ArrayList<Entry>();
        lineEntries2 = new ArrayList<Entry>();

        if(range == 1) range2=24;
        else range2=range;

        for(int i=0; i<range2; i++) {
            lineEntries.add(new Entry((float) i, (float)0));
            lineEntries2.add(new Entry((float) i, (float)0));
        }
    }

    private void setCaloriasConsumidas(){
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        int auxInt, range2=range;//,cnt=0;
        if(range==1) range2=24;
        float [] calories = new float [range2];
        Arrays.fill(calories, 0);

        calConsum = 0;

        for(Calorie calorie: caloriesConsumed){
            auxInt = DateValidator.getCountOfDays(calorie.getDate().format(), "dd/MM/yyyy");

            if( auxInt < range ){
                if(range!=1){
                    calConsum += calorie.getCantCalorie();
                    calories[auxInt]+=calorie.getCantCalorie();
                }
                else{
                    calConsum += calorie.getCantCalorie();
                    calories[calorie.getDate().getHour()]+=calorie.getCantCalorie();
                }
            }
        }

        for(int i=0;i<range2;i++){
            if(calories[i]!=0){
                lineEntries.get(i).setY(calories[i]);
            }
        }
    }

    private void setCaloriasQuemadas(){
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        int auxInt, range2=range;//,cnt=0;
        if(range==1) range2=24;
        float [] calories = new float [range2];
        Arrays.fill(calories, 0);

        calQuema = 0;

        for(Calorie calorie: burnedCalorie){
            auxInt = DateValidator.getCountOfDays(calorie.getDate().format(), "dd/MM/yyyy");

            if( auxInt < range ){
                if(range!=1){
                    calQuema += calorie.getCantCalorie();
                    calories[auxInt]+=calorie.getCantCalorie();
                }
                else{
                    calQuema += calorie.getCantCalorie();
                    calories[calorie.getDate().getHour()]+=calorie.getCantCalorie();
                }
            }
        }

        for(int i=0;i<range2;i++){
            if(calories[i]!=0){
                lineEntries2.get(i).setY(calories[i]);
            }
        }
    }

    private void getcalConsumidas(){
        databaseReference.child("calConsumidas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();
                    Calorie calorie;

                    if(emailAux.equals(email)){

                        date = new Date(areaSnapshot.child("date").child("day").getValue(Integer.class),
                                areaSnapshot.child("date").child("month").getValue(Integer.class),
                                areaSnapshot.child("date").child("year").getValue(Integer.class));
                        date.setHour(areaSnapshot.child("date").child("hour").getValue(Integer.class));

                        if( DateValidator.getCountOfDays(date.format(),"dd/MM/yyyy") <= 365) {

                            //Toast.makeText(CalorieComparisonActivity.this,date.toString(),Toast.LENGTH_LONG).show();

                            calConsum = Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString()) +
                                    Float.parseFloat(areaSnapshot.child("calExtra").getValue().toString());
                            calorie = new Calorie(calConsum, date);
                            caloriesConsumed.add(calorie);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getcaloriasQuemadas(){
        databaseReference.child("caloriasQuemadas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();

                    if(emailAux.equals(email)){
                        Calorie calorie;

                        date = new Date(areaSnapshot.child("date").child("day").getValue(Integer.class),
                                areaSnapshot.child("date").child("month").getValue(Integer.class),
                                areaSnapshot.child("date").child("year").getValue(Integer.class));

                        date.setHour(areaSnapshot.child("date").child("hour").getValue(Integer.class));

                        //Guardar ocurrencias del último año
                        if( DateValidator.getCountOfDays(date.format(),"dd/MM/yyyy") <= 365) {
                            calQuema = Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString());

                            calorie = new Calorie(Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString()),
                                    date);
                            burnedCalorie.add(calorie);
                        }
                    }
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
                return 1;
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