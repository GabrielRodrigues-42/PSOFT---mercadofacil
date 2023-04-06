package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("Testes do Repositório de Produtos")
public class ProdutoRepositoryTests {
    @Autowired
    ProdutoRepository driver;
    Produto produto;
    @BeforeEach void setup() {
        produto = TestUtils.criarProduto(
                10L, "Produto Dez", 100.00,
                "1234567890123", "Empresa 10"
        );
    }
    @Test
    @DisplayName("Quando criar um novo produto com dados válidos")
    void testQuandoCriarProduto() {
        Produto resultado = (Produto) driver.save(produto);
        assertNotNull(resultado);
        assertEquals("Produto Dez", resultado.getNome());
        assertEquals("Fabricante Dez", resultado.getFabricante());
        assertEquals("1234567890123", resultado.getCodigoBarra());
        assertEquals(100.00, resultado.getPreco());
    }
}
