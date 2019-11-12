package com.example.exercisetrackerapp.ui.location;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;

import java.util.Locale;

public class MainActivityForOdometer extends AppCompatActivity {


        private OdometerService odometer;
        private boolean bound = false;
        private ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder) {
                OdometerService.OdometerBinder odometerBinder =
                        (OdometerService.OdometerBinder) binder;
                odometer = odometerBinder.getOdometer();
                bound = true;
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                bound = false;
            }
        };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_odometer);
        displayDistance();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, OdometerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }
    private void displayDistance() {
        final TextView distanceView = (TextView)findViewById(R.id.miles);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                double distance = 0.0;
                if (bound && odometer != null) {
                    distance = odometer.getDistance();
                }
                String distanceStr = String.format(Locale.getDefault(),
                        "%1$,.2f miles", distance);
                distanceView.setText(distanceStr);
                handler.postDelayed(this, 1000);
            }
        });
    }
}
