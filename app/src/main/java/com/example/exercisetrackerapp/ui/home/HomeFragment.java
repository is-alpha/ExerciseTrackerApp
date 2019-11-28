package com.example.exercisetrackerapp.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.burnedCalories.BurnedCaloriesActivity;
import com.example.exercisetrackerapp.ui.consumedCalories.ConsumedCalorieInfoActivity;
import com.example.exercisetrackerapp.ui.consumedCalories.ConsumedCaloriesActivity;
import com.example.exercisetrackerapp.ui.exerciseRoutine.TrackExerciseActivity;
import com.example.exercisetrackerapp.ui.location.MainActivityForOdometer;
import com.example.exercisetrackerapp.ui.sleep.RegisterSleepManualActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    //Button botonCaloriasQuemadas;
    public  String email,name,calConsum,uid;
    private DatabaseReference mDatabase;
    TextView textViewCalorias;
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
        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    if(areaSnapshot.child("correo").getValue().toString().equals(email)){
                        calConsum = areaSnapshot.child("calConsumidas").getValue().toString();
                        textViewCalorias.setText(calConsum);
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

        //PRUEBA DEL ODOMETRO CON BOTON DE SUENO 
        ImageButton botonRegistroSuenoM = (ImageButton) root.findViewById(R.id.botonRegistroSuenoM);
        botonRegistroSuenoM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        launchuOdometer();

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




        /*ImageButton botonRegistroSuenoM = (ImageButton) root.findViewById(R.id.botonRegistroSuenoM);
        botonRegistroSuenoM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRegisterSleepManualActivity();
            }
        });*/


        ImageButton btntrackExercise=(ImageButton) root.findViewById(R.id.imageButton6);
        btntrackExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchStopWatch();
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
}