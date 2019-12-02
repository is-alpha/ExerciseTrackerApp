package com.example.exercisetrackerapp.ui.weight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Date;
import com.example.exercisetrackerapp.ui.consumedCalories.ConsumedCalories;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class WeightActivity extends AppCompatActivity {

    int i=0,j=0;
    String peso;
    Button botonRestarPeso,botonSumarPeso,botonCancelarPeso,botonGuardarPeso;
    String email="",uid,id,userID;
    EditText editText_peso;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideal_weight);

        botonSumarPeso  = (Button) findViewById(R.id.botonSumarPeso);
        botonRestarPeso  = (Button) findViewById(R.id.botonRestarPeso);
        botonCancelarPeso = (Button) findViewById(R.id.botonCancelarPeso);
        botonGuardarPeso  = (Button) findViewById(R.id.botonGuardarPeso);
        editText_peso = (EditText)  findViewById(R.id.editText_peso);

        inicializarFirebase();

        ////AQUI SE AGARRA EL EMAIL DEL USUARIO QUE INGRESO SESION
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }
        id = databaseReference.push().getKey();

        botonSumarPeso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i = Integer.parseInt(editText_peso.getText().toString());
                i+=5;
                peso = Integer.toString(i);
                editText_peso.setText(peso);
            }
        });


        botonRestarPeso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i = Integer.parseInt(editText_peso.getText().toString());
                if(i>5){
                    i-=5;
                }
                peso = Integer.toString(i);
                editText_peso.setText(peso);

            }
        });


        botonGuardarPeso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registrarPeso();
            }
        });


        botonCancelarPeso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               finish();
            }
        });

    }
    private void registrarPeso(){

        String sPeso = editText_peso.getText().toString();


        if(validacion(sPeso)==1) {

            IdealWeight data = new IdealWeight(email, Integer.parseInt(sPeso));
            databaseReference.child("peso").child(id).setValue(data);
            Toast.makeText(this, "Registro Exitoso! ", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private int validacion(String sPeso){

        if(TextUtils.isEmpty(sPeso)){
            Toast.makeText(this,"Ingrese Peso Ideal",Toast.LENGTH_LONG).show();
            return 0;
        }

        return 1;
    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
