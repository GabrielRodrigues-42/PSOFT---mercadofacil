package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.VolatilLoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class VolatilLoteRepositoryTest {


    @Autowired
    VolatilLoteRepository driver;


    Lote lote;
    Lote resultado;
    Produto produto;


    @BeforeEach
    void setup() {
        produto = Produto.builder()
                .id(1L)
                .nome("Produto Base")
                .codigoBarra("123456789")
                .fabricante("Fabricante Base")
                .preco(125.36)
                .build();
        lote = Lote.builder()
                .id(1L)
                .numeroDeItens(100)
                .produto(produto)
                .build();
    }


    @AfterEach
    void tearDown() {
        produto = null;
        driver.deleteAll();
    }

    //Save

    @Test
    @DisplayName("Adicionar o primeiro Lote no repositorio de dados")
    void salvarPrimeiroLote() {
        resultado = driver.save(lote);


        assertEquals(driver.findAll().size(),1);
        assertEquals(resultado.getId().longValue(), lote.getId().longValue());
        assertEquals(resultado.getProduto(), produto);
    }


    @Test
    @DisplayName("Adicionar o segundo Lote (ou posterior) no repositorio de dados")
    void salvarSegundoLoteOuPosterior() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);


        resultado = driver.save(loteExtra);


        assertEquals(driver.findAll().size(),2);
        assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
        assertEquals(resultado.getProduto(), produtoExtra);


    }

    //Delete

    @Test
    @DisplayName("Remover o primeiro Lote do repositório de dados")
    void deletarPrimeiroLote() {
        driver.save(lote);
        driver.delete(lote);

        assertEquals(driver.findAll().size(), 0);

    }

    @Test
    @DisplayName("Remover o segundo Lote (ou posterior) do repositório de dados")
    void deletarSegundoLoteOuPosterior() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);
        driver.save(loteExtra);
        driver.delete(lote);

        assertEquals(driver.findAll().size(), 1);

    }

    //Find

    @Test
    @DisplayName("Encontrar um Lote no repositório de dados único")
    void acharPrimeiroLote() {
        driver.save(lote);
        resultado = driver.find(2L);

        assertEquals(driver.findAll().size(),1);
        assertEquals(resultado.getId().longValue(), lote.getId().longValue());
        assertEquals(resultado.getProduto(), produto);

    }

    @Test
    @DisplayName("Encontrar um Lote no repositório de dados múltiplo")
    void acharAlgumLote() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(4L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);
        driver.save(loteExtra);
        resultado = driver.find(4L);

        assertEquals(driver.findAll().size(),2);
        assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
        assertEquals(resultado.getProduto(), produtoExtra);

    }

    //Uptade

    @Test
    @DisplayName("Encontrar um Lote no repositório de dados único")
    void atualizar() {
        resultado = driver.save(lote);
        assertEquals(driver.findAll().size(),1);
        assertEquals(resultado.getId().longValue(), lote.getId().longValue());
        assertEquals(resultado.getProduto(), produto);
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(4L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        resultado = driver.update(loteExtra);
        assertEquals(driver.findAll().size(),2);
        assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
        assertEquals(resultado.getProduto(), produtoExtra);


    }






}
