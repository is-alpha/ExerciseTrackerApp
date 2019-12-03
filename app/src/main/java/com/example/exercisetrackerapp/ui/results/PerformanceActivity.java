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
import java.util.Calendar;

public class PerformanceActivity extends AppCompatActivity {

    private Spinner spinner;
    private int range;
    float peso, altura, total = 0, calorias, edad, calgoals, timegoals, timeprom,ttlcal;
    Calendar cale = Calendar.getInstance();
    int year = cale.get(Calendar.YEAR);
    String genero;
    TextView basal, cal,totalg,calgoalstxt,timegoalstxt, promtimeg;
    ArrayList<Entry> lineEntries = new ArrayList<Entry>();
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
        calgoalstxt= (TextView) findViewById(R.id.textViewxfg);
        timegoalstxt=(TextView)findViewById(R.id.textViewex0);
        promtimeg=(TextView)findViewById(R.id.textViewexprom0);

        inicializarFirebase();
        getCaloriesBurned();
        getBasalCalories();
        getTimeGoals();
        getPromTimeGoals();
        getCaloriesGoals();


        spinner = (Spinner) findViewById(R.id.spinnerExercise);

        setBasalCalories();
        setAverage();
        setTotal();
        setCaloriesGoals();
        setTimeGoals();
        setPromTimeGoals();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                range = getRange(spinner.getSelectedItem().toString());
                setGraphic();
                cleanGraphic();
                setBasalCalories();
                setAverage();
                setTotal();
                setCaloriesGoals();
                setTimeGoals();
                setPromTimeGoals();
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

        cleanGraphic();
        setCalories();

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
            cal.setText(Float.toString(ttlcal));
        else
            cal.setText((Float.toString(ttlcal/range)));

    }
    void setTotal(){
        if(range==24)
            totalg.setText((Float.toString(total + ttlcal)));
        else
            totalg.setText((Float.toString((total*range)+(ttlcal/range))));
    }

    void setPromTimeGoals(){
        if(range==24)
            promtimeg.setText((Float.toString(timeprom)));
        else
            promtimeg.setText((Float.toString(timeprom/range)));
    }

    void setCaloriesGoals(){

        calgoalstxt.setText((Float.toString(calgoals)));
    }

    void setTimeGoals(){

        timegoalstxt.setText((Float.toString(timegoals)));

    }

    private void getBasalCalories(){
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

    private void getPromTimeGoals(){

        databaseReference.child("ejercicioActual").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 1;
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();
                    if (emailAux.equals(email)) {
                        timeprom = timeprom + 10;//Float.parseFloat(areaSnapshot.child("objTiempo").getValue().toString());
                        timeprom = timeprom / i;
                        i++;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getCaloriesGoals(){
        databaseReference.child("metas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot areaSnapshot: dataSnapshot.getChildren()){
                    emailAux=areaSnapshot.child("usuario").getValue().toString();
                    if(emailAux.equals(email)){
                        calgoals=calgoals+ Float.parseFloat(areaSnapshot.child("calorias").getValue().toString());
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
                Date date;
                Calorie calorie;

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();
                    if (emailAux.equals(email)) {

                        date = new Date(areaSnapshot.child("date").child("day").getValue(Integer.class),
                                areaSnapshot.child("date").child("month").getValue(Integer.class),
                                areaSnapshot.child("date").child("year").getValue(Integer.class));

                        date.setHour(areaSnapshot.child("date").child("hour").getValue(Integer.class));

                        if( DateValidator.getCountOfDays(date.format(),"dd/MM/yyyy") <= 365) {
                            calorie = new Calorie(Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString()),
                                    date);

                            burnedCalories.add(calorie);
                            ttlcal += Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString());
                        }

                        setGraphic();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void getTimeGoals(){
        databaseReference.child("metas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot areaSnapshot: dataSnapshot.getChildren()){
                    emailAux=areaSnapshot.child("usuario").getValue().toString();
                    if(emailAux.equals(email)){
                        timegoals=timegoals + Float.parseFloat(areaSnapshot.child("tiempo").getValue().toString());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void cleanGraphic() {
        int range2;
        lineEntries = new ArrayList<Entry>();

        if (range == 1) range2 = 24;
        else range2 = range;

        for (int i = 0; i <= range2; i++) {
            lineEntries.add(new Entry((float) i, (float) 0));
        }
    }

    private void setCalories(){
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        int auxInt, range2=range;//,cnt=0;
        if(range==1) range2=24;
        float [] calories = new float [range2];
        Arrays.fill(calories, 0);

        calorias= 0;

        for(Calorie calorie: burnedCalories){
            auxInt = DateValidator.getCountOfDays(calorie.getDate().format(), "dd/MM/yyyy");

            if( auxInt < range ){
                if(range!=1){
                    calorias += calorie.getCantCalorie();
                    calories[auxInt]+=calorie.getCantCalorie();
                }
                else{
                    calorias += calorie.getCantCalorie();
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

}


