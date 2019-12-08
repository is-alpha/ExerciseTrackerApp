package com.example.exercisetrackerapp.ui.results;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.example.exercisetrackerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class CalorieCountExerciseActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private Spinner spinner;
    List<String> listaNomEjercicios,listaFecha;
    StringBuilder fecha = new StringBuilder();
    List <Float> listaTiempo,listaCalorias;
    List <Integer> listaImagenes;
    String email="",uid,id,userID,emailAux,range;
    ListView listViewExercises;
    String nombreEjercicio,fechaStr,day,month,year;
    float tiempo,calorias;
    int idImage,currentMonth,currentYear,currentDay,validation;
    Calendar instance;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caloriecount_exercise);
        inicializarFirebase();
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }

        instance = Calendar.getInstance();
        currentDay = instance.get(Calendar.DAY_OF_WEEK);
        currentMonth = instance.get(Calendar.MONTH);
        currentYear = instance.get(Calendar.YEAR);

        spinner = (Spinner) findViewById(R.id.spinnerEx);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                range = spinner.getSelectedItem().toString();
                setListExercise();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void setListExercise(){
        databaseReference.child("caloriasQuemadas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaNomEjercicios = new ArrayList<String>();
                listaFecha = new ArrayList<String>();
                listaTiempo = new ArrayList<Float>();
                listaCalorias = new ArrayList<Float>();
                listaImagenes = new ArrayList<Integer>();

                //int i = 0;
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    nombreEjercicio = areaSnapshot.child("exercise").getValue().toString();
                    fecha = new StringBuilder();
                    day = areaSnapshot.child("date").child("day").getValue(Integer.class).toString();
                    month = areaSnapshot.child("date").child("month").getValue(Integer.class).toString();
                    year = areaSnapshot.child("date").child("year").getValue(Integer.class).toString();

                    fechaStr = day + "/" + month + "/" + year;
                    tiempo = areaSnapshot.child("tiempo").getValue(Float.class);
                    calorias = areaSnapshot.child("cantCalorie").getValue(Float.class);

                    emailAux = areaSnapshot.child("usuario").getValue().toString();
                    if(emailAux.equals(email)){
                        validation = 0;

                        switch (range) {
                            case "Hoy":
                                if(Integer.parseInt(day) == currentDay && Integer.parseInt(month) == currentMonth && Integer.parseInt(year) == currentYear)
                                    validation = 1;
                                break;
                            case "Semana":
                                if(Integer.parseInt(year) == currentYear) //Falta modificar esta parte
                                    validation = 1;
                                break;
                            case "Mes":
                                if(Integer.parseInt(month) == currentMonth && Integer.parseInt(year) == currentYear)
                                    validation = 1;
                                break;
                            case "Año":
                                if(Integer.parseInt(year) == currentYear)
                                    validation = 1;
                                break;
                        }

                        if (validation == 1) {
                            listaNomEjercicios.add(nombreEjercicio);
                            listaFecha.add(fechaStr);
                            listaTiempo.add(tiempo);
                            listaCalorias.add(calorias);

                            switch(nombreEjercicio) {
                                case "Abdominales": idImage = R.drawable.img_abdominales; break;
                                case "Caminadora": idImage = R.drawable.img_caminadora; break;
                                case "Caminar": idImage = R.drawable.img_caminar; break;
                                case "Ciclismo": idImage = R.drawable.img_ciclismo; break;
                                case "Correr": idImage = R.drawable.img_correr; break;
                                case "Fútbol": idImage = R.drawable.img_futbol; break;
                                case "Pesas": idImage = R.drawable.img_pesas; break;

                            }
                            listaImagenes.add(idImage);
                        }

                    }

                }

                List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
                for(int i=0;i<listaNomEjercicios.size();i++){
                    HashMap<String, String> hm = new HashMap<String,String>();
                    hm.put("exercise",listaNomEjercicios.get(i));
                    hm.put("fecha",listaFecha.get(i));
                    hm.put("tiempo",listaTiempo.get(i).toString());
                    hm.put("calorias",listaCalorias.get(i).toString());
                    hm.put("image",listaImagenes.get(i).toString());
                    aList.add(hm);
                }


                // Keys used in Hashmap
                String[] from = {"image","exercise","fecha","tiempo","calorias"};
                // Ids of views in listview_layout
                int[] to = {R.id.imageViewEjercicio,R.id.textViewEjercicio,R.id.textViewFechaInicial, R.id.textViewTiempo,R.id.textViewCalorias};

                // Instantiating an adapter to store each items
                // R.layout.listview_layout defines the layout of each item
                SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.custom_layout_exercises, from, to);
                listViewExercises = findViewById(R.id.listViewExercises);
                listViewExercises.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
