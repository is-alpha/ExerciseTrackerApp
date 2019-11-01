package com.example.exercisetrackerapp.ui.burnedCalories;

import android.content.Intent;
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
import com.example.exercisetrackerapp.ui.profile.ProfileActivity;
import com.example.exercisetrackerapp.ui.registro.DatosRegistro;
import com.example.exercisetrackerapp.ui.registro.RegistroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BurnedCaloriesActivity extends AppCompatActivity {
    private Button mBtCancelar;
    private Button mRegistrar;
    //private FirebaseAuth firebaseAuth;
   // private DatabaseReference mDatabase;
    private EditText fechaInicio, caloriasQuemadas;

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

        Spinner spinner = (Spinner) findViewById(R.id.spinnerExercise);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.exercise, R.layout.spinner_item);

    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void registrarCaloriasManual(){


        //String id = mDatabase.push().getKey();


        validacion();
        /*
         Persona p = new Persona();
        p.setUid(UUID.randomUUID().toString());
        p.setNombre(nombre);
        p.setApellido(app);
        p.setCorreo(correo);
        p.setPassword(password);
        databaseReference.child("Persona").child(p.getUid()).setValue(p);
        Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
        limpiarCajas();
        */

        /*
        firebaseAuth.createUserWithEmailAndPassword(correo,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {

                    Toast.makeText(RegistroActivity.this,"Usuario registrado",Toast.LENGTH_LONG).show();

                    i=true;
                }
                else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(RegistroActivity.this,"Usuario YA Registrado Ingrese Otro",Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        if (contrasena.length() <= 5) {
                            Toast.makeText(RegistroActivity.this, "La contraseña debe tener mas de 5 caracteres", Toast.LENGTH_LONG).show();
                            return ;
                        }
                        if (!correo.contains("@gmail")) {
                            Toast.makeText(RegistroActivity.this, "Ingrese correo @gmail ", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {

                            Toast.makeText(RegistroActivity.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }

            }
        });
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

    private void validacion(){
        String sFechaInicio = fechaInicio.getText().toString();
        String sCaloriasQuemadas = caloriasQuemadas.getText().toString();

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
