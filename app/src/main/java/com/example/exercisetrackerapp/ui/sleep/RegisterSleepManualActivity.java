package com.example.exercisetrackerapp.ui.sleep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.registro.DatosRegistro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class RegisterSleepManualActivity extends AppCompatActivity {

    TextView horasSueno,horasSiestas;
    int i=0,j=0;
    Button sumar,restar,sumarext,restarext;
    public  String email,name;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase;// Referencia a la base de Datos firebase
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                         ///REVISAR ESTE .XML PORQUE FALLAS//
       setContentView(R.layout.activity_register_hours_sleep_manual);
       // setContentView(R.layout.activity_register_hours_sleep);

        horasSueno  = (TextView) findViewById(R.id.textViewLabelHorasD);
        horasSiestas  = (TextView) findViewById(R.id.textView_horasSiesta);
        sumar  = (Button) findViewById(R.id.boton_sumarHoras);
        restar  = (Button) findViewById(R.id.boton_restarHoras);
        sumarext  = (Button) findViewById(R.id.boton_sumarSiesta);
        restarext  = (Button) findViewById(R.id.boton_restarSiesta);
        if (user != null) {
            email = user.getEmail();

        }

        sumar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(i<=23 ){
                    i++;
                }
            }
        });

        sumarext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(j<=23 ){
                    j++;
                }
            }
        });
        restar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(i>=0 ){
                    i++;
                }
            }
        });
        restarext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(j>=0){
                    j++;
                }
            }
        });
        //

        //
        ponerHoras();
        ponerHorasExtras();
        getHorasSueno();
    }
    public void getHorasSueno(){
        float hsueno = Float.parseFloat(horasSueno.getText().toString());
        float hextras = Float.parseFloat(horasSiestas.getText().toString());

        DatosRegistro data = new DatosRegistro(hsueno,hextras);
        mDatabase.child("users").child(data.getId()).setValue(data);
    }
    public void ponerHoras(){
        String str = String.valueOf(i);
        horasSueno.setText(str);
    }
    public void ponerHorasExtras(){
        String str = String.valueOf(j);
        horasSiestas.setText(str);
    }
}
