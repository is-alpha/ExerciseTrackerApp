package com.example.exercisetrackerapp.ui.registro;

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
import com.example.exercisetrackerapp.ui.login.LoginActivity;
import com.example.exercisetrackerapp.ui.profile.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {
    private Button mBtRegresar;
    private Button mRegistrar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;// Referencia a la base de Datos firebase
    private EditText nombre,email,contrasena,validacion,fecha,altura,peso,ocupacion;
    boolean i=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //mDatabase = FirebaseDatabase.getInstance().getReference("DatosRegistro");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        mBtRegresar = (Button) findViewById(R.id.botonRegresar);
        nombre = (EditText) findViewById(R.id.nombre);
        email = (EditText) findViewById(R.id.email);
        contrasena = (EditText) findViewById(R.id.contrasena);
        validacion = (EditText) findViewById(R.id.validacion);
       // fecha = (EditText) findViewById(R.id.editTextFechaNac);
        altura = (EditText) findViewById(R.id.altura);
        peso = (EditText) findViewById(R.id.peso);
        ocupacion = (EditText) findViewById(R.id.editTextOcupacion);
        mBtRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRegistrar = (Button) findViewById(R.id.botonRegistrarse);
        mRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
              //  launchProfile();
                registro();

            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinnerGenero);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genero, R.layout.spinner_item);

    }


    private void registro(){

        String  name = nombre.getText().toString();
        final String  correo = email.getText().toString();
        String password = contrasena.getText().toString();
        String vcontrasena= validacion.getText().toString();
        String trabajo=ocupacion.getText().toString();
        String id = mDatabase.push().getKey();
       // int date = Integer.parseInt(fecha.getText().toString());
        float height = Float.parseFloat(altura.getText().toString());
        float weight=Float.parseFloat(peso.getText().toString());
        if(TextUtils.isEmpty(correo)){
            Toast.makeText(this,"Ingrese correo",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Ingrese contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(vcontrasena)){
            Toast.makeText(this,"Valide contraseña",Toast.LENGTH_LONG).show();
            return;
        }
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
        if (contrasena.length() <= 5) {
            return ;
        }
        if (!correo.contains("@gmail")) {
            return;
        }
        else {
            DatosRegistro data = new DatosRegistro(id, name, correo, password, vcontrasena, trabajo, 10, height, weight);
            mDatabase.child("users").child(id).setValue(data);
            launchProfile();
        }




    }
   /* Spinner spinner = (Spinner) findViewById(R.id.spinnerGenero);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genero, R.layout.spinner_item);

    //spinner.setAdapter(new ArrayAdapter(this, R.id.spinner_item));

    spinner.setAdapter(new ArrayAdapter.createFromResource(this, R.array.genero, R.layout.spinner_item));
*/
    //adapter.setSpinnerMode(R.layout.dropdown);
   // spinner.setAdapter(adapter);


   private void launchProfile() {
       Intent intent = new Intent(this, ProfileActivity.class);
       startActivity(intent);
   }

}





