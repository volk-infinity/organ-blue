package com.example.organ;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.organ.activity.DetalhesProdutoActivity;
import com.example.organ.adapter.ListaCategoriaAdapter;
import com.example.organ.helper.RecyclerItemClickListener;
import com.example.organ.model.Categoria;
import com.example.organ.model.Produtos;
import com.example.organ.model.Usuario;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    public CategoryFragment() {
        // Required empty public constructor
    }

    private RecyclerView rcListaCategoria;
    private List<Categoria> listaCategoria = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        //Config iniciais da listagem de categoria
        rcListaCategoria = view.findViewById(R.id.rcListaCategoria);

        //config rc
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcListaCategoria.setLayoutManager(layoutManager);

        //configura adapter
        this.ListagemCategoria();
        ListaCategoriaAdapter adapter = new ListaCategoriaAdapter(listaCategoria);
        rcListaCategoria.setAdapter(adapter);



        //rcListaCategoria.setHasFixedSize(true);
        rcListaCategoria.setAdapter(adapter);


        return view;
    }

    public void ListagemCategoria(){
        Categoria c = new Categoria("Vegetais", R.drawable.img_product_7);
        this.listaCategoria.add(c);

        c = new Categoria("Sementes", R.drawable.img_product_20_soja);
        this.listaCategoria.add(c);

        c = new Categoria("Frutas", R.drawable.img_product_18);
        this.listaCategoria.add(c);

        c = new Categoria("Máquinas", R.drawable.img_product_12);
        this.listaCategoria.add(c);
    }

}
