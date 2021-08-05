package com.example.organ.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organ.R;
import com.example.organ.activity.MainActivity;
import com.example.organ.activity.ResetPasswordActivity;
import com.example.organ.config.ConfigFirebase;
import com.example.organ.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogFragment extends Fragment {


    public LogFragment() {
        // Required empty public constructor
    }

    private TextInputEditText varEmail, varSenha;
    private String email, senha;
    private FirebaseAuth auth;
    private Button btnEntrar;
    private TextView btnRecupSenha;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log, container, false);

        varEmail = view.findViewById(R.id.txtEmailLog);
        varSenha = view.findViewById(R.id.txtSenhaLog);
        email = varEmail.getText().toString();
        senha = varSenha.getText().toString();
        auth = ConfigFirebase.getFirebaseAuth();

        btnEntrar = view.findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationLogUser(email, senha);
            }
        });

        btnRecupSenha = view.findViewById(R.id.txtForgot);
        btnRecupSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRecupSenha();
            }
        });

        return view;
    }

    public void abrirRecupSenha(){
        Intent i = new Intent(getActivity(), ResetPasswordActivity.class);
        startActivity(i);
    }


    public void validationLogUser(String email, String senha) {
        String txtEmail = varEmail.getText().toString();
        String txtSenha = varSenha.getText().toString();


        if (txtEmail.isEmpty() && txtSenha.isEmpty()) {
            Toast.makeText(getActivity(),
                    "Preencha TODOS os campos!",
                    Toast.LENGTH_LONG).show();
        }
        else{
            if (!txtEmail.isEmpty()) {
                if (!txtSenha.isEmpty()) {
                    Usuario usuario = new Usuario();
                    usuario.setEmail(txtEmail);
                    usuario.setSenha(txtSenha);

                    logUser(usuario);

                } else {
                    Toast.makeText(getActivity(),
                            "Senha não preenchida!",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(),
                        "E-mail não preenchido!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void logUser(Usuario usuario){
        auth.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(getActivity(),
                            "Sucesso ao logar!",
                            Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    Intent intent = new Intent(getContext().getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    String excecao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao ="Usuário não está cadastrado";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "E-mail e senha não correspondem a usuário cadastrado";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "Essa conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao logar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(),
                            excecao,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
