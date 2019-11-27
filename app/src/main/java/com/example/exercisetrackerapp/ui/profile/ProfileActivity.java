package com.example.exercisetrackerapp.ui.profile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.editProfile.EditPasswordActivity;
import com.example.exercisetrackerapp.ui.login.LoginActivity;
import com.example.exercisetrackerapp.ui.registro.DatosRegistro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private Button mRegresar;

    private Button mBtnEditPassword;

    //private ImageView calendario;
    private DatePickerDialog picker;
    private TextView textViewFechaNac;
    private TextView nombre;
    private TextView correo;
    private TextView genero;
    private TextView altura;
    private TextView peso;
    private TextView ocupacion;
    private TextView fecha;
    public  String email,userID;
    LoginActivity log;
    private FirebaseDatabase mFirebaseDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private  DatabaseReference myRef;
    ArrayList<String> array  = new ArrayList<>();
    DatosRegistro uInfo = new DatosRegistro();
    //private Button botonCalendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

       // mFirebaseDatabase = FirebaseDatabase.getInstance();
        //myRef = mFirebaseDatabase.getReference();
        Bundle bundle = new Bundle();

        mRegresar = (Button) findViewById(R.id.botonRegresarP);
       /* nombre = (TextView) findViewById(R.id.nombreUsuario);
        correo = (TextView) findViewById(R.id.correo);
        genero = (TextView) findViewById(R.id.genero);
        altura = (TextView) findViewById(R.id.altura);
        peso = (TextView) findViewById(R.id.peso);
        ocupacion = (TextView) findViewById(R.id.trabajoValor);
        fecha = (TextView) findViewById(R.id.editTextFechaNac);*/

        ////
        mBtnEditPassword=(Button) findViewById(R.id.botonEditarPassword);
        mBtnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent= new Intent(ProfileActivity.this, EditPasswordActivity.class);
                //startActivity(intent);
                startActivity(new Intent(ProfileActivity.this, EditPasswordActivity.class));
            //no funciona:(
            }
        });
        ////
        mRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (user != null) {
            email = user.getEmail();
            userID = user.getUid();
        }

/*
        //Empieza aqui chaval
        myRef.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
        //myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // showData(dataSnapshot);

                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    uInfo.setNombre(ds.child(userID).getValue(DatosRegistro.class).getNombre()); //set the name
                   // uInfo.setCorreo(ds.child(userID).getValue(DatosRegistro.class).getCorreo()); //set the emai
                    uInfo.setOcupacion(ds.child(userID).getValue(DatosRegistro.class).getOcupacion());
                    uInfo.setAltura(ds.child(userID).getValue(DatosRegistro.class).getAltura()); //set the phone_num
                    uInfo.setPeso(ds.child(userID).getValue(DatosRegistro.class).getPeso()); //
                    uInfo.setFecha(ds.child(userID).getValue(DatosRegistro.class).getFecha());


                    ArrayList<String> array  = new ArrayList<>();
                    array.add(uInfo.getNombre());
                    array.add(uInfo.getCorreo());
                    array.add(uInfo.getOcupacion());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


*/


/*
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    */
        ///


       // nombre.setText(email.toString());

      //  Toast.makeText(this,"H "+email,Toast.LENGTH_LONG).show();
       // nam = log.getCorreo();
       // Toast.makeText(this,nam,Toast.LENGTH_LONG).show();
        /*

        botonCalendario = (Button) findViewById(R.id.botonCalendario);
        textViewFechaNac = (TextView) findViewById(R.id.textViewFechaNac);
        botonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                textViewFechaNac.setText(day + "/" + month + "/" + year);
                            }
                        }, year,  month, day);
                datePickerDialog.show();
            }
        });
        */

    }

//Medtodo opcional
 /*   private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            DatosRegistro uInfo = new DatosRegistro();
            uInfo.setNombre(ds.child(userID).getValue(DatosRegistro.class).getNombre()); //set the name
            uInfo.setCorreo(ds.child(userID).getValue(DatosRegistro.class).getCorreo()); //set the emai
            uInfo.setOcupacion(ds.child(userID).getValue(DatosRegistro.class).getOcupacion());
            uInfo.setAltura(ds.child(userID).getValue(DatosRegistro.class).getAltura());
            uInfo.setPeso(ds.child(userID).getValue(DatosRegistro.class).getPeso()); //

            ArrayList<String> array  = new ArrayList<>();
            array.add(uInfo.getNombre());
            array.add(uInfo.getCorreo());
            array.add(uInfo.getOcupacion());
        }
    }
    */
 public String agarraDatos(){
    return "hoola";
}

    public ArrayList despliega(){
    return array;

    }
}
