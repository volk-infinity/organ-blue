package com.example.organ.helper;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.organ.config.ConfigFirebase;
import com.example.organ.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UsuarioFirebase {

    public static String getIdUser(){

        FirebaseAuth usuario = ConfigFirebase.getFirebaseAuth();
        String email = usuario.getCurrentUser().getEmail();
        String idUser = Base64Custom.codificarBase64(email);

        return idUser;
    }

    public static FirebaseUser getUserAtual(){
        FirebaseAuth usuario = ConfigFirebase.getFirebaseAuth();
        return usuario.getCurrentUser();
    }


    public static boolean atualizarNomeUsuario(String nome){

        try{
            FirebaseUser user = getUserAtual();

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nome)
                    .build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(!task.isSuccessful()){
                        Log.d("Perfil","Erro ao atualizar o nome do perfil");

                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean atualizarFotoUsuario(Uri url){

        try{
            FirebaseUser user = getUserAtual();

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(url)
                    .build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(!task.isSuccessful()){
                        Log.d("Perfil","Erro ao atualizar a foto de perfil");

                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static Usuario getDadosUsuarioLogado(){

        FirebaseUser firebaseUser = getUserAtual();

        Usuario usuario = new Usuario();
        usuario.setEmail(firebaseUser.getEmail());
        usuario.setNome(firebaseUser.getDisplayName());

        if(firebaseUser.getPhotoUrl() == null){
            usuario.setFoto("");
        }else{
            usuario.setFoto(firebaseUser.getPhotoUrl().toString());
        }

        return usuario;
    }
}
