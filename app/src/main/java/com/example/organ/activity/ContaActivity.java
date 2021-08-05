package com.example.organ.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.organ.R;
import com.example.organ.config.ConfigFirebase;
import com.example.organ.helper.Base64Custom;
import com.example.organ.helper.Permissao;
import com.example.organ.helper.UsuarioFirebase;
import com.example.organ.model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContaActivity extends AppCompatActivity {


    private TextView txtNomePerfil;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private ImageButton imgCamera, imgGaleria;
    private static final int SELECAO_CAMERA = 100;
    private static final int SELECAO_GALERIA = 200;
    private CircleImageView circleImageViewPerfil;
    private StorageReference storageReference;
    private String idUser;
    private Usuario usuarioLogado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);

        storageReference = ConfigFirebase.getFirebaeStorage();
        idUser = UsuarioFirebase.getIdUser();
        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();

        //Validar permissões
        Permissao.validarPermissoes(permissoesNecessarias, this,1);

        imgCamera = findViewById(R.id.imgCamera);
        imgGaleria = findViewById(R.id.imgGaleria);
        circleImageViewPerfil = findViewById(R.id.CircleImgFotoPerfil);
        txtNomePerfil = findViewById(R.id.txtNomePerfil);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Conta");
        setSupportActionBar(toolbar);

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager()) != null)
                startActivityForResult(i, SELECAO_CAMERA);

            }
        });


        imgGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                if(i.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(i, SELECAO_GALERIA);
            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Recuperar dados do usuario
        FirebaseUser user = UsuarioFirebase.getUserAtual();
        Uri url = user.getPhotoUrl();
        if(url != null){
            Glide.with(ContaActivity.this)
                    .load(url)
                    .into(circleImageViewPerfil);
        }else{
            circleImageViewPerfil.setImageResource(R.drawable.img_padrao);

        }

        txtNomePerfil.setText(user.getDisplayName());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK){
            Bitmap imagem = null;

            try {

                switch (requestCode){
                    case SELECAO_CAMERA:
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                    case SELECAO_GALERIA:
                        Uri localImageSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(),localImageSelecionada);
                        break;
                }
                if(imagem != null){

                    circleImageViewPerfil.setImageBitmap(imagem);

                    FirebaseAuth usuario = ConfigFirebase.getFirebaseAuth();
                    String email = usuario.getCurrentUser().getEmail();
                    String idUser = Base64Custom.codificarBase64(email);

                    //recuperar dados na imagem para o fb
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] dadosImagem = baos.toByteArray();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70,baos);


                    //salvar imagem no fb
                    StorageReference imageRef = storageReference.
                            child("imagens")
                            .child("perfil")
                            .child(idUser)
                            .child("perfil.jpeg");

                    UploadTask uploadTask = imageRef.putBytes(dadosImagem);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    ContaActivity.this,
                                    "Erro ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(
                                    ContaActivity.this,
                                    "Sucesso ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();

                            Uri url = taskSnapshot.getUploadSessionUri();
                            atualizaFotoUser(url);
                        }
                    });

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public void atualizaFotoUser(Uri url){
        boolean retorno = UsuarioFirebase.atualizarFotoUsuario(url);
        if(retorno) {
            usuarioLogado.setFoto(url.toString());
            usuarioLogado.atualizar();
            Toast.makeText(
                    this,
                    "Sua foto foi atualizada!",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        for(int permissaoResultado: grantResults){
            if(permissaoResultado == PackageManager.PERMISSION_DENIED){
                AlertValidationPermission();
            }
        }
    }


    private void AlertValidationPermission(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setCancelable(false);
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
