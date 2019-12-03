package com.example.exercisetrackerapp.ui.exerciseRoutine;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.goals.GoalsRegisterActivity;
import com.example.exercisetrackerapp.ui.goals.GoalsViewModel;
import com.example.exercisetrackerapp.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListExerciseRoutineFragment extends Fragment {

    private ExerciseRoutineViewModel eRoutineViewModel;

    //DATOS
    List<String> listaNomEjercicios;
    List <Integer> listaImagenes;
    ListView listViewRoutines;
    List<String> listaFechaInicial, listaFechaFin;
    List <Float> listaTiempo;
    String nombreEjercicio;
    int idImage;
    float tiempo;
    StringBuilder fecha = new StringBuilder();
    String fechaInicial,fechaFin;

    //INTERFASE
    Button botonAnadirRutina;

    //BASE DE DATOS
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //referencia al usuario
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email, emailAux;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        eRoutineViewModel =
                ViewModelProviders.of(this).get(ExerciseRoutineViewModel.class);

        View root = inflater.inflate(R.layout.fragment_exercise_routine, container, false);

        botonAnadirRutina =(Button) root.findViewById(R.id.botonAnadirRutina);

        botonAnadirRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getActivity(), GoalsRegisterActivity.class);
                //startActivity(intent);
                Fragment fragment = null;
                fragment = new ExerciseRoutineFragment();
                replaceFragment(fragment);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        if (user != null) {
            email = user.getEmail();
        }

        databaseReference.child("rutinas").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaNomEjercicios = new ArrayList<String>();
                listaImagenes = new ArrayList<>();
                listaFechaInicial = new ArrayList<String>();
                listaFechaFin = new ArrayList<String>();
                listaTiempo = new ArrayList<Float>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    nombreEjercicio = areaSnapshot.child("ejercicio").getValue().toString();
                    tiempo = areaSnapshot.child("tiempo").getValue(Float.class);

                    fecha = new StringBuilder();
                    fecha.append(areaSnapshot.child("dInicial").child("day").getValue(Integer.class));
                    fecha.append("/");
                    fecha.append(areaSnapshot.child("dInicial").child("month").getValue(Integer.class));
                    fecha.append("/");
                    fecha.append(areaSnapshot.child("dInicial").child("year").getValue(Integer.class));

                    fechaInicial = fecha.toString();


                    fecha = new StringBuilder();
                    fecha.append(areaSnapshot.child("dFinal").child("day").getValue(Integer.class));
                    fecha.append("/");
                    fecha.append(areaSnapshot.child("dFinal").child("month").getValue(Integer.class));
                    fecha.append("/");
                    fecha.append(areaSnapshot.child("dFinal").child("year").getValue(Integer.class));

                    fechaFin = fecha.toString();

                    emailAux = areaSnapshot.child("usuario").getValue().toString();

                    if (emailAux.equals(email)) {

                        listaNomEjercicios.add(nombreEjercicio);
                        listaFechaInicial.add(fechaInicial);
                        listaFechaFin.add(fechaFin);
                        listaTiempo.add(tiempo);

                        switch(nombreEjercicio) {
                            case "Abdominales": idImage = R.drawable.img_abdominales; break;
                            case "Caminadora": idImage = R.drawable.img_caminadora; break;
                            case "Caminar": idImage = R.drawable.img_caminar; break;
                            case "Ciclismo": idImage = R.drawable.img_ciclismo; break;
                            case "Correr": idImage = R.drawable.img_correr; break;
                            case "Fútbol": idImage = R.drawable.img_futbol; break;
                            case "Pesas": idImage = R.drawable.img_pesas; break;
                            default : idImage = R.drawable.img_pesas; break;
                        }

                        listaImagenes.add(idImage);

                    }
                }

                List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

                for(int i=0;i<listaNomEjercicios.size();i++){
                    HashMap<String, String> hm = new HashMap<String,String>();
                    hm.put("exercise",listaNomEjercicios.get(i));
                    hm.put("fechaIni",listaFechaInicial.get(i));
                    hm.put("fechaFin",listaFechaFin.get(i));
                    hm.put("image",listaImagenes.get(i).toString());
                    hm.put("tiempo",listaTiempo.get(i).toString());
                    aList.add(hm);
                }

                // Keys used in Hashmap
                String[] from = {"image","exercise","fechaIni","fechaFin","tiempo","calorias"};
                // Ids of views in listview_layout
                int[] to = {R.id.imageViewEjercicio,R.id.textViewEjercicio,R.id.textViewFechaInicial,R.id.textViewFechaFin,R.id.textViewTiempo, R.id.textViewCalorias};

                // Instantiating an adapter to store each items
                SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.custom_layout_routine, from, to);
                listViewRoutines = view.findViewById(R.id.listViewMetas);
                listViewRoutines.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
