package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Produtos")
public class ProdutoV1ControllerTests {
    @Autowired
    MockMvc driver;

    @Autowired
    ProdutoRepository<Produto, Long> produtoRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Produto produto;

    @BeforeEach
    void setup() {
        produto = produtoRepository.find(10L);
    }

    @AfterEach
    void tearDown() {
        produto = null;
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de campos obrigatórios")
    class ProdutoValidacaoCamposObrigatorios {

        @Test
        @DisplayName("Quando alteramos o nome do produto com dados válidos")
        void quandoAlteramosNomeDoProdutoValido() throws Exception {
            // Arrange
            produto.setNome("Produto Dez Alterado");

            // Act
            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            // Assert
            assertEquals(resultado.getNome(), "Produto Dez Alterado");
        }

    }

        @Test
        @DisplayName("Quando alteramos o nome do produto com dados inválidos")
        void quandoAlteramosNomeDoProdutoInvalido() throws Exception {
            // Arrange
            produto.setNome(null);

            // Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Nome Inválido", thrown.getMessage());
        }

    @Nested
    @DisplayName("Conjunto de casos de verificação da regra sobre o preço")
    class ProdutoValidacaoRegrasDoPreco {
        @Test
        @DisplayName("Quando alteramos o nome do produto com dados inválidos")
        void precoIgualAZero() throws Exception {
            //Arrange
            produto.setPreco(0.0);
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Preço Inválido", thrown.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos o preço do produto com dados válidos")
        void quandoAlteramosPrecoValido() throws Exception {
            // Arrange
            produto.setPreco(42.00);

            // Act
            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            // Assert
            assertEquals(42.00, resultado.getPreco());
        }

    }

    @Nested
    @DisplayName("Conjunto de casos de verificação da validação do código de barras")
    class ProdutoValidacaoCodigoDeBarras {
        @Test
        @DisplayName("Quando alteramos o Código de Barras do produto com dados válidos")
        void quandoAlteramosCodValido() throws Exception {
            // Arrange
            produto.setCodigoBarra("7899137500100");

            // Act
            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            // Assert
            assertEquals("7899137500100", resultado.getCodigoBarra());
        }

        @Test
        @DisplayName("Quando alteramos o Código de Barras do produto com dados inválidos")
        void quandoAlteramosCodPequeno() throws Exception {
            // Arrange
            produto.setCodigoBarra("234");
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Código de Barras Pequeno", thrown.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos o Código de Barras do produto com dados inválidos")
        void quandoAlteramosCodEstrangeiro() throws Exception {
            // Arrange
            produto.setCodigoBarra("1239137500100");
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Código de Barras Estrangeiro", thrown.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos o Código de Barras do produto com dados inválidos")
        void quandoAlteramosCodCompetidor() throws Exception {
            // Arrange
            produto.setCodigoBarra("7891234500100");
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Código de Barras Competidor", thrown.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos o Código de Barras do produto com dados inválidos")
        void quandoAlteramosCodErrado() throws Exception {
            // Arrange
            produto.setCodigoBarra("7899137500101");
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Código de Barras Matematicamente Errado", thrown.getMessage());
        }
    }

}
