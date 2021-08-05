package com.example.organ.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organ.R;
import com.example.organ.config.ConfigFirebase;
import com.example.organ.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputEditText varEmail;
    private String email;
    private Button btnRecupSenha;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        auth = FirebaseAuth.getInstance();

        varEmail = findViewById(R.id.txtEmailRecup);
        email = varEmail.getText().toString();
        btnRecupSenha = findViewById(R.id.btnRecuperar);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Recuperar acesso");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    public void validationReset(View view) {
        String txtEmail = varEmail.getText().toString();

        if (!txtEmail.isEmpty()) {
            reset();
        }else {
            Toast.makeText(
                    this,
                    "Preencha com seu e-mail",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void reset(){
        auth.sendPasswordResetEmail(varEmail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(
                                    ResetPasswordActivity.this,
                                    "Recuperação de acesso iniciada. Email enviado.",
                                    Toast.LENGTH_SHORT).show();
                            varEmail.setText("");
                        }else{
                            Toast.makeText(ResetPasswordActivity.this,
                                    "Erro ao recuperar senha",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
