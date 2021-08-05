package com.example.organ.fragment;


import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.organ.R;
import com.example.organ.activity.LoginActivity;
import com.example.organ.activity.MainActivity;
import com.example.organ.config.ConfigFirebase;
import com.example.organ.helper.Base64Custom;
import com.example.organ.helper.UsuarioFirebase;
import com.example.organ.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


/**
 * A simple {@link Fragment} subclass.
 */

public class RegisterFragment extends Fragment {


    public RegisterFragment() {
        // Required empty public constructor
    }


    private Button btnRegistrar;
    private TextInputEditText varNome, varEmail, varSenha;
    private FirebaseAuth auth;
    private String email, senha;
    private RadioButton rbtnTermo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        varNome = view.findViewById(R.id.txtNomeUser);
        varEmail = view.findViewById(R.id.txtEmailRegister);
        varSenha = view.findViewById(R.id.txtSenhaRegister);
        email = varEmail.getText().toString();
        senha = varSenha.getText().toString();

        btnRegistrar = view.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationUser(email, senha);
            }
        });

        return view;
    }


    public void validationUser(String email, String senha){
        String txtNome = varNome.getText().toString();
        String txtEmail = varEmail.getText().toString();
        String txtSenha = varSenha.getText().toString();
        rbtnTermo = getView().findViewById(R.id.rbtnTermo);

        if(!txtNome.isEmpty() && !txtEmail.isEmpty() && !txtSenha.isEmpty())
            if(!txtNome.isEmpty()) {
                if (!txtEmail.isEmpty()) {
                    if (!txtSenha.isEmpty()) {
                        if(rbtnTermo.isChecked()) {

                            Usuario usuario = new Usuario();
                            usuario.setNome(txtNome);
                            usuario.setEmail(txtEmail);
                            usuario.setSenha(txtSenha);

                            registerUser(usuario);
                        }
                        else{
                            Toast.makeText(getActivity(),
                                    "Aceite os termos e condições",
                                    Toast.LENGTH_LONG).show();
                        }

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
            else{
                Toast.makeText(getActivity(),
                        "Nome não preenchido!",
                        Toast.LENGTH_LONG).show();
            }
        else{
            Toast.makeText(getActivity(),
                    "Preencha TODOS os campos!",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void registerUser(final Usuario usuario){
        auth = ConfigFirebase.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(getActivity(),
                            "Sucesso ao cadastrar usuário",
                            Toast.LENGTH_LONG).show();
                    UsuarioFirebase.atualizarNomeUsuario(usuario.getNome());
                    getActivity().finish();
                    Intent intent = new Intent(getContext().getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    try{
                        String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                        usuario.setIdUsuario(idUsuario);
                        usuario.salvar();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    String excecao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao ="Digite uma senha mais forte!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Por favor, digite um e-mail válido";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "Essa conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
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
