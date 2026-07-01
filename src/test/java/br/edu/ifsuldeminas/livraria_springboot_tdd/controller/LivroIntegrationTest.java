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
public class LivroIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCadastrarLivroComAutor() throws Exception {
        String autorJson = """
            {
              "nome": "Autor Teste Livro",
              "paisOrigem": "Brasil"
            }
        """;

        // cria autor e captura o ID retornado
        MvcResult result = mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autorJson))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        int autorId = JsonPath.read(response, "$.id");

        // cria livro vinculado ao autor criado
        String livroJson = String.format("""
            {
              "titulo": "Dom Casmurro",
              "anoPublicacao": 1899,
              "lingua": "Português",
              "autores": [
                { "id": %d }
              ]
            }
        """, autorId);

        mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(livroJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Dom Casmurro"));
    }

    @Test
    public void deveListarAutoresDeLivro() throws Exception {
        // cria autor
        String autorJson = """
            {
              "nome": "Autor Teste Listagem",
              "paisOrigem": "Brasil"
            }
        """;

        MvcResult result = mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autorJson))
                .andExpect(status().isOk())
                .andReturn();

        int autorId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        // cria livro vinculado ao autor
        String livroJson = String.format("""
            {
              "titulo": "Memórias Póstumas de Brás Cubas",
              "anoPublicacao": 1881,
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

        // lista autores do livro criado
        mockMvc.perform(get("/livros/" + livroId + "/autores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Autor Teste Listagem"));
    }
}
