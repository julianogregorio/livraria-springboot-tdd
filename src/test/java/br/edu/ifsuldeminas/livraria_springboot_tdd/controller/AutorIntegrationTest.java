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
public class AutorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCadastrarAutorComSucesso() throws Exception {
        String autorJson = """
            {
              "nome": "José de Alencar",
              "paisOrigem": "Brasil"
            }
        """;

        mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autorJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("José de Alencar"));
    }

    @Test
    public void deveListarAutores() throws Exception {
        mockMvc.perform(get("/autores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").exists());
    }

    @Test
    public void deveListarLivrosDeAutor() throws Exception {
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

        // Depois cria um livro vinculado a esse autor
        String livroJson = """
            {
              "titulo": "Memórias Póstumas de Brás Cubas",
              "anoPublicacao": 1881,
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

        // Agora lista os livros do autor
        mockMvc.perform(get("/autores/1/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Memórias Póstumas de Brás Cubas"));
    }
}
