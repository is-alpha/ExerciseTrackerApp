package com.example.exercisetrackerapp.ui.editProfile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class EditPasswordActivity extends AppCompatActivity {
    private EditText mEditTextEmail;
    private Button mBtnEditPassword;

    private String email= "";

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reset_password);

        mAuth=FirebaseAuth.getInstance();
        mDialog= new ProgressDialog(this);

        mEditTextEmail= (EditText) findViewById(R.id.editTextEmail);
        mBtnEditPassword=(Button) findViewById(R.id.btnEditPassword);

        mBtnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=mEditTextEmail.getText().toString();

                if(!email.isEmpty()){
                    mDialog.setMessage("Espere un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();
                }
                else {
                    Toast.makeText(EditPasswordActivity.this,"Debe ingresar el email para editar su contraseña",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void resetPassword(){

        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditPasswordActivity.this,"Se ha enviado un correo para restablecer contraseña",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditPasswordActivity.this,"No se pudo enviar el correo de restablecer contraseña",Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }
        });
    }
}
