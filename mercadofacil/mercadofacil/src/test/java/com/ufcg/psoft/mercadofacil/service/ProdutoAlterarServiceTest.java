package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes para alteração de produto")
public class ProdutoAlterarServiceTest {

    @Autowired
    ProdutoAlterarService driver;
    @MockBean
    ProdutoRepository<Produto, Long> produtoRepository;
    Produto produto;
    @BeforeEach
    void setup() {
        Mockito.when(produtoRepository.find(10L))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500100")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez")
                        .preco(450.00)
                        .build()
                );
        produto = produtoRepository.find(10L);
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500104")
                        .nome("Nome Produto Alterado")
                        .fabricante("Nome Fabricante Alterado")
                        .preco(500.00)
                        .build()
                );
    }
    @Test
    @DisplayName("Quando altero o nome do produto com um nome válido")
    void alterarNomeDoProduto() {
        produto.setNome("Nome Produto Alterado");
        Produto resultado = driver.alterar(produto);
        assertEquals("Nome Produto Alterado", resultado.getNome());
    }

    @Test
    @DisplayName("Quando o preço é menor ou igual a zero")
    void precoMenorIgualZero() {
        produto.setPreco(0.0);
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Preço inválido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o nome é vazio")
    void nomeVazio() {
        produto.setNome("");
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Nome inválido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o nome é nulo")
    void nomeNulo() {
        produto.setNome(null);
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Nome inválido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o Código de Barra é vazio")
    void codigoBarraVazio() {
        produto.setCodigoBarra("");
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Código de Barra vazio!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o Código de Barra é nulo")
    void codigoBarraNulo() {
        produto.setCodigoBarra(null);
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Código de Barra nulo!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o fabricante é vazio")
    void fabricanteVazio() {
        produto.setFabricante("");
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Fabricante inválido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o fabricante é nulo")
    void fabricanteNulo() {
        produto.setFabricante(null);
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Fabricante inválido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o Código de Barras é inválido")
    void codigoBarrasInvalido() {
        produto.setCodigoBarra("789913750010");
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Código de Barra pequeno!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o Código de Barras é inválido")
    void codigoBarrasInvalido2() {
        produto.setCodigoBarra("7899137500104");
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Código de Barra errado!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o Código de Barras é estrangeiro")
    void codigoBarrasEstrangeiro() {
        produto.setCodigoBarra("7799137500100");
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Código de Barra estrangeiro!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o Código de Barras é competidor")
    void codigoBarrasCompetidor() {
        produto.setCodigoBarra("7899147500100");
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        assertEquals("Código de Barra competidor!", thrown.getMessage());
    }

}
