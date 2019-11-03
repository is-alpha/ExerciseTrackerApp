package com.example.exercisetrackerapp.ui.burnedCalories;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Date;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BurnedCaloriesActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private Button mBtCancelar;
    private Button mRegistrar;
    private EditText caloriasQuemadas;
    private TextView fechaInicio;
    private Spinner spinner;
    String email="",uid,id,userID;
    ImageButton button;
    Date fecha;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

   ///CON ESTA REFERENCIA
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burnedcalories_registration);

        inicializarFirebase();

       ////AQUI SE AGARRA EL EMAIL DEL USUARIO QUE INGRESO SESION
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
            //Toast.makeText(this, uid, Toast.LENGTH_LONG).show();
        }

        id = databaseReference.push().getKey();
        Toast.makeText(this,"Hola "+email,Toast.LENGTH_LONG).show();
        fechaInicio = (TextView) findViewById(R.id.textViewDate);
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }
        );

        button = (ImageButton) findViewById(R.id.botonCalendario);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        /*
        databaseReference.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userID = dataSnapshot.child("id").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }
        );*/
    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.textViewDate);
        textView.setText(currentDateString);
        fecha = new Date(dayOfMonth,month,year);

    }

    private void registrarCaloriasManual(){

        String sFechaInicio = fechaInicio.getText().toString();
        String sCaloriasQuemadas = caloriasQuemadas.getText().toString();
        String ejercicioSeleccionado = spinner.getSelectedItem().toString();

        if(validacion(sFechaInicio,sCaloriasQuemadas)==1) {
            //Toast.makeText(this, "fecha: " + uid + " calorias: " + sCaloriasQuemadas + " ejercicio: " + ejercicioSeleccionado, Toast.LENGTH_LONG).show();
            //Toast.makeText(this, userID, Toast.LENGTH_LONG).show();

            BurnedCalories data = new BurnedCalories(email, Float.parseFloat(sCaloriasQuemadas), fecha, ejercicioSeleccionado);
            databaseReference.child("caloriasQuemadas").child(id).setValue(data);
            limpiarTextBox();
            Toast.makeText(this, "Registro Exitoso! ", Toast.LENGTH_LONG).show();
        }

    }

    private void limpiarTextBox(){
        fechaInicio.setText("");
        caloriasQuemadas.setText("");
    }

    private int validacion(String sFechaInicio,String sCaloriasQuemadas){

        if(TextUtils.isEmpty(sFechaInicio)){
            Toast.makeText(this,"Ingrese Fecha de Inicio",Toast.LENGTH_LONG).show();
            return 0;
        }
        if(TextUtils.isEmpty(sCaloriasQuemadas)){
            Toast.makeText(this,"Ingrese Calorias Quemadas",Toast.LENGTH_LONG).show();
            return 0;
        }

        return 1;
    }
}
