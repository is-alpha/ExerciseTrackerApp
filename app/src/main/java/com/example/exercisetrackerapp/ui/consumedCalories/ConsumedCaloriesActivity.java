package com.example.exercisetrackerapp.ui.consumedCalories;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Date;
import com.example.exercisetrackerapp.ui.burnedCalories.BurnedCalories;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConsumedCaloriesActivity extends AppCompatActivity {

    private TextView caloriasManual,caloriasManualExtra,textViewFechaCalorias;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    Date fecha;
    int i=0,j=0;
    String cal;
    Button sumarCal,restarCal,sumarExtra,restarExtra,botonCancelar_HorasSManual,botonGuardarConsCal;
    String email="",uid,id,userID;
    int year, month,dayOfMonth;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumed_calories);

        textViewFechaCalorias = (TextView) findViewById(R.id.textViewFechaCalorias);
        caloriasManual  = (TextView) findViewById(R.id.editText_caloriasManual);
        caloriasManualExtra  = (TextView) findViewById(R.id.editText_caloriasManualExtra);
        sumarCal  = (Button) findViewById(R.id.boton_sumarCalorias);
        restarCal  = (Button) findViewById(R.id.boton_restarCalorias);
        sumarExtra  = (Button) findViewById(R.id.boton_sumarCaloriasExtras);
        restarExtra  = (Button) findViewById(R.id.boton_restarCaloriasExtras);
        botonCancelar_HorasSManual  = (Button) findViewById(R.id.botonCancelar_HorasSManual);
        botonGuardarConsCal = (Button) findViewById(R.id.botonGuardarConsCal);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        date = dateFormat.format(calendar.getTime());
        textViewFechaCalorias.setText(date);


        inicializarFirebase();

        ////AQUI SE AGARRA EL EMAIL DEL USUARIO QUE INGRESO SESION
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }
        id = databaseReference.push().getKey();

        botonGuardarConsCal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registrarCaloriasManual();
            }
        });

        sumarCal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i = Integer.parseInt(caloriasManual.getText().toString());
                i+=50;
                cal = Integer.toString(i);
                caloriasManual.setText(cal);
            }
        });

        sumarExtra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                j = Integer.parseInt(caloriasManualExtra.getText().toString());
                j+=50;
                cal = Integer.toString(j);
                caloriasManualExtra.setText(cal);
            }
        });
        restarCal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i = Integer.parseInt(caloriasManual.getText().toString());
                if(i>50){
                    i-=50;
                }
                cal = Integer.toString(i);
                caloriasManual.setText(cal);

            }
        });

        restarExtra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                j = Integer.parseInt(caloriasManualExtra.getText().toString());
                if(j>50){
                    j-=50;
                }
                cal = Integer.toString(j);
                caloriasManualExtra.setText(cal);
            }
        });

        botonCancelar_HorasSManual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               finish();
            }
        });
    }
    private void registrarCaloriasManual(){

        String sFechaInicio = textViewFechaCalorias.getText().toString();
        String sCaloriasConsumidas = caloriasManual.getText().toString();
        String sCaloriasConsumidasExtra = caloriasManualExtra.getText().toString();

        Calendar c = Calendar.getInstance();;
        fecha = new Date(c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.MONTH),c.get(Calendar.YEAR));
        if(validacion(sFechaInicio,sCaloriasConsumidas,sCaloriasConsumidasExtra)==1) {

            ConsumedCalories data = new ConsumedCalories(email, Integer.parseInt(sCaloriasConsumidas), Integer.parseInt(sCaloriasConsumidasExtra), fecha);
            databaseReference.child("calConsumidas").child(id).setValue(data);
            Toast.makeText(this, "Registro Exitoso! ", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private int validacion(String sFechaInicio,String sCaloriasConsumidas,String sCaloriasConsumidasExtra){

        if(TextUtils.isEmpty(sFechaInicio)){
            return 0;
        }
        if(TextUtils.isEmpty(sCaloriasConsumidas)){
            Toast.makeText(this,"Ingrese Calorias Consumidas",Toast.LENGTH_LONG).show();
            return 0;
        }

        if(TextUtils.isEmpty(sCaloriasConsumidasExtra)){
            Toast.makeText(this,"Ingrese Calorias Consumidas Extra",Toast.LENGTH_LONG).show();
            return 0;
        }

        return 1;
    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
