package com.example.exercisetrackerapp.ui.consumedCalories;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.burnedCalories.BurnedCaloriesActivity;
import com.example.exercisetrackerapp.ui.editProfile.EditPasswordActivity;
import com.example.exercisetrackerapp.ui.profile.ProfileActivity;
import com.example.exercisetrackerapp.ui.registro.DatosRegistro;
import com.example.exercisetrackerapp.ui.sleep.SleepInfoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ConsumedCalorieRegisterActivity extends AppCompatActivity {
    public Button botonCancelarCaloriasConsumReg,botonGuardarCalConsum;
    public EditText editTextCalConsumD;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public String email="",uid,id,userID, calConsum="-1",emailAux;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_calorie_consume);

        inicializarFirebase();

        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }
        calConsum = "10";
        editTextCalConsumD = (EditText) findViewById(R.id.editTextCalConsumD);

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("correo").getValue().toString();
                    if(emailAux.equals(email)){
                        calConsum = areaSnapshot.child("calConsumidas").getValue().toString();
                        editTextCalConsumD.setText(calConsum);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        botonCancelarCaloriasConsumReg = (Button) findViewById(R.id.botonCancelarCaloriasConsumReg);
        botonCancelarCaloriasConsumReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        botonGuardarCalConsum = (Button) findViewById(R.id.botonGuardarCalConsum);
        botonGuardarCalConsum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                guardarCaloriasConsum();

            }
        });


    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void guardarCaloriasConsum(){

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("correo").getValue().toString();
                    if(emailAux.equals(email)){

                        DatabaseReference calConsumRef = areaSnapshot.getRef().child("calConsumidas");
                        calConsumRef.setValue(editTextCalConsumD.getText().toString());
                        editTextCalConsumD.setText("");

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if(editTextCalConsumD.getText().toString()!= "") {
            Toast.makeText(this, "Registro Exitoso! ", Toast.LENGTH_LONG).show();
            launchConsumedCalorieInfoActivity();
        }

    }

    private void launchConsumedCalorieInfoActivity() {
        Intent intent = new Intent(this, ConsumedCalorieInfoActivity.class);
        startActivity(intent);
    }
}

