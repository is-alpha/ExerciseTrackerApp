package com.example.exercisetrackerapp.ui.burnedCalories;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Date;
import com.example.exercisetrackerapp.ui.login.LoginActivity;
import com.example.exercisetrackerapp.ui.profile.ProfileActivity;
import com.example.exercisetrackerapp.ui.registro.DatosRegistro;
import com.example.exercisetrackerapp.ui.registro.RegistroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BurnedCaloriesActivity extends AppCompatActivity {
    private Button mBtCancelar;
    private Button mRegistrar;
    //private FirebaseAuth firebaseAuth;
    // private DatabaseReference mDatabase;
    private EditText fechaInicio, caloriasQuemadas;
    private Spinner spinner;
    String correo;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burnedcalories_registration);

        //mDatabase = FirebaseDatabase.getInstance().getReference("DatosRegistro");
        //mDatabase = FirebaseDatabase.getInstance().getReference();
        //firebaseAuth = FirebaseAuth.getInstance();

        inicializarFirebase();

        fechaInicio = (EditText) findViewById(R.id.editTextDate);
        caloriasQuemadas = (EditText) findViewById(R.id.editTextCaloriasGastadas);

        mBtCancelar = (Button) findViewById(R.id.botonCancelar);
        mBtCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRegistrar = (Button) findViewById(R.id.botonRegistrarCalQuemadas);
        mRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registrarCaloriasManual();
            }
        });

        spinner = (Spinner) findViewById(R.id.spinnerExercise);
        databaseReference.child("ejercicio").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> listaEjercicios = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String consultaEjercicios = areaSnapshot.child("nombre").getValue(String.class);
                    listaEjercicios.add(consultaEjercicios);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BurnedCaloriesActivity.this, android.R.layout.simple_spinner_item, listaEjercicios);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
            }
            // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, listaEjercicios, R.layout.spinner_item);

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }
        );
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void registrarCaloriasManual(){

        String sFechaInicio = fechaInicio.getText().toString();
        String sCaloriasQuemadas = caloriasQuemadas.getText().toString();
        String ejercicioSeleccionado = spinner.getSelectedItem().toString();

        validacion(sFechaInicio,sCaloriasQuemadas);
        Toast.makeText(this,"fecha: " + sFechaInicio + " calorias: "+sCaloriasQuemadas + " ejercicio: " + ejercicioSeleccionado,Toast.LENGTH_LONG).show();
        limpiarTextBox();


        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String userID = sharedPref.getString("userID", "");
        Toast.makeText(this,"userID: " + userID,Toast.LENGTH_LONG).show();


        /*
        DatabaseReference databaseReference =
        DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
        */

        /*
        databaseReference.child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot areaSnapshot ;
                correo = areaSnapshot.child("correo").getValue(String.class);
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    correo = areaSnapshot.child("correo").getValue(String.class);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Toast.makeText(this,"correo: " + correo,Toast.LENGTH_LONG).show();
        */

        /*
        BurnedCalories data = new BurnedCalories(sCaloriasQuemadas ,sFechaInicio,exercise);
        mDatabase.child("users").child(id).setValue(data);
        //launchProfile();
         */

    }

    private void limpiarTextBox(){
        fechaInicio.setText("");
        caloriasQuemadas.setText("");
    }

    private void validacion(String sFechaInicio,String sCaloriasQuemadas){

        if(TextUtils.isEmpty(sFechaInicio)){
            Toast.makeText(this,"Ingrese Fecha de Inicio",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(sCaloriasQuemadas)){
            Toast.makeText(this,"Ingrese Calorias Quemadas",Toast.LENGTH_LONG).show();
            return;
        }

    }
}
