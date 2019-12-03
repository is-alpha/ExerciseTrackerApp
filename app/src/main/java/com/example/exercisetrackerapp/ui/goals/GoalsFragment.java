package com.example.exercisetrackerapp.ui.goals;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.exercisetrackerapp.R;
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

public class GoalsFragment extends Fragment {

    private GoalsViewModel goalsViewModel;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<String> listaNomEjercicios, listaFechaInicial, listaFechaFin, listaTiempo,listaCalorias,listaCumplida;
    String email="",uid,id,userID,emailAux;
    ListView listViewMetas;
    String nombreEjercicio,fechaInicial,fechaFin,tiempo,calorias,cumplida;
    Button botonAnadirMeta;


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalsViewModel =
                ViewModelProviders.of(this).get(GoalsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_exercise_goals, container, false);


        botonAnadirMeta =(Button) root.findViewById(R.id.botonAnadirMeta);
        botonAnadirMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GoalsRegisterActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
/*
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }


        databaseReference.child("metas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaNomEjercicios = new ArrayList<String>();
                listaFechaInicial = new ArrayList<String>();
                listaFechaFin = new ArrayList<String>();
                listaTiempo = new ArrayList<String>();
                listaCalorias = new ArrayList<String>();
                listaCumplida = new ArrayList<String>();

                //int i = 0;
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    nombreEjercicio = areaSnapshot.child("exercise").getValue(String.class);
                    fechaInicial = areaSnapshot.child("fechaIni").getValue(String.class);
                    fechaFin = areaSnapshot.child("fechaFin").getValue(String.class);
                    tiempo = areaSnapshot.child("tiempo").getValue(String.class);
                    calorias = areaSnapshot.child("calorias").getValue(String.class);
                    cumplida = areaSnapshot.child("cumplida").getValue(String.class);

                    emailAux = areaSnapshot.child("usuario").getValue().toString();
                    if(emailAux.equals(email)){
                        listaNomEjercicios.add(nombreEjercicio);
                        listaFechaInicial.add(fechaInicial);
                        listaFechaFin.add(fechaFin);
                        listaTiempo.add(tiempo);
                        listaCalorias.add(calorias);
                        listaCumplida.add(cumplida);
                    }

                }

                List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

                for(int i=0;i<listaNomEjercicios.size();i++){
                    HashMap<String, String> hm = new HashMap<String,String>();
                    hm.put("exercise",listaNomEjercicios.get(i));
                    hm.put("fechaIni",listaFechaInicial.get(i));
                    hm.put("fechaFin",listaFechaFin.get(i));
                    hm.put("tiempo",listaTiempo.get(i));
                    hm.put("calorias",listaCalorias.get(i));
                    hm.put("cumplida",listaCumplida.get(i));
                    aList.add(hm);
                }
                // Keys used in Hashmap
                String[] from = {"exercise","fechaIni","fechaFin","tiempo","calorias","cumplida"};
                // Ids of views in listview_layout
                int[] to = {R.id.textViewEjercicio,R.id.textViewFechaInicial,R.id.textViewFechaFin,R.id.textViewTiempo, R.id.textViewCalorias, R.id.textViewCumplida};

                // Instantiating an adapter to store each items
                // R.layout.listview_layout defines the layout of each item
                SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.custom_layout_metas, from, to);
                listViewMetas = view.findViewById(R.id.listViewMetas);
                listViewMetas.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        super.onViewCreated(view, savedInstanceState);


    }*/
    private void inicializarFirebase(){
        //FirebaseApp.initializeApp();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}