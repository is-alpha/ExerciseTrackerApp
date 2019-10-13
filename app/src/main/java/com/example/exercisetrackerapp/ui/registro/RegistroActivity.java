package com.example.exercisetrackerapp.ui.registro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.profile.ProfileActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {
    private Button mBtRegresar;
    private Button mRegistrar;
    private DatabaseReference mDatabase;// Referencia a la base de Datos firebase
    private EditText nombre,email,contrasena,validacion,fecha,altura,peso,ocupacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //mDatabase = FirebaseDatabase.getInstance().getReference("DatosRegistro");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mBtRegresar = (Button) findViewById(R.id.botonRegresar);
        nombre = (EditText) findViewById(R.id.nombre);
        email = (EditText) findViewById(R.id.email);
        contrasena = (EditText) findViewById(R.id.contrasena);
        validacion = (EditText) findViewById(R.id.validacion);
        fecha = (EditText) findViewById(R.id.editTextFechaNac);
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
        String  correo = email.getText().toString();
        String password = contrasena.getText().toString();
        String vcontrasena= validacion.getText().toString();
        String trabajo=ocupacion.getText().toString();
        String id = mDatabase.push().getKey();
        int date = Integer.parseInt(fecha.getText().toString());
        float height = Float.parseFloat(altura.getText().toString());
        float weight=Float.parseFloat(peso.getText().toString());
        DatosRegistro data = new DatosRegistro(id,name,correo,password,vcontrasena,trabajo,date,height,weight);
        mDatabase.child("users").child(id).setValue(data);
       // mDatabase reg = new mDatabase(id,name,correo,password,vcontrasena,trabajo,height,weight);
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





