package com.example.exercisetrackerapp.ui.exerciseRoutine;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Date;
import com.example.exercisetrackerapp.ui.location.OdometerService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TrackExerciseActivity extends AppCompatActivity{

    private TextView textViewEjercicio;
    ImageView imageViewImagen;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    DatabaseReference obj;
    Date fecha;
    int i=0,j=0;
    String email="",uid,id,userID,emailAux,objetivo, mArray[];
    int year, month,dayOfMonth,idImage;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private OdometerService odometer;
    private boolean bound = false;
    private final int PERMISSION_REQUEST_CODE = 698;
    private final int NOTIFICATION_ID = 423;
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
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_exercise);
        displayDistance();
        inicializarFirebase();

        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }

        textViewEjercicio = (TextView) findViewById(R.id.textViewEjercicio);
        imageViewImagen  = (ImageView) findViewById(R.id.imageViewImagen);

        databaseReference.child("ejercicioActual").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();
                    if(emailAux.equals(email)){

                        switch(areaSnapshot.child("exercise").getValue().toString()) {
                            case "Abdominales": idImage = R.drawable.img_abdominales; break;
                            case "Caminadora": idImage = R.drawable.img_caminadora; break;
                            case "Caminar": idImage = R.drawable.img_caminar; break;
                            case "Ciclismo": idImage = R.drawable.img_ciclismo; break;
                            case "Correr": idImage = R.drawable.img_correr; break;
                            case "Fútbol": idImage = R.drawable.img_futbol; break;
                            case "Pesas": idImage = R.drawable.img_pesas; break;

                        }

                        textViewEjercicio.setText(areaSnapshot.child("exercise").getValue().toString());
                        imageViewImagen.setImageResource(idImage);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(this, OdometerService.class);
                    bindService(intent, connection, Context.BIND_AUTO_CREATE);
                } else {
//Create a notification builder
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                            .setSmallIcon(android.R.drawable.ic_menu_compass)
                            .setContentTitle(getResources().getString(R.string.app_name))
                            //.setContentText(getResources().getString(R.string.permission_denied))
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVibrate(new long[] { 1000, 1000})
                            .setAutoCancel(true);
//Create an action
                    Intent actionIntent = new Intent(this, TrackExerciseActivity.class);
                    PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 0,
                            actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(actionPendingIntent);
//Issue the notification
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(NOTIFICATION_ID, builder.build());
                }
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this,
                OdometerService.PERMISSION_STRING)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{OdometerService.PERMISSION_STRING},
                    PERMISSION_REQUEST_CODE);
        } else {
            Intent intent = new Intent(this, OdometerService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
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
        final TextView distanceView = (TextView)findViewById(R.id.distancia);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                double distance = 0.0;
                if (bound && odometer != null) {
                    distance = odometer.getDistance();
                }
                String distanceStr = String.format(Locale.getDefault(),
                        "%1$,.2f m", distance);
                distanceView.setText(distanceStr);
                handler.postDelayed(this, 1000);
            }
        });
    }

}
