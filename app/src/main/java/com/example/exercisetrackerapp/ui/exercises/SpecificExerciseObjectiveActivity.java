package com.example.exercisetrackerapp.ui.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Date;
import com.example.exercisetrackerapp.ui.burnedCalories.BurnedCaloriesActivity;
import com.example.exercisetrackerapp.ui.consumedCalories.ConsumedCalories;
import com.example.exercisetrackerapp.ui.exerciseRoutine.TrackExerciseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SpecificExerciseObjectiveActivity extends AppCompatActivity {

    private TextView textViewEjercicio,textViewObjetivo;
    ImageView imageViewImagen;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    DatabaseReference obj;
    Date fecha;
    int i=0,j=0;
    Button sumarCal,restarCal,sumarExtra,restarExtra,botonCancelarCalorias,botonGuardarConsCal;
    String email="",uid,id,userID,emailAux,objetivo, mArray[];
    int year, month,dayOfMonth,idImage;

    Spinner mySpinner;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Button botonTrackExercise;
    ImageButton imageButtonRestar, imageButtonSumar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_exercise);

        inicializarFirebase();

        ////AQUI SE AGARRA EL EMAIL DEL USUARIO QUE INGRESO SESION
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }

        textViewEjercicio = (TextView) findViewById(R.id.textViewEjercicio);
        imageViewImagen  = (ImageView) findViewById(R.id.imageViewImagen);
        textViewObjetivo = (TextView) findViewById(R.id.textViewObjetivo);
        imageButtonRestar = (ImageButton)  findViewById(R.id.imageButtonRestar);
        imageButtonSumar = (ImageButton) findViewById(R.id.imageButtonSumar);

        databaseReference.child("ejercicioActual").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();
                    if(emailAux.equals(email)){

                        switch(areaSnapshot.child("exercise").getValue().toString()) {
                            case "Abdominales": idImage = R.drawable.img_abdominales; break;
                            case "Caminadora": idImage = R.drawable.img_caminadora; break;
                            case "Caminar": idImage = R.drawable.img_caminar; break;
                            case "Ciclismo": idImage = R.drawable.img_ciclismo; break;
                            case "Correr": idImage = R.drawable.img_correr; break;
                            case "Fútbol": idImage = R.drawable.img_futbol; break;
                            case "Pesas": idImage = R.drawable.img_pesas; break;

                        }

                        textViewEjercicio.setText(areaSnapshot.child("exercise").getValue().toString());
                        imageViewImagen.setImageResource(idImage);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mySpinner = (Spinner) findViewById(R.id.spinnerObjetivo);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);

                mArray = getResources().getStringArray(R.array.objetivos);
                objetivo = mySpinner.getSelectedItem().toString();
                /*
                databaseReference.child("ejercicioActual").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                            emailAux = areaSnapshot.child("usuario").getValue().toString();
                            if(emailAux.equals(email)){
                                switch(objetivo) {
                                    case "Objetivo de tiempo": textViewObjetivo.setText(areaSnapshot.child("objTiempo").getValue().toString()); break;
                                    case "Objetivo de distancia": textViewObjetivo.setText(areaSnapshot.child("objDistancia").getValue().toString()); break;
                                    case "Objetivo de repeticiones": textViewObjetivo.setText(areaSnapshot.child("objRep").getValue().toString()); break;

                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });*/
                /*
                switch(objetivo) {
                    case "Objetivo de tiempo": textViewObjetivo.setText("TIEMPO"); break;
                    case "Objetivo de distancia": textViewObjetivo.setText("DISTANCIA"); break;
                    case "Objetivo de repeticiones": textViewObjetivo.setText("REPETICIONES"); break;

                }*/

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        imageButtonRestar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i = Integer.parseInt(textViewObjetivo.getText().toString());
                if(i>0){
                    i-=1;
                }
                textViewObjetivo.setText(Integer.toString(i));

                databaseReference.child("ejercicioActual").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                            emailAux = areaSnapshot.child("usuario").getValue().toString();
                            if(emailAux.equals(email)){
                                switch(objetivo) {
                                    case "Objetivo de tiempo": obj = areaSnapshot.getRef().child("objTiempo"); break;
                                    case "Objetivo de distancia": obj = areaSnapshot.getRef().child("objDistancia"); break;
                                    case "Objetivo de repeticiones": obj = areaSnapshot.getRef().child("objRep"); break;

                                }
                                obj.setValue(textViewObjetivo.getText().toString());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        });

        imageButtonSumar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i = Integer.parseInt(textViewObjetivo.getText().toString());
                i++;
                /*if(i>0){
                    i-=1;
                }*/
                textViewObjetivo.setText(Integer.toString(i));

                databaseReference.child("ejercicioActual").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                            emailAux = areaSnapshot.child("usuario").getValue().toString();
                            if(emailAux.equals(email)){
                                switch(objetivo) {
                                    case "Objetivo de tiempo": obj = areaSnapshot.getRef().child("objTiempo"); break;
                                    case "Objetivo de distancia": obj = areaSnapshot.getRef().child("objDistancia"); break;
                                    case "Objetivo de repeticiones": obj = areaSnapshot.getRef().child("objRep"); break;

                                }
                                obj.setValue(textViewObjetivo.getText().toString());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        botonTrackExercise = (Button) findViewById(R.id.botonTrackExercise);
        botonTrackExercise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchStopWatch();
            }
        });

    }

    private void launchStopWatch() {
        Intent intent = new Intent(this, TrackExerciseActivity.class);
        startActivity(intent);
    }


    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}


