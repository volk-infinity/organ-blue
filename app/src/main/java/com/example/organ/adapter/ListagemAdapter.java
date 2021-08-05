package com.example.organ.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organ.R;
import com.example.organ.model.Produtos;

import java.util.List;


public class ListagemAdapter extends RecyclerView.Adapter<ListagemAdapter.MyViewHolder>
{

    private List<Produtos> product;

    public ListagemAdapter(List<Produtos> listaProdutos) {
        this.product = listaProdutos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_products, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Produtos produto = product.get(position);
        holder.txtNomeProduto.setText(produto.getNomeProd());
        holder.txtPrecoProduto.setText(produto.getPrecoProd());
        holder.imgProduto.setImageResource(produto.getImgProd());
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNomeProduto;
        private TextView txtPrecoProduto;
        private ImageView imgProduto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNomeProduto = itemView.findViewById(R.id.txtNomeProduto);
            txtPrecoProduto = itemView.findViewById(R.id.txtPrecoProduto);
            imgProduto = itemView.findViewById(R.id.imgProduto);

        }
    }

}

