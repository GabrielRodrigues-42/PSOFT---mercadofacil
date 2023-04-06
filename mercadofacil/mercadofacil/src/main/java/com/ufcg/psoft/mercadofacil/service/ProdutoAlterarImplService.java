package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoAlterarImplService implements ProdutoAlterarService {
    @Autowired
    ProdutoRepository<Produto, Long> produtoRepository;

    private boolean validar(String codigoBarra) {
        //7899137500100
        int[] numeros = codigoBarra.chars().map(Character::getNumericValue).toArray();
        int somaPares = numeros[1] + numeros[3] + numeros[5] + numeros[7] + numeros[9] + numeros[11];
        int somaImpares = numeros[0] + numeros[2] + numeros[4] + numeros[6] + numeros[8] + numeros[10];
        int somaParesTriplo = somaPares * 3;
        int resultado = somaParesTriplo + somaImpares;
        int digitoVerificador = resultado % 10;
        return digitoVerificador == numeros[12];
    }
    @Override
    public Produto alterar(Produto produtoAlterado) {
        if(produtoAlterado.getPreco() <= 0) {
            throw new RuntimeException("Preço inválido!");
        }
        if(produtoAlterado.getNome() == null) {
            throw new RuntimeException("Nome inválido!");
        }
        if(produtoAlterado.getNome().equals("")) {
            throw new RuntimeException("Nome inválido!");
        }
        if(produtoAlterado.getCodigoBarra() == null) {
            throw new RuntimeException("Código de Barra nulo!");
        }
        if(produtoAlterado.getCodigoBarra().equals("")) {
            throw new RuntimeException("Código de Barra vazio!");
        }
        if(produtoAlterado.getCodigoBarra().length() != 13) {
            throw new RuntimeException("Código de Barra pequeno!");
        }
        if(!String.valueOf(produtoAlterado.getCodigoBarra().charAt(0)).equals("7") ||
                !String.valueOf(produtoAlterado.getCodigoBarra().charAt(1)).equals("8") ||
                !String.valueOf(produtoAlterado.getCodigoBarra().charAt(2)).equals("9")) {
            throw new RuntimeException("Código de Barra estrangeiro!");
        }
        if(!String.valueOf(produtoAlterado.getCodigoBarra().charAt(3)).equals("9") ||
                !String.valueOf(produtoAlterado.getCodigoBarra().charAt(4)).equals("1") ||
                !String.valueOf(produtoAlterado.getCodigoBarra().charAt(5)).equals("3") ||
                !String.valueOf(produtoAlterado.getCodigoBarra().charAt(6)).equals("7") ||
                !String.valueOf(produtoAlterado.getCodigoBarra().charAt(7)).equals("5")) {
            throw new RuntimeException("Código de Barra competidor!");
        }
        if(validar(produtoAlterado.getCodigoBarra()) == false){
            throw new RuntimeException("Código de Barra errado!");
        }
        if(produtoAlterado.getFabricante() == null) {
            throw new RuntimeException("Fabricante inválido!");
        }
        if(produtoAlterado.getFabricante().equals("")) {
            throw new RuntimeException("Fabricante inválido!");
        }
        return produtoRepository.update(produtoAlterado);
    }
}

