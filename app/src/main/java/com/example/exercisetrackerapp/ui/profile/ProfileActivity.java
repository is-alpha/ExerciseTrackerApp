package com.example.exercisetrackerapp.ui.profile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import com.example.exercisetrackerapp.R;

public class ProfileActivity extends AppCompatActivity {
    private Button mRegresar;
    //private ImageView calendario;
    private DatePickerDialog picker;
    private TextView textViewFechaNac;
    //private Button botonCalendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mRegresar = (Button) findViewById(R.id.botonRegresarP);
        mRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
}
