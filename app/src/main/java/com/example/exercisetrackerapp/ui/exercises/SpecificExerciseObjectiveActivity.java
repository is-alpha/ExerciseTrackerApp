package com.example.exercisetrackerapp.ui.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    private TextView textViewEjercicio;
    ImageView imageViewImagen;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    Date fecha;
    int i=0,j=0;
    String cal;
    Button sumarCal,restarCal,sumarExtra,restarExtra,botonCancelarCalorias,botonGuardarConsCal;
    String email="",uid,id,userID,emailAux;
    int year, month,dayOfMonth,idImage;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Button botonTrackExercise;

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
        /*
        botonTrackExercise = (Button) findViewById(R.id.botonTrackExercise);
        botonTrackExercise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchStopWatch();
            }
        });


        textViewFechaCalorias = (TextView) findViewById(R.id.textViewFechaCalorias);
        caloriasManual  = (TextView) findViewById(R.id.editText_caloriasManual);
        caloriasManualExtra  = (TextView) findViewById(R.id.editText_caloriasManualExtra);
        sumarCal  = (Button) findViewById(R.id.boton_sumarCalorias);
        restarCal  = (Button) findViewById(R.id.boton_restarCalorias);
        sumarExtra  = (Button) findViewById(R.id.boton_sumarCaloriasExtras);
        restarExtra  = (Button) findViewById(R.id.boton_restarCaloriasExtras);
        botonCancelarCalorias  = (Button) findViewById(R.id.botonCancelarCalorias);
        botonGuardarConsCal = (Button) findViewById(R.id.botonGuardarConsCal);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        date = dateFormat.format(calendar.getTime());
        textViewFechaCalorias.setText(date);



        id = databaseReference.push().getKey();

        botonGuardarConsCal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registrarCaloriasManual();
            }
        });

        sumarCal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i = Integer.parseInt(caloriasManual.getText().toString());
                i+=50;
                cal = Integer.toString(i);
                caloriasManual.setText(cal);
            }
        });

        sumarExtra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                j = Integer.parseInt(caloriasManualExtra.getText().toString());
                j+=50;
                cal = Integer.toString(j);
                caloriasManualExtra.setText(cal);
            }
        });
        restarCal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i = Integer.parseInt(caloriasManual.getText().toString());
                if(i>50){
                    i-=50;
                }
                cal = Integer.toString(i);
                caloriasManual.setText(cal);

            }
        });

        restarExtra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                j = Integer.parseInt(caloriasManualExtra.getText().toString());
                if(j>50){
                    j-=50;
                }
                cal = Integer.toString(j);
                caloriasManualExtra.setText(cal);
            }
        });

        botonCancelarCalorias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        */

    }

    private void launchStopWatch() {
        Intent intent = new Intent(this, TrackExerciseActivity.class);
        startActivity(intent);
    }

    /*
    private void registrarEjercicioActual(){

        String sFechaInicio = textViewFechaCalorias.getText().toString();
        String sCaloriasConsumidas = caloriasManual.getText().toString();
        String sCaloriasConsumidasExtra = caloriasManualExtra.getText().toString();

        Calendar c = Calendar.getInstance();;
        fecha = new Date(c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.MONTH),c.get(Calendar.YEAR));
        if(validacion(sFechaInicio,sCaloriasConsumidas,sCaloriasConsumidasExtra)==1) {

            ConsumedCalories data = new ConsumedCalories(email, Integer.parseInt(sCaloriasConsumidas), Integer.parseInt(sCaloriasConsumidasExtra), fecha);
            databaseReference.child("calConsumidas").child(id).setValue(data);
            Toast.makeText(this, "Registro Exitoso! ", Toast.LENGTH_LONG).show();
            finish();
        }

    }
    */
    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}


