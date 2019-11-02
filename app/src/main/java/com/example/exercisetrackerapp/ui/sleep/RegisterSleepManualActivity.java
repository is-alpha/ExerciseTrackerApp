package com.example.exercisetrackerapp.ui.sleep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.registro.DatosRegistro;
import com.google.firebase.database.DatabaseReference;

public class RegisterSleepManualActivity extends AppCompatActivity {

    TextView horasSueno;
    private DatabaseReference mDatabase;// Referencia a la base de Datos firebase
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hours_sleep_manual);
        horasSueno  = (TextView) findViewById(R.id.textViewLabelHorasD);
    }
    public void getHorasSueno(){
        float hsueno = Float.parseFloat(horasSueno.getText().toString());

        DatosRegistro data = new DatosRegistro(hsueno);
        mDatabase.child("users").child(data.getId()).setValue(data);
    }
}
