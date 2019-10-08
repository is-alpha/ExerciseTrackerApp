package com.example.exercisetrackerapp.ui.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.exercisetrackerapp.R;

public class ProfileActivity extends AppCompatActivity {
    private Button mRegresar;

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
    }
}
