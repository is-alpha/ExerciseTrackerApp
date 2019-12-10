package com.example.exercisetrackerapp.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.burnedCalories.BurnedCaloriesActivity;
import com.example.exercisetrackerapp.ui.consumedCalories.ConsumedCalorieInfoActivity;
import com.example.exercisetrackerapp.ui.consumedCalories.ConsumedCaloriesActivity;
import com.example.exercisetrackerapp.ui.exerciseRoutine.TrackExerciseActivity;
import com.example.exercisetrackerapp.ui.location.MainActivityForOdometer;
import com.example.exercisetrackerapp.ui.results.WeightLossActivity;
import com.example.exercisetrackerapp.ui.sleep.RegisterSleepManualActivity;
import com.example.exercisetrackerapp.ui.sleep.SleepInfoActivity;
import com.example.exercisetrackerapp.ui.weight.WeightActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public  String email,name,calConsum,uid,emailAux;
    float calorias;
    private DatabaseReference mDatabase;
    TextView textViewCalorias,textViewPeso,textViewHorasSueno,textViewCaloriasQuemadas;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        inicializarFirebase();

        ////AQUI SE AGARRA EL EMAIL DEL USUARIO QUE INGRESO SESION
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }

        textViewCalorias = (TextView) root.findViewById(R.id.textViewCalorias);
        textViewPeso = (TextView) root.findViewById(R.id.textViewPeso);
        textViewHorasSueno = (TextView) root.findViewById(R.id.textViewHorasSueno);
        textViewCaloriasQuemadas = (TextView) root.findViewById(R.id.textViewCaloriasQuemadas);

        getCaloriesBurned();

        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    if(areaSnapshot.child("correo").getValue().toString().equals(email)){
                        calConsum = areaSnapshot.child("calConsumidas").getValue().toString();
                        textViewCalorias.setText(calConsum);
                        textViewPeso.setText(areaSnapshot.child("peso").getValue().toString());
                        textViewHorasSueno.setText(areaSnapshot.child("hsueno").getValue().toString());
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        /*final TextView textView = root.findViewById(R.id.text_share);
        goalsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        ///
        ///
        ImageButton botonCaloriasQuemadas = (ImageButton) root.findViewById(R.id.botonCaloriasQuemadas);
        botonCaloriasQuemadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchBurnedCaloriesRegistry();
            }
        });

        ImageButton botonCaloriasQuemadas2 = (ImageButton) root.findViewById(R.id.botonCaloriasQuemadas2);
        botonCaloriasQuemadas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchBurnedCaloriesRegistry();


            }
        });

        //boton para odometro
        ImageButton botonOdometro = (ImageButton) root.findViewById(R.id.imageButton10);
        botonOdometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //launchuOdometer();

            }
        });

        ImageButton botonCaloriasConsumidasEditar = (ImageButton) root.findViewById(R.id.botonCaloriasConsumidasEditar);
        botonCaloriasConsumidasEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchConsumedCaloriesRegistry();
            }
        });

        ImageButton botonCaloriasConsumidasInfo = (ImageButton) root.findViewById(R.id.botonCaloriasConsumidasInfo);
        botonCaloriasConsumidasInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCaloriasConsumidasInfo();
            }
        });


        ImageButton botonPesoEditar = (ImageButton) root.findViewById(R.id.botonPesoEditar);
        botonPesoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPesoRegistry();
            }
        });

        ImageButton botonPesoInfo = (ImageButton) root.findViewById(R.id.botonPesoInfo);
        botonPesoInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPesoInfo();
            }
        });



        ImageButton botonRegistroSuenoM = (ImageButton) root.findViewById(R.id.botonRegistroSuenoM);
        botonRegistroSuenoM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRegisterSleepManualActivity();
            }
        });


        ImageButton btntrackExercise=(ImageButton) root.findViewById(R.id.imageButton6);
        btntrackExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchStopWatch();
            }
        });

        ImageButton botonSuenoInfo = (ImageButton) root.findViewById(R.id.botonSuenoInfo);
        botonSuenoInfo .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSleepInfoActivity();
            }
        });

        return root;
    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
    }

    private void launchBurnedCaloriesRegistry() {
        Intent intent = new Intent(getActivity(), BurnedCaloriesActivity.class);
        startActivity(intent);
    }

    private void launchConsumedCaloriesRegistry() {
        Intent intent = new Intent(getActivity(), ConsumedCaloriesActivity.class);
        startActivity(intent);
    }

    private void launchCaloriasConsumidasInfo() {
        Intent intent = new Intent(getActivity(), ConsumedCalorieInfoActivity.class);
        startActivity(intent);
    }

    private void launchPesoRegistry() {
        Intent intent = new Intent(getActivity(), WeightActivity.class);
        startActivity(intent);
    }

    private void launchPesoInfo() {
        Intent intent = new Intent(getActivity(), WeightLossActivity.class);
        startActivity(intent);
    }


    private void launchSleepInfoActivity() {
        Intent intent = new Intent(getActivity(), SleepInfoActivity.class);
        startActivity(intent);
    }

    private void launchRegisterSleepManualActivity() {
        Intent intent = new Intent(getActivity(), RegisterSleepManualActivity.class);
        startActivity(intent);
    }

    private void launchuOdometer() {
        Intent intent = new Intent(getActivity(), MainActivityForOdometer.class);
        startActivity(intent);
    }

    private void launchStopWatch() {
        Intent intent = new Intent(getActivity(), TrackExerciseActivity.class);
        startActivity(intent);
    }

    private void getCaloriesBurned(){

        mDatabase.child("caloriasQuemadas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=1;
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("usuario").getValue().toString();
                    if (emailAux.equals(email)) {
                        calorias = calorias + Float.parseFloat(areaSnapshot.child("cantCalorie").getValue().toString());
                        calorias = calorias / i;

                        DecimalFormat decimalFormat = new DecimalFormat("#.00");
                        textViewCaloriasQuemadas.setText(decimalFormat.format(calorias));
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}