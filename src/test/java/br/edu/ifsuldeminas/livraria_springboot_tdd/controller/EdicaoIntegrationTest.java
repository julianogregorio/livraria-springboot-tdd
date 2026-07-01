package br.edu.ifsuldeminas.livraria_springboot_tdd.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EdicaoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCadastrarEdicaoComSucesso() throws Exception {
        // Primeiro cria um autor
        String autorJson = """
            {
              "nome": "Machado de Assis",
              "paisOrigem": "Brasil"
            }
        """;

        mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autorJson))
                .andExpect(status().isOk());

        // Depois cria um livro vinculado ao autor
        String livroJson = """
            {
              "titulo": "Dom Casmurro",
              "anoPublicacao": 1899,
              "lingua": "Português",
              "autores": [
                { "id": 1 }
              ]
            }
        """;

        mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(livroJson))
                .andExpect(status().isOk());

        // Agora cria uma edição vinculada ao livro
        String edicaoJson = """
            {
              "anoEdicao": 1900,
              "preco": 49.90,
              "numPaginas": 256,
              "quantEstoque": 10,
              "livro": { "id": 1 }
            }
        """;

        mockMvc.perform(post("/edicoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(edicaoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.preco").value(49.90));
    }

    @Test
    public void deveFalharSePrecoNegativo() throws Exception {
        String edicaoJson = """
            {
              "anoEdicao": 1900,
              "preco": -10,
              "numPaginas": 200,
              "quantEstoque": 5,
              "livro": { "id": 1 }
            }
        """;

        mockMvc.perform(post("/edicoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(edicaoJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deveRealizarVendaComSucesso() throws Exception {
        // Realiza venda de 2 unidades da edição com id=1
        mockMvc.perform(post("/edicoes/1/vender")
                .param("quantidade", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantEstoque").value(8)); // estoque inicial era 10
    }
}
