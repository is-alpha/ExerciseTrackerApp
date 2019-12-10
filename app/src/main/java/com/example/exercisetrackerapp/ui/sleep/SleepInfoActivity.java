package com.example.exercisetrackerapp.ui.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.consumedCalories.ConsumedCalorieRegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SleepInfoActivity extends AppCompatActivity {

    private Button botonEditarHoras;
    private TextView textViewFecha,textViewHorasSDiarias,textViewPromedioHoras,textViewCantidadCalorias,textViewHorasSueno;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    public String email="",uid,id,userID, horasSueno="-1",emailAux;
    double peso,calorias = 0.42 ,calTotal,libra = 2.20462;
    int horas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours_sleep);

        textViewFecha = (TextView) findViewById(R.id.textViewFecha);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        date = dateFormat.format(calendar.getTime());
        textViewFecha.setText(date);

        inicializarFirebase();

        ////AQUI SE AGARRA EL EMAIL DEL USUARIO QUE INGRESO SESION
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }


        textViewHorasSDiarias = (TextView) findViewById(R.id.textViewHorasSDiarias);
        textViewPromedioHoras = (TextView) findViewById(R.id.textViewPromedioHoras);
        textViewCantidadCalorias  = (TextView) findViewById(R.id.textViewCantidadCalorias );
        textViewHorasSueno = (TextView) findViewById(R.id.textViewHorasSueno);

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("correo").getValue().toString();
                    if(emailAux.equals(email)){
                        horasSueno = areaSnapshot.child("hsueno").getValue().toString();
                        textViewHorasSDiarias.setText(horasSueno);
                        textViewPromedioHoras.setText(horasSueno);
                        peso = Float.parseFloat(areaSnapshot.child("peso").getValue().toString());
                        calTotal = libra*calorias*peso*Float.parseFloat(horasSueno);
                        DecimalFormat df = new DecimalFormat("0.00");
                        textViewCantidadCalorias.setText(String.valueOf(df.format(calTotal)));
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        databaseReference.child("horSueno").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("correo").getValue().toString();
                    if(emailAux.equals(email)){
                        horas = areaSnapshot.child("horas").getValue(Integer.class);
                        horas += areaSnapshot.child("horasExtras").getValue(Integer.class);
                        textViewHorasSueno.setText(String.valueOf(horas));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        botonEditarHoras  = (Button)findViewById(R.id.botonEditarHoras);
        botonEditarHoras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchSuenoRegisterActivity();
            }
        });
    }

    private void launchSuenoRegisterActivity() {
        Intent intent = new Intent(this, RegisterSleepActivity.class);
        startActivity(intent);
    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
