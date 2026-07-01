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
public class AutorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCadastrarAutorComSucesso() throws Exception {
        String autorJson = """
            {
              "nome": "Autor Teste Cadastro",
              "paisOrigem": "Brasil"
            }
        """;

        mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autorJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Autor Teste Cadastro"));
    }

    @Test
    public void deveListarAutores() throws Exception {
        mockMvc.perform(get("/autores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").exists());
    }

    @Test
    public void deveListarLivrosDeAutor() throws Exception {
        // cria autor e captura ID
        String autorJson = """
            {
              "nome": "Autor Teste Livros",
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
              "titulo": "Livro Teste Autor",
              "anoPublicacao": 2024,
              "lingua": "Português",
              "autores": [
                { "id": %d }
              ]
            }
        """, autorId);

        mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(livroJson))
                .andExpect(status().isOk());

        // lista livros do autor
        mockMvc.perform(get("/autores/" + autorId + "/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Livro Teste Autor"));
    }
}
