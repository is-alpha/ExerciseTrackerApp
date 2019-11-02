package com.example.exercisetrackerapp.ui.profile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.login.LoginActivity;

public class ProfileActivity extends AppCompatActivity {
    private Button mRegresar;
    //private ImageView calendario;
    private DatePickerDialog picker;
    private TextView textViewFechaNac;
    private TextView nombre;
    private TextView correo;
    private TextView genero;
    private TextView altura;
    private TextView peso;
    private TextView ocupacion;
    public static String nam="HOLAAAAA";
    LoginActivity log;
    //private Button botonCalendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        mRegresar = (Button) findViewById(R.id.botonRegresarP);
        nombre = (TextView) findViewById(R.id.nombre);
        correo = (TextView) findViewById(R.id.email);
        genero = (TextView) findViewById(R.id.genero);
        altura = (TextView) findViewById(R.id.altura);
        peso = (TextView) findViewById(R.id.peso);
        ocupacion = (TextView) findViewById(R.id.trabajoValor);
        mRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        nombre.setText("jlololo");
       // nam = log.getCorreo();
       // Toast.makeText(this,nam,Toast.LENGTH_LONG).show();
        /*

        botonCalendario = (Button) findViewById(R.id.botonCalendario);
        textViewFechaNac = (TextView) findViewById(R.id.textViewFechaNac);
        botonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                textViewFechaNac.setText(day + "/" + month + "/" + year);
                            }
                        }, year,  month, day);
                datePickerDialog.show();
            }
        });
        */

    }

    public void despliega(){


    }
}
