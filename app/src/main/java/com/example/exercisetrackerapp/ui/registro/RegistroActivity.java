package com.example.exercisetrackerapp.ui.registro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.profile.ProfileActivity;

public class RegistroActivity extends AppCompatActivity {
    private Button mBtRegresar;
    private Button mRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mBtRegresar = (Button) findViewById(R.id.botonRegresar);
        mBtRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRegistrar = (Button) findViewById(R.id.botonRegistrarse);
        mRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                launchProfile();
            }
        });
    }


   /* Spinner spinner = (Spinner) findViewById(R.id.spinnerGenero);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genero, R.layout.spinner_item);

    //spinner.setAdapter(new ArrayAdapter(this, R.id.spinner_item));

    spinner.setAdapter(new ArrayAdapter.createFromResource(this, R.array.genero, R.layout.spinner_item));
*/
    //adapter.setSpinnerMode(R.layout.dropdown);
   // spinner.setAdapter(adapter);
   private void launchProfile() {
       Intent intent = new Intent(this, ProfileActivity.class);
       startActivity(intent);
   }

}





