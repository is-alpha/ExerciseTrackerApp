package com.example.exercisetrackerapp.ui.exercises;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.example.exercisetrackerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExerciseList extends ListFragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<String> listaEjercicios;


    private void obtenerLista() {

        inicializarFirebase();

        databaseReference.child("ejercicio").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaEjercicios = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String consultaEjercicios = areaSnapshot.child("nombre").getValue(String.class);
                    listaEjercicios.add(consultaEjercicios);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<listaEjercicios.size(); i++){
            /*HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", "Country : " + countries[i]);
            hm.put("cur","Currency : " + currency[i]);
            hm.put("flag", Integer.toString(flags[i]) );
            aList.add(hm);
             */
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("ej", listaEjercicios.get(i));
           /* hm.put("cur","Currency : " + currency[i]);
            hm.put("flag", Integer.toString(flags[i]) );
            */
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "ej"};

        // Ids of views in listview_layout
        int[] to = { R.id.textVnomEjercicio};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.customlayout, from, to);

        setListAdapter(adapter);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void inicializarFirebase(){
        //FirebaseApp.initializeApp();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
