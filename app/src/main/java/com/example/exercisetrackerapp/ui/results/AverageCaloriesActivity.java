package com.example.exercisetrackerapp.ui.results;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.burnedCalories.BurnedCalories;
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

public class AverageCaloriesActivity  extends AppCompatActivity {

    //VARIABLES
    private Spinner spinner;
    private int range;
    float peso, altura, total = 0, calorias, i = 1, edad;
    Calendar cale = Calendar.getInstance();
    int year = cale.get(Calendar.YEAR);
    String genero;
    TextView basal, cal;
    ArrayList<BurnedCalories> burnedCalories = new ArrayList<BurnedCalories>() ;

    //GRAFICOS
    private LineChart lineChart;
    private LineDataSet lineDataSet;

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
        cal = (TextView) findViewById(R.id.numKcal);

        inicializarFirebase();
        getCaloriesBurned();

        //Obtener spinner seleccionado
        spinner = (Spinner) findViewById(R.id.spinnerExercise);
        //Listener. Realizar cambios si se cambia el valor del spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                range = getRange(spinner.getSelectedItem().toString());
                setGraphic();
                setBasalCalories();
                //Toast.makeText(parentView.getContext(), String.valueOf(range) , Toast.LENGTH_LONG).show();
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
        // Enlazamos al XML
        lineChart = this.findViewById(R.id.lineChart);

        // Recolectamos el set de datos
        ArrayList<Entry> lineEntries = new ArrayList<Entry>();


        // -----  MOMENTANEO  --------------------------

        lineEntries.add(new Entry((float) 0, (float) 2));
        lineEntries.add(new Entry((float) 1, (float) 0));

        // -----  MOMENTANEO  --------------------------


        // disable description text
        lineChart.getDescription().setEnabled(false);
        // Unimos los datos al data set
        lineDataSet = new LineDataSet(lineEntries, "Calorías gastadas");
        // Asociamos al gráfico
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);

        // draw points over time
        lineChart.animateX(1500);
    }

    void setBasalCalories(){
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
                        if(range==24)
                            basal.setText(Float.toString(total)); //cantidad de un solo dia
                        else
                            basal.setText(Float.toString(total*range)); //por cantidad de dias
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //Funcion que almacena todas las calorias quemadas de los ultimos dos años
    private void getCaloriesBurned(){

        //BurnedCalories(String usuario, float cantCalorias, Date date,String ejercicio)

        databaseReference.child("caloriasQuemadas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();
                    if (emailAux.equals(email)) {
                        calorias = calorias + Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString());
                        calorias = calorias / i;
                        cal.setText(Float.toString(calorias));
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


