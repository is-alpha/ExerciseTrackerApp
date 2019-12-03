package com.example.exercisetrackerapp.ui.goals;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Date;
import com.example.exercisetrackerapp.ui.burnedCalories.BurnedCalories;
import com.example.exercisetrackerapp.ui.burnedCalories.BurnedCaloriesActivity;
import com.example.exercisetrackerapp.ui.burnedCalories.DatePickerFragment;
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

public class GoalsRegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Button mBtCancelar;
    private Button mRegistrar;
    private EditText editTextTiempo,editTextCaloriasGastadas;
    private TextView textViewDate, textViewDate2;
    private Spinner spinner;
    String email="",uid,id,userID;
    String currentDateString;
    Date fecha,fecha2;
    ImageView imageViewIni, imageViewFin;
    int idTextView, idFecha;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ///CON ESTA REFERENCIA
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_exercise_goals);  inicializarFirebase();

        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }

        id = databaseReference.push().getKey();

        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewDate2 = (TextView) findViewById(R.id.textViewDate2);
        editTextTiempo = (EditText) findViewById(R.id.editTextTiempo);
        editTextCaloriasGastadas = (EditText) findViewById(R.id.editTextCaloriasGastadas);

        mBtCancelar = (Button) findViewById(R.id.botonCancelarMetas);
        mBtCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRegistrar = (Button) findViewById(R.id.botonGuardarMetas);
        mRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registrarMetas();
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

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(GoalsRegisterActivity.this, android.R.layout.simple_spinner_item, listaEjercicios);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }
        );

        imageViewIni = (ImageButton) findViewById(R.id.imageViewIni);
        imageViewIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

                idFecha = 1;
                idTextView = R.id.textViewDate;
            }
        });

        imageViewFin = (ImageButton) findViewById(R.id.imageViewFin);
        imageViewFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker2 = new DatePickerFragment();
                datePicker2.show(getSupportFragmentManager(), "date picker");

                idFecha = 2;
                idTextView = R.id.textViewDate2;
            }
        });
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
        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());


        TextView textView = (TextView) findViewById(idTextView);
        textView.setText(currentDateString);
        if(idFecha==1)
            fecha = new Date(dayOfMonth,month,year);
        else if(idFecha == 2)
            fecha2 = new Date(dayOfMonth,month,year);

    }

    private void registrarMetas(){

        String ejercicioSeleccionado = spinner.getSelectedItem().toString();
        String sFechaInicio = textViewDate.getText().toString();
        String sFechaFin = textViewDate2.getText().toString();
        String tiempo =  editTextTiempo.getText().toString();
        String calorias = editTextCaloriasGastadas.getText().toString();


        if(validacion(sFechaInicio,sFechaFin,tiempo,calorias )==1) {

           Goal data = new Goal(email, ejercicioSeleccionado,fecha,fecha2, Float.parseFloat(tiempo) ,Float.parseFloat(calorias),false);
            databaseReference.child("metas").child(id).setValue(data);
            limpiarTextBox();
            Toast.makeText(this, "Registro Exitoso! ", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void limpiarTextBox(){
        textViewDate.setText("");
        textViewDate2.setText("");
        editTextTiempo.setText("");
        editTextCaloriasGastadas.setText("");

    }

    private int validacion(String sFechaInicio,String sFechaFin, String tiempo,String calorias){

        if(TextUtils.isEmpty(sFechaInicio)){
            Toast.makeText(this,"Ingrese Fecha de Inicio",Toast.LENGTH_LONG).show();
            return 0;
        }

        if(TextUtils.isEmpty(sFechaFin)){
            Toast.makeText(this,"Ingrese Fecha Final",Toast.LENGTH_LONG).show();
            return 0;
        }
        if(TextUtils.isEmpty(tiempo)){
            Toast.makeText(this,"Ingrese el Tiempo",Toast.LENGTH_LONG).show();
            return 0;
        }

        if(TextUtils.isEmpty(calorias)){
            Toast.makeText(this,"Ingrese las calorias",Toast.LENGTH_LONG).show();
            return 0;
        }

        return 1;
    }
}
