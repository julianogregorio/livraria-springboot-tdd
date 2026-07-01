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
public class LivroIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCadastrarLivroComAutor() throws Exception {
        String autorJson = """
            {
              "nome": "Machado de Assis",
              "paisOrigem": "Brasil"
            }
        """;

        // cria autor
        mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autorJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

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

        // cria livro
        mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(livroJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Dom Casmurro"));
    }

    @Test
    public void deveListarAutoresDeLivro() throws Exception {
        mockMvc.perform(get("/livros/1/autores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Machado de Assis"));
    }
}
