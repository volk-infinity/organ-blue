package com.example.organ.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.organ.R;
import com.example.organ.activity.DetalhesProdutoActivity;
import com.example.organ.adapter.ListagemAdapter;
import com.example.organ.helper.RecyclerItemClickListener;
import com.example.organ.model.Produtos;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    private RecyclerView recyclerListagem;
    private List<Produtos> produtos = new ArrayList<>();
    private ImageButton btnFavoritar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        //RecyclerView - Listagem dos Produtos
        recyclerListagem = view.findViewById(R.id.rcListagem);

        //Define Layout
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerListagem.setLayoutManager(layoutManager);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerListagem.setLayoutManager(layoutManager);

        //Define Adapter
        this.prepararProdutos();
        ListagemAdapter adapter = new ListagemAdapter(produtos);
        recyclerListagem.setAdapter(adapter);

        recyclerListagem.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerListagem,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent i = new Intent(getActivity(), DetalhesProdutoActivity.class);
                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );


        return view;
    }

    public void prepararProdutos(){

        Produtos p = new Produtos("Pimentão", "R$2,00", R.drawable.img_product_1);
        this.produtos.add(p);

        p = new Produtos("Morango", "R$5,65", R.drawable.img_product_2);
        this.produtos.add(p);

        p = new Produtos("Ervilha", "R$2,50", R.drawable.img_product_3);
        this.produtos.add(p);

        p = new Produtos("Repolho", "R$7,00", R.drawable.img_product_4);
        this.produtos.add(p);

        p = new Produtos("Tomate", "R$3,25", R.drawable.img_product_5);
        this.produtos.add(p);

        p = new Produtos("Couve-flor", "R$10,00", R.drawable.img_product_6);
        this.produtos.add(p);

        p = new Produtos("Cenoura", "R$6,00", R.drawable.img_product_7);
        this.produtos.add(p);

        p = new Produtos("Cebola", "R$4,99", R.drawable.img_product_8);
        this.produtos.add(p);

        p = new Produtos("Maçã", "R$3,50", R.drawable.img_product_9);
        this.produtos.add(p);

        p = new Produtos("Alho", "R$1,90", R.drawable.img_product_10);
        this.produtos.add(p);

        p = new Produtos("Pimenta", "R$3,00", R.drawable.img_product_11);
        this.produtos.add(p);

        p = new Produtos("Trator Peq.", "R$R$63.489,00", R.drawable.img_product_12);
        this.produtos.add(p);

        p = new Produtos("Semente Abóbora", "R$5,00", R.drawable.img_product_19_abobora);
        this.produtos.add(p);

        p = new Produtos("Trator agro", "R$65.000", R.drawable.img_product_13);
        this.produtos.add(p);

        p = new Produtos("Pá", "R$18,90", R.drawable.img_product_14);
        this.produtos.add(p);

        p = new Produtos("ancinho c/ cabo", "R$ 28,90", R.drawable.img_product_15);
        this.produtos.add(p);

        p = new Produtos("Semente Girassol", "R$4,00", R.drawable.img_product_20_girassol);
        this.produtos.add(p);

        p = new Produtos("Sacho 2 pontas", "R$35,00", R.drawable.img_product_16);
        this.produtos.add(p);

        p = new Produtos("Laranja", "R$7,00", R.drawable.img_product_17);
        this.produtos.add(p);

        p = new Produtos("Maçã Fuji", "R$3,00", R.drawable.img_product_18);
        this.produtos.add(p);

        p = new Produtos("Sementes soja", "R$1,50", R.drawable.img_product_20_soja);
        this.produtos.add(p);

    }

}
