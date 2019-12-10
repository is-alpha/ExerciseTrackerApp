package com.example.exercisetrackerapp.ui.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.SideMenuActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterSleepManualActivity extends AppCompatActivity {

    TextView horasSueno,horasSiestas;
    int i=0,j=0;
    String hor;
    Button sumar,restar,sumarext,restarext,guardarHoras;
    public  String email,name,emailAux,id;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_hours_sleep_manual);
        inicializarFirebase();

        horasSueno = (TextView) findViewById(R.id.textView_horasManual);
        horasSiestas = (TextView) findViewById(R.id.textView_horasSiesta);
        sumar = (Button) findViewById(R.id.boton_sumarHoras);
        restar = (Button) findViewById(R.id.boton_restarHoras);
        sumarext = (Button) findViewById(R.id.boton_sumarSiesta);
        restarext = (Button) findViewById(R.id.boton_restarSiesta);
        guardarHoras = (Button) findViewById(R.id.botonRegistrarSueno);
        if (user != null) {
            email = user.getEmail();

        }

        id = databaseReference.push().getKey();
        sumar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i <= 23) {
                    i++;
                }
                hor = Integer.toString(i);
                horasSueno.setText(hor);
            }
        });

        sumarext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (j <= 23) {
                    j++;
                }

                hor = Integer.toString(j);
                horasSiestas.setText(hor);
            }
        });
        restar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (i > 0) {
                    i--;
                }
                hor = Integer.toString(i);
                horasSueno.setText(hor);

            }
        });

        restarext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (j > 0) {
                    j--;
                }
                hor = Integer.toString(j);
                horasSiestas.setText(hor);
            }
        });

        guardarHoras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                getHorasSueno();
            }
        });


    }
    public void getHorasSueno(){
        if(TextUtils.isEmpty(horasSueno.getText().toString())){
            Toast.makeText(this,"Ingrese Horas de Sueno",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(horasSiestas.getText().toString())){
            Toast.makeText(this,"Ingrese Horas de Siestas",Toast.LENGTH_LONG).show();
            return;
        }
        float hsueno = Float.parseFloat(horasSueno.getText().toString());
        float hextras = Float.parseFloat(horasSiestas.getText().toString());

            TimeSleep data = new TimeSleep(email, hsueno, hextras);
            databaseReference.child("horSueno").child(id).setValue(data);
            Toast.makeText(this, "Registro Exitoso! ", Toast.LENGTH_LONG).show();
            launchSideMenuActivity();
    }
    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void launchSideMenuActivity() {
        Intent intent = new Intent(this, SideMenuActivity.class);
        startActivity(intent);
    }
}
