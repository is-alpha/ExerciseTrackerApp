package com.example.exercisetrackerapp.ui.registro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.SideMenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class RegistroActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private Button mBtRegresar;
    private Button mRegistrar;
    private ImageButton cal;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;// Referencia a la base de Datos firebase
    private EditText nombre,email,contrasena,validacion,fecha,altura,peso,ocupacion,genero;
    private String gen;
    String currentDateString;
    com.example.exercisetrackerapp.data.model.Date fechaN;
    Date fechaActual;
    boolean i=false;
    Spinner spinner;

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
        spinner = (Spinner) findViewById(R.id.spinnerGenero);

        mBtRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cal = (ImageButton) findViewById(R.id.imageCal);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        mRegistrar = (Button) findViewById(R.id.botonRegistrarse);
        mRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gen = spinner.getSelectedItem().toString();
                registro();

            }
        });


       /* ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genero, R.layout.spinner_item);
        genero = adapter.getContext().toString();*/

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
         currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.textViewFechaNac);
        textView.setText(currentDateString);
        fechaN = new com.example.exercisetrackerapp.data.model.Date(dayOfMonth,month,year);
    }

    private void registro(){

        String  name = nombre.getText().toString();
        final String  correo = email.getText().toString();
        String password = contrasena.getText().toString();
        String vcontrasena= validacion.getText().toString();
        String trabajo=ocupacion.getText().toString();
        float hsueno = 0, calQuemadas = 0, calConsumidas = 0;
        String gener = gen;
        Date fecha;
        String id = mDatabase.push().getKey();
        String p=peso.getText().toString();
        String a=altura.getText().toString();
        float height;
        float weight;
       // int date = Integer.parseInt(fecha.getText().toString());

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Ingrese nombre",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(correo)){
            Toast.makeText(this,"Ingrese correo",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Ingrese contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(vcontrasena)){
            Toast.makeText(this,"Valide contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(a)){
            Toast.makeText(this,"Ingrese altura",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(p)){
            Toast.makeText(this,"Ingrese peso",Toast.LENGTH_LONG).show();
            return;
        }

        else if(TextUtils.isEmpty(trabajo)){
            Toast.makeText(this,"Ingrese ocupacion",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(currentDateString)){
            Toast.makeText(this,"Ingrese fecha de nacimiento",Toast.LENGTH_LONG).show();
            return;
        }
        height = Float.parseFloat(a);
        weight=Float.parseFloat(p);
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
                        Toast.makeText(RegistroActivity.this,"Usuario ya registrado ingrese otro",Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        if (contrasena.length() <= 5) {
                            Toast.makeText(RegistroActivity.this, "La contraseña debe tener mas de 5 caracteres", Toast.LENGTH_LONG).show();
                            return ;
                        }
                        if (!(correo.contains("@gmail") || (correo.contains("@uabc")) )) {
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
        else if (!(correo.contains("@gmail") || (correo.contains("@uabc")) )) {
            return;
        }
        else {
            //java.util.Date date = new java.util.Date();
            //LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            //com.example.exercisetrackerapp.data.model.Date creacionCuenta = new com.example.exercisetrackerapp.data.model.Date(localDate.getDayOfMonth(),localDate.getMonthValue(),localDate.getYear());

            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            //cal.setTime(date);
            com.example.exercisetrackerapp.data.model.Date creacionCuenta = new com.example.exercisetrackerapp.data.model.Date( cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR));

            DatosRegistro data = new DatosRegistro(id, name, correo, password, trabajo, fechaN, height, weight,hsueno,calQuemadas,calConsumidas,gener,creacionCuenta,weight);
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
       Intent intent = new Intent(this, SideMenuActivity.class);
       //intent.putExtra(name);
       startActivity(intent);
   }


}





