package com.example.exercisetrackerapp.ui.results.AverageCalories;

import android.graphics.Color;
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

public class AverageCaloriesActivity  extends AppCompatActivity {

    //VARIABLES
    private Spinner spinner;
    private int range;
    float peso, altura, totalMB = 0,calorias, edad, totalCalories, promCalories;
    Calendar cale = Calendar.getInstance();
    int year = cale.get(Calendar.YEAR);
    String genero;
    TextView basal, promCal, totalBCal;
    ArrayList<Calorie> burnedCalories = new ArrayList<Calorie>() ;

    //GRAFICOS
    private LineChart lineChart;
    private LineDataSet lineDataSet, lineDataSet2;
    private ArrayList<Entry> lineEntries = new ArrayList<Entry>();
    private ArrayList<Entry> lineEntries2 = new ArrayList<Entry>();

    //BASE DE DATOS
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //referencia al usuario
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email, emailAux;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_calories);

        if (user != null) {
            email = user.getEmail();
        }
        basal = (TextView) findViewById(R.id.numMBasal);
        promCal = (TextView) findViewById(R.id.numKcal);
        totalBCal = findViewById(R.id.totalCBurned);

        inicializarFirebase();
        getBasalCalories();
        getCaloriesBurned();

        //Obtener spinner seleccionado
        spinner = (Spinner) findViewById(R.id.spinnerExercise);

        //Listener. Realizar cambios si se cambia el valor del spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                range = getRange(spinner.getSelectedItem().toString());
                //getCaloriesBurned();
                setGraphic();
                setBasalCalories();
                //setCalories();
                //int i = DateValidator.getCountOfDays("3/12/2019","dd/MM/yyyy");
                //Toast.makeText(parentView.getContext(), String.valueOf(i) , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
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

    void setGraphic() {
        // Enlazamos al XML
        lineChart = this.findViewById(R.id.lineChart);

        // Recolectamos el set de datos
        cleanGraphic();
        setCalories();

        // disable description text
        lineChart.getDescription().setEnabled(false);
        // Unimos los datos al data set
        lineDataSet = new LineDataSet(lineEntries, "Calorías gastadas");
        lineDataSet2 = new LineDataSet(lineEntries2, "Metabolismo Basal");
        lineDataSet2.setColor(Color.GREEN);
        lineDataSet2.setCircleColorHole(Color.GREEN);
        lineDataSet2.setCircleColor(Color.GREEN);

        // Asociamos al gráfico
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineData.addDataSet(lineDataSet2);
        lineChart.setData(lineData);
        lineChart.invalidate();

        // draw points over time
        lineChart.animateX(1500);
    }

    void setBasalCalories(){
        basal.setText(Float.toString(this.getNumBasalCalories()));
    }

    private float getNumBasalCalories(){
            return (totalMB *range); //por cantidad de dias
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
                            totalMB = (float) ((10 * peso) + (6.25 * altura * 100) - (5 * edad) + 5);
                        } else {
                            totalMB = (float) ((10 * peso) + (6.25 * altura * 100) - (5 * edad) - 161);
                        }
                        setBasalCalories();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setCalories(){
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        int auxInt, range2=range;//,cnt=0;
        if(range==1) range2=24;
        float [] calories = new float [range2];
        Arrays.fill(calories, 0);

        totalCalories = 0;

        for(Calorie calorie: burnedCalories){
            auxInt = DateValidator.getCountOfDays(calorie.getDate().format(), "dd/MM/yyyy");

            if( auxInt < range ){
                if(range!=1){
                    totalCalories += calorie.getCantCalorie();
                    calories[auxInt]+=calorie.getCantCalorie();
                }
                else{
                    totalCalories += calorie.getCantCalorie();
                    calories[calorie.getDate().getHour()]+=calorie.getCantCalorie();
                }
            }
        }

        for(int i=0;i<range2;i++){
            if(calories[i]!=0){
                lineEntries.get(i).setY(calories[i]);
            }

            if(range!=1)
                lineEntries2.get(i).setY(totalMB);
            else
                lineEntries2.get(i).setY(totalMB/24);
        }

        //promedio por dia incluyendo metabolismo basal
        promCal.setText(decimalFormat.format((totalCalories+getNumBasalCalories())/range));
        totalBCal.setText(decimalFormat.format(totalCalories+getNumBasalCalories()));
    }

    //Funcion que almacena todas las calorias quemadas del ultimo año
    private void getCaloriesBurned(){
        //getCountOfDays(String date, String dateFormat)
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

                        //Guardar ocurrencias del último año
                        if( DateValidator.getCountOfDays(date.format(),"dd/MM/yyyy") <= 365) {
                            calorie = new Calorie(Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString()),
                                    date);

                            burnedCalories.add(calorie);
                            calorias += Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString());
                        }

                        //setCalories();
                        setGraphic();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void cleanGraphic(){
        int range2;
        lineEntries = new ArrayList<Entry>();
        lineEntries2 = new ArrayList<Entry>();

        if(range == 1) range2=24;
        else range2=range;

        for(int i=0; i<range2; i++) {
            lineEntries.add(new Entry((float) i, (float)0));
            lineEntries2.add(new Entry((float) i, (float)totalMB));
        }
    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}


