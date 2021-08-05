package com.example.organ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organ.R;
import com.example.organ.model.Categoria;

import java.util.List;

public class ListaCategoriaAdapter extends RecyclerView.Adapter<ListaCategoriaAdapter.MyViewHolder> {

    private List<Categoria> categorias;

    public ListaCategoriaAdapter(List<Categoria>listaCategoria) {
        this.categorias = listaCategoria;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemListaCategoria = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_categoria,parent,false);

        return new MyViewHolder(itemListaCategoria);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Categoria categoria = categorias.get(position);
        holder.nomeCategoria.setText(categoria.getNomeCategoria());
        holder.fotoCategoria.setImageResource(categoria.getImgProdutoCategoria());

    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView fotoCategoria;
        TextView nomeCategoria;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoCategoria = itemView.findViewById(R.id.imgProdutoCategoria);
            nomeCategoria = itemView.findViewById(R.id.txtNomeCategoria);

        }
    }

}
