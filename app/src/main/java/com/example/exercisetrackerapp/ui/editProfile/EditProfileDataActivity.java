package com.example.exercisetrackerapp.ui.editProfile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditProfileDataActivity extends AppCompatActivity {

    private Button mBtnEditar;

    private EditText newNombre;
    private EditText newTrabajo;
    private EditText newAltura;
    private EditText newPeso;
    Spinner spGenero;
    private EditText mCorreoConfirmacion;

    private String sCorreo="";

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        newNombre= (EditText) findViewById(R.id.editName);
        newTrabajo=(EditText)findViewById(R.id.editTrabajo);
        newAltura=(EditText)findViewById(R.id.editAltura);
        newPeso=(EditText)findViewById(R.id.editPeso);
        spGenero=(Spinner)findViewById(R.id.spinnerGenero);
        mCorreoConfirmacion=(EditText)findViewById(R.id.correoC);

        mBtnEditar=(Button)findViewById(R.id.editarButton);

        mBtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sCorreo=mCorreoConfirmacion.getText().toString();

                if(!sCorreo.isEmpty()){

                    Query query=mDatabase.orderByChild("correo").equalTo(sCorreo);
                    String nNombre= newNombre.getText().toString();
                    String nTrabajo= newTrabajo.getText().toString();
                    float nAltura= Float.parseFloat(newAltura.getText().toString());
                    float nPeso= Float.parseFloat(newPeso.getText().toString());

                    final Map<String,Object> personaMap= new HashMap<>();
                    personaMap.put("altura", nAltura);
                    personaMap.put("genero", spGenero.getSelectedItem().toString());
                    personaMap.put("nombre", nNombre);
                    personaMap.put("ocupacion", nTrabajo);
                    personaMap.put("peso", nPeso);

                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                String clave=datasnapshot.getKey();

                                mDatabase.child(clave).updateChildren(personaMap);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(EditProfileDataActivity.this,"Los datos se han editado correctamente",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditProfileDataActivity.this,"Debe ingresar el email para editar su información",Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

}
