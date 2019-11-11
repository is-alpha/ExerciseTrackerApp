package com.example.exercisetrackerapp.ui.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.registro.DatosRegistro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;


    private TextView textViewFechaNac;
    private TextView nombre;
    private TextView correo;
    private TextView genero;
    private TextView altura;
    private TextView peso;
    private TextView ocupacion;
    private TextView fecha;
    private   String email,userID;
    String nam;
    private FirebaseDatabase mFirebaseDatabase;
    List<String> listaDatos;
    DatosRegistro uInfo;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("users");
    int i=0;

    ArrayList<String> array  = new ArrayList<>();
   // DatosRegistro uInfo = new DatosRegistro();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        nombre = (TextView) root.findViewById(R.id.nombreUsuario);
        nombre = (TextView) root.findViewById(R.id.nombreUsuario);
        correo = (TextView) root.findViewById(R.id.correo);
        genero = (TextView) root.findViewById(R.id.genero);
        altura = (TextView) root.findViewById(R.id.altura);
        peso = (TextView) root.findViewById(R.id.peso);
        ocupacion = (TextView) root.findViewById(R.id.trabajoValor);
        fecha = (TextView) root.findViewById(R.id.editTextFechaNac);

        if (user != null) {
            email = user.getEmail();
            userID = user.getUid();
        }
        myRef.orderByChild("correo").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                listaDatos = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                     nam = areaSnapshot.getValue().toString();
                    nombre.setText(nam);
                }

                /*
                String dato = dataSnapshot.getValue().toString();
                listaDatos.add(dato);
              nombre.setText(listaDatos.get(0));*/


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            // ...
        });

        correo.setText(email);

       /* altura.setText(String.valueOf(uInfo.getAltura()));
        peso.setText(String.valueOf(uInfo.getPeso()));
        ocupacion.setText(uInfo.getOcupacion());
        fecha.setText(String.valueOf(uInfo.getFecha()));*/

        /*final TextView textView = root.findViewById(R.id.text_home);
        profileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        ///



        ///
        return root;
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

    }


}