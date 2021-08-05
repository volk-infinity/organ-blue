package com.example.organ;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCarFragment extends Fragment {


    public ShopCarFragment() {
        // Required empty public constructor
    }


    private Button btnFinalizar;
    private ImageButton somaButton, subtraiButton;
    private TextView qtdeTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_car, container, false);

        btnFinalizar = view.findViewById(R.id.btnFinalizarCompra);
        somaButton = view.findViewById(R.id.somaButton);
        subtraiButton = view.findViewById(R.id.diminuiButton);
        qtdeTextView =  view.findViewById(R.id.qtdeTextView);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "É necessário logar primeiro", Toast.LENGTH_SHORT).show();
            }
        });

        somaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer qtde =  Integer.parseInt(qtdeTextView.getText().toString());
                qtde += 1;
                qtdeTextView.setText(qtde.toString());
                Toast.makeText(getContext(), qtde.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        subtraiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer qtde =  Integer.parseInt(qtdeTextView.getText().toString());
                qtde -= 1;
                qtdeTextView.setText(qtde.toString());
                Toast.makeText(getContext(), qtde.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }



}
