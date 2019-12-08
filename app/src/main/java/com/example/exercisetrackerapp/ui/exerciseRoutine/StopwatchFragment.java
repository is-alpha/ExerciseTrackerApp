package com.example.exercisetrackerapp.ui.exerciseRoutine;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Date;
import com.example.exercisetrackerapp.ui.burnedCalories.BurnedCalories;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

public class StopwatchFragment extends Fragment implements View.OnClickListener{
    private int seconds=0;
    private boolean isRunning;
    private boolean wasRunning;
    String email,emailAux,uid,ejercicio,id;
    float calorias, distancia,minutos;
    int repeticiones;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");

        }


    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnStart:
                onClickStart();
                break;

            case R.id.btnStop:
                onClickStop();
                break;

            case R.id.btnEnd:
                onClickEnd();
                break;
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View layout = inflater.inflate(R.layout.fragment_stopwatch,container,false);
        runTimer(layout);
        Button stopButton = (Button)layout.findViewById(R.id.btnStop);
        Button startButton = (Button)layout.findViewById(R.id.btnStart);
        Button resetButton = (Button)layout.findViewById(R.id.btnEnd);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onPause(){
        super.onPause();
        wasRunning=isRunning;
        isRunning=false;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(wasRunning)
            isRunning=true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",isRunning);
        savedInstanceState.putBoolean("wasRunning",wasRunning);

    }

    private void onClickStart(){
        isRunning=true;
    }

    private void onClickStop(){
        isRunning=false;
    }

    private void onClickEnd(){
        isRunning=false;
        registrarCaloriasQuemadas();
        //calculoCalorias();
        seconds=0;

    }

    public void runTimer(View view){

        final TextView timeView= (TextView)view.findViewById(R.id.time_view);
        final Handler handler= new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int secs=seconds%60;
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,secs);

                timeView.setText(time);

                if(isRunning)
                    seconds++;

                handler.postDelayed(this,1000);
            }
        });
    }




    private void registrarCaloriasQuemadas(){

        inicializarFirebase();

        ////AQUI SE AGARRA EL EMAIL DEL USUARIO QUE INGRESO SESION
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }

        databaseReference.child("ejercicioActual").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();
                    if(emailAux.equals(email)){

                        switch(areaSnapshot.child("exercise").getValue().toString()) {

                            case "Caminar": ejercicio="Caminar"; break;
                            case "Ciclismo": ejercicio="Ciclismo"; break;
                            case "Correr": ejercicio="Correr"; break;


                        }
                    }
                }

                int minutes=(seconds%3600)/60;
                Date fecha;
                Calendar c = Calendar.getInstance();
                // ejercicio="Caminar";
                fecha = new Date(c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.MONTH),c.get(Calendar.YEAR));
                ////AQUI SE AGARRA EL EMAIL DEL USUARIO QUE INGRESO SESION
                if (user != null) {
                    email = user.getEmail();
                    uid = user.getUid();
                }

                if(ejercicio.equals("Caminar")){
                    BurnedCalories data = new BurnedCalories(email, (minutes*200)/30,  fecha,ejercicio,minutes);
                   databaseReference.child("caloriasQuemadas").child(uid).setValue(data);
                }

                if(ejercicio.equals("Correr")){
                    BurnedCalories data = new BurnedCalories(email, (minutes*325)/30,  fecha,ejercicio,minutes);
                    databaseReference.child("caloriasQuemadas").child(uid).setValue(data);
                }
                if(ejercicio.equals("Ciclismo")){
                    BurnedCalories data = new BurnedCalories(email, (minutes*450)/30, fecha,ejercicio,minutes);
                    databaseReference.child("caloriasQuemadas").child(uid).setValue(data);

                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    public void calculoCalorias(){
        inicializarFirebase();

        int minutes=(seconds%3600)/60;
        Date fecha;
        Calendar c = Calendar.getInstance();
         ejercicio="Correr";
        fecha = new Date(c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.MONTH),c.get(Calendar.YEAR));
        ////AQUI SE AGARRA EL EMAIL DEL USUARIO QUE INGRESO SESION
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }

  /*      if(ejercicio.equals("Caminar")){
            BurnedCalories data = new BurnedCalories(email, (minutes*200)/30,  fecha,ejercicio,minutes);
            databaseReference.child("caloriasQuemadas").child(uid).setValue(data);
        }
*/
        if(ejercicio.equals("Correr")){
            BurnedCalories data = new BurnedCalories(email, (minutes*325)/30,  fecha,ejercicio,minutes);
            databaseReference.child("caloriasQuemadas").child(uid).setValue(data);
        }
        /*if(ejercicio.equals("Ciclismo")){
            BurnedCalories data = new BurnedCalories(email, (minutes*450)/30, fecha,ejercicio,minutes);
            databaseReference.child("caloriasQuemadas").child(uid).setValue(data);

        }*/

    }
    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }



}