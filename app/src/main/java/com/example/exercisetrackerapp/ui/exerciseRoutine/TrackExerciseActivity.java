package com.example.exercisetrackerapp.ui.exerciseRoutine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Date;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TrackExerciseActivity extends AppCompatActivity{

    private TextView textViewEjercicio;
    ImageView imageViewImagen;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    DatabaseReference obj;
    Date fecha;
    int i=0,j=0;
    String email="",uid,id,userID,emailAux,objetivo, mArray[];
    int year, month,dayOfMonth,idImage;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_exercise);

        inicializarFirebase();

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

    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}
