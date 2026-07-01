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

        MvcResult result = mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autorJson))
                .andExpect(status().isOk())
                .andReturn();

        int autorId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

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
        String autorJson = """
            {
              "nome": "Autor Teste Listagem",
              "paisOrigem": "Brasil"
            }
        """;

        int autorId = JsonPath.read(mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autorJson))
                .andReturn().getResponse().getContentAsString(), "$.id");

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

        int livroId = JsonPath.read(mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(livroJson))
                .andReturn().getResponse().getContentAsString(), "$.id");

        mockMvc.perform(get("/livros/" + livroId + "/autores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Autor Teste Listagem"));
    }

    @Test
    public void naoDevePermitirLivroSemAutores() throws Exception {
        String livroJson = """
            {
              "titulo": "Livro Sem Autor",
              "anoPublicacao": 2024,
              "lingua": "Português",
              "autores": []
            }
        """;

        mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(livroJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void naoDevePermitirLivroDuplicado() throws Exception {
        String autorJson = """
            {
              "nome": "Autor Teste Duplicado",
              "paisOrigem": "Brasil"
            }
        """;

        int autorId = JsonPath.read(mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autorJson))
                .andReturn().getResponse().getContentAsString(), "$.id");

        String livroJson = String.format("""
            {
              "titulo": "Livro Duplicado",
              "anoPublicacao": 2024,
              "lingua": "Português",
              "autores": [
                { "id": %d }
              ]
            }
        """, autorId);

        // cria livro
        mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(livroJson))
                .andExpect(status().isOk());

        // tenta criar novamente com mesmo título
        mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(livroJson))
                .andExpect(status().isConflict());
    }
}
