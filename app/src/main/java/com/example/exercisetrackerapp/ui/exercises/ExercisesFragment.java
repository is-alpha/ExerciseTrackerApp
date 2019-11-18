package com.example.exercisetrackerapp.ui.exercises;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.burnedCalories.BurnedCaloriesActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExercisesFragment extends Fragment {

    private ExercisesViewModel exercisesViewModel;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<String> listaNomEjercicios;
    List<String> listaImagenEjercicios;
    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        exercisesViewModel =
                ViewModelProviders.of(this).get(ExercisesViewModel.class);
       // View root = inflater.inflate(R.layout.fragment_exercises, container, false);
        View root = inflater.inflate(R.layout.fragment_exercises, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("ejercicio").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaNomEjercicios = new ArrayList<String>();
                //int i = 0;
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String nombreEjercicios = areaSnapshot.child("nombre").getValue(String.class);
                    listaNomEjercicios.add(nombreEjercicios);
                    /*
                    String imagenEjercicios = areaSnapshot.child("imagen").getValue(String.class);
                    listaImagenEjercicios.add(imagenEjercicios);
                    */

                }

                List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
                //TEMPORAL
                int[] imagesExercises = new int[]{
                        R.drawable.img_abdominales,
                        R.drawable.img_caminadora,
                        R.drawable.img_caminar,
                        R.drawable.img_ciclismo,
                        R.drawable.img_correr,
                        R.drawable.img_futbol,
                        R.drawable.img_natacion,
                        R.drawable.img_pesas,
                };

                for(int i=0;i<listaNomEjercicios.size();i++){
                    HashMap<String, String> hm = new HashMap<String,String>();
                    hm.put("im",Integer.toString(imagesExercises[i]));
                    hm.put("nom",listaNomEjercicios.get(i));
                    aList.add(hm);
                }

                // Keys used in Hashmap
                String[] from = {"im","nom"};
                // Ids of views in listview_layout
                int[] to = {R.id.imageVejercicio,R.id.textVnomEjercicio};

                // Instantiating an adapter to store each items
                // R.layout.listview_layout defines the layout of each item
                SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.customlayout, from, to);
                listView = view.findViewById(R.id.ListViewEj);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }


    private void inicializarFirebase(){
        //FirebaseApp.initializeApp();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


}