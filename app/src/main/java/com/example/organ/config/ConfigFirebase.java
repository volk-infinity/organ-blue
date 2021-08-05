package com.example.organ.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfigFirebase {

    private static DatabaseReference db;
    private static FirebaseAuth auth;
    private static StorageReference storage;

    //retorna a instancia do FirebaseDB
    public static DatabaseReference getFirebaseDatabase(){
        if(db == null){
            db = FirebaseDatabase.getInstance().getReference();
        }
        return db;
    }

    //retorna a instancia do FirebaseAuth
    public static FirebaseAuth getFirebaseAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    //retorna a instancia do storage
    public static StorageReference getFirebaeStorage(){
            if (storage == null) {
                storage = FirebaseStorage.getInstance().getReference();
            }
            return storage;
    }
}
