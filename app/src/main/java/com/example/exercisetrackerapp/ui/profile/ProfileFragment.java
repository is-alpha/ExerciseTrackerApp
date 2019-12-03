package com.example.exercisetrackerapp.ui.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.editProfile.EditPasswordActivity;
import com.example.exercisetrackerapp.ui.editProfile.EditProfileDataActivity;
import com.example.exercisetrackerapp.ui.registro.DatosRegistro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    private Button mRegresar;
    private Button mBtnEditPassword;
    private Button mBtnEditData;

    private TextView textViewFechaNac;
    private TextView nombre;
    private TextView correo;
    private TextView genero;
    private TextView altura;
    private TextView peso;
    private TextView ocupacion;
    private TextView fecha;
    private   String email,userID,emailAux,name, trabajo;
    private float height, weight;
    private FirebaseDatabase mFirebaseDatabase;
    List<String> listaDatos;
    DatosRegistro uInfo;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


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

        inicializarFirebase();
        mRegresar =(Button) root.findViewById(R.id.botonRegresarP);
        mRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        mBtnEditPassword=(Button) root.findViewById(R.id.botonEditarPassword);
        mBtnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditPasswordActivity.class);
                startActivity(intent);
            }
        });

        mBtnEditData=(Button) root.findViewById(R.id.botonEditar);
        mBtnEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileDataActivity.class);
                startActivity(intent);
            }
        });


        if (user != null) {
            email = user.getEmail();
            userID = user.getUid();
        }

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("correo").getValue().toString();
                    if(emailAux.equals(email)){
                        name = areaSnapshot.child("nombre").getValue().toString();
                        nombre.setText(name);
                        weight = Float.parseFloat(areaSnapshot.child("peso").getValue().toString());
                        peso.setText(Float.toString(weight));
                        height = Float.parseFloat(areaSnapshot.child("altura").getValue().toString());
                        altura.setText(Float.toString(height));
                        trabajo = areaSnapshot.child("ocupacion").getValue().toString();
                        ocupacion.setText(trabajo);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        /*
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
              nombre.setText(listaDatos.get(0));


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
*/
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


    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}
