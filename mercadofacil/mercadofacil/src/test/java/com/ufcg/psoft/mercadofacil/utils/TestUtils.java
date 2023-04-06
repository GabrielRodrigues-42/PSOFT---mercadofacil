package com.ufcg.psoft.mercadofacil.utils;

import com.ufcg.psoft.mercadofacil.model.Produto;

public class TestUtils {

    public static Produto criarProduto(Long id, String nome, double preco, String codDeBarras, String fabricante) {
        Produto produto = Produto.builder()
                .id(id)
                .codigoBarra(codDeBarras)
                .nome(nome)
                .fabricante(fabricante)
                .preco(preco)
                .build();
        return produto;

    }
}
