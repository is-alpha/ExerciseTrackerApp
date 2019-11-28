package com.example.exercisetrackerapp.ui.consumedCalories;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.registro.RegistroActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConsumedCalorieInfoActivity extends AppCompatActivity {

    private Button editarCaloriasConsumidas;
    private TextView textViewFechaCal,textViewCalConsum,textViewCaloriasD;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    public String email="",uid,id,userID, calConsum="-1",emailAux;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_consumed_info);

        textViewFechaCal = (TextView) findViewById(R.id.textViewFechaCal);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        date = dateFormat.format(calendar.getTime());
        textViewFechaCal.setText(date);

        inicializarFirebase();

        ////AQUI SE AGARRA EL EMAIL DEL USUARIO QUE INGRESO SESION
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }
        textViewCalConsum = (TextView) findViewById(R.id.textViewCalConsum);
        textViewCaloriasD = (TextView) findViewById(R.id.textViewCaloriasD);
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("correo").getValue().toString();
                    if(emailAux.equals(email)){
                        calConsum = areaSnapshot.child("calConsumidas").getValue().toString();
                        textViewCalConsum.setText(calConsum);
                        textViewCaloriasD.setText(calConsum);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        editarCaloriasConsumidas  = (Button)findViewById(R.id.botonEditarCalorias);
        editarCaloriasConsumidas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchConsumedCalorieRegisterActivity();
            }
        });
    }

    private void launchConsumedCalorieRegisterActivity() {
        Intent intent = new Intent(this, ConsumedCalorieRegisterActivity.class);
        startActivity(intent);
    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
