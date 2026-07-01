package br.edu.ifsuldeminas.livraria_springboot_tdd.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EdicaoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCadastrarEdicaoComSucesso() throws Exception {
        // cria autor
        String autorJson = """
            {
              "nome": "Autor Teste Edicao",
              "paisOrigem": "Brasil"
            }
        """;

        MvcResult autorResult = mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autorJson))
                .andExpect(status().isOk())
                .andReturn();

        int autorId = JsonPath.read(autorResult.getResponse().getContentAsString(), "$.id");

        // cria livro vinculado ao autor
        String livroJson = String.format("""
            {
              "titulo": "Livro Teste Edicao",
              "anoPublicacao": 2024,
              "lingua": "Português",
              "autores": [
                { "id": %d }
              ]
            }
        """, autorId);

        MvcResult livroResult = mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(livroJson))
                .andExpect(status().isOk())
                .andReturn();

        int livroId = JsonPath.read(livroResult.getResponse().getContentAsString(), "$.id");

        // cria edição vinculada ao livro
        String edicaoJson = String.format("""
            {
              "anoEdicao": 2025,
              "preco": 49.90,
              "numPaginas": 256,
              "quantEstoque": 10,
              "livro": { "id": %d }
            }
        """, livroId);

        mockMvc.perform(post("/edicoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(edicaoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.preco").value(49.90));
    }

    @Test
    public void deveFalharSePrecoNegativo() throws Exception {
        // aqui também precisamos de um livro válido antes
        String autorJson = """
            {
              "nome": "Autor Teste PrecoNegativo",
              "paisOrigem": "Brasil"
            }
        """;

        int autorId = JsonPath.read(mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autorJson))
                .andReturn().getResponse().getContentAsString(), "$.id");

        String livroJson = String.format("""
            {
              "titulo": "Livro Teste PrecoNegativo",
              "anoPublicacao": 2024,
              "lingua": "Português",
              "autores": [
                { "id": %d }
              ]
            }
        """, autorId);

        int livroId = JsonPath.read(mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(livroJson))
                .andReturn().getResponse().getContentAsString(), "$.id");

        String edicaoJson = String.format("""
            {
              "anoEdicao": 2025,
              "preco": -10,
              "numPaginas": 200,
              "quantEstoque": 5,
              "livro": { "id": %d }
            }
        """, livroId);

        mockMvc.perform(post("/edicoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(edicaoJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deveRealizarVendaComSucesso() throws Exception {
        // cria autor, livro e edição primeiro
        String autorJson = """
            {
              "nome": "Autor Teste Venda",
              "paisOrigem": "Brasil"
            }
        """;

        int autorId = JsonPath.read(mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autorJson))
                .andReturn().getResponse().getContentAsString(), "$.id");

        String livroJson = String.format("""
            {
              "titulo": "Livro Teste Venda",
              "anoPublicacao": 2024,
              "lingua": "Português",
              "autores": [
                { "id": %d }
              ]
            }
        """, autorId);

        int livroId = JsonPath.read(mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(livroJson))
                .andReturn().getResponse().getContentAsString(), "$.id");

        String edicaoJson = String.format("""
            {
              "anoEdicao": 2025,
              "preco": 59.90,
              "numPaginas": 300,
              "quantEstoque": 10,
              "livro": { "id": %d }
            }
        """, livroId);

        int edicaoId = JsonPath.read(mockMvc.perform(post("/edicoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(edicaoJson))
                .andReturn().getResponse().getContentAsString(), "$.id");

        // realiza venda de 2 unidades
        mockMvc.perform(post("/edicoes/" + edicaoId + "/vender")
                .param("quantidade", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantEstoque").value(8));
    }
}
