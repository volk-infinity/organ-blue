package com.example.organ.model;

public class Produtos {
    private String nomeProd;
    private String precoProd;
    private String descricao;
    private int imgProd;


    public Produtos() {

    }

    public Produtos(String nomeProd, String precoProd, int imgProd) {
        this.nomeProd = nomeProd;
        this.precoProd = precoProd;
        this.imgProd = imgProd;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeProd() {
        return nomeProd;
    }

    public void setNomeProd(String nomeProd) {
        this.nomeProd = nomeProd;
    }

    public String getPrecoProd() {
        return precoProd;
    }

    public void setPrecoProd(String precoProd) {
        this.precoProd = precoProd;
    }

    public int getImgProd() {
        return imgProd;
    }

    public void setImgProd(int imgProd) {
        this.imgProd = imgProd;
    }
}
