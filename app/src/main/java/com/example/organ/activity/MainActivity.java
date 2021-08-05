package com.example.organ.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.organ.CategoryFragment;
import com.example.organ.R;
import com.example.organ.ShopCarFragment;
import com.example.organ.config.ConfigFirebase;
import com.example.organ.fragment.MainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


public class MainActivity extends AppCompatActivity {


    //private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private FirebaseAuth auth;
    private MaterialSearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.materialSearchPrincipal);
        //Configura toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(toolbar);

        //Configurar bottom navigation view
        configuraBottomNavigationView();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewPager, new MainFragment()).commit();


        auth = ConfigFirebase.getFirebaseAuth();


/*
        referencia.child("pontos").setValue("01");

        DatabaseReference usuarios = referencia.child("usuarios");

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("FIREBASE", dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
    }

    private void configuraBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavigation);

        //configurações iniciais
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.enableShiftingMode(true);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);

        //habilitar navegação
        habilitarNavegacao(bottomNavigationViewEx);
    }

    private void habilitarNavegacao(BottomNavigationViewEx viewEx){

        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                switch (menuItem.getItemId()){
                    case R.id.ic_home:
                        fragmentTransaction.replace(R.id.viewPager, new MainFragment()).commit();
                        return true;
                    case R.id.ic_category:
                        fragmentTransaction.replace(R.id.viewPager, new CategoryFragment()).commit();
                        return true;
                    case R.id.ic_buy:
                        fragmentTransaction.replace(R.id.viewPager, new ShopCarFragment()).commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        //config botao pesquisa

        MenuItem item = menu.findItem(R.id.mnPesquisar);
        searchView.setMenuItem(item);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.mnSair :
                if(usuario.getCurrentUser() != null) {
                    logoutUser();
                    Toast.makeText(
                            this,
                            "Você deslogou",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(
                            this,
                            "Você não está logado para sair",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.mnConta:
                if(usuario.getCurrentUser() != null){
                    Conta();
                }else {
                    LogRegister();
                }
        }

        return super.onOptionsItemSelected(item);
    }

    public void logoutUser(){
        try {

            auth.signOut();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void LogRegister(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void Conta(){
        Intent intent = new Intent(MainActivity.this, ContaActivity.class);
        startActivity(intent);
    }
}
