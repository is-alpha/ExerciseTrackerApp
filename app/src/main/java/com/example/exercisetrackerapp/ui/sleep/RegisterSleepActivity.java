package com.example.exercisetrackerapp.ui.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterSleepActivity extends AppCompatActivity {

    public Button botonCancelarSuenoReg,botonGuardarSueno;
    public EditText editTexSueno;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public String email="",uid,id,userID, sueno="-1",emailAux;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hours_sleep);

        inicializarFirebase();

        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }
        editTexSueno = (EditText) findViewById(R.id.editTextHoras);

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("correo").getValue().toString();
                    if(emailAux.equals(email)){
                        sueno = areaSnapshot.child("hsueno").getValue().toString();
                        editTexSueno.setText(sueno);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        botonCancelarSuenoReg = (Button) findViewById(R.id.botonCancelarHorasRegistro);
        botonCancelarSuenoReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        botonGuardarSueno = (Button) findViewById(R.id.botonGuardarHorasRegistro);
        botonGuardarSueno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                guardarSueno();

            }
        });


    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void guardarSueno(){

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("correo").getValue().toString();
                    if(emailAux.equals(email)){

                        DatabaseReference suenoRef = areaSnapshot.getRef().child("hsueno");
                        suenoRef.setValue(editTexSueno.getText().toString());
                        editTexSueno.setText("");

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if(editTexSueno.getText().toString()!= "") {
            Toast.makeText(this, "Registro Exitoso! ", Toast.LENGTH_LONG).show();
            launchSleepInfoActivity();
        }

    }

    private void launchSleepInfoActivity() {
        Intent intent = new Intent(this, SleepInfoActivity.class);
        startActivity(intent);
    }
}

