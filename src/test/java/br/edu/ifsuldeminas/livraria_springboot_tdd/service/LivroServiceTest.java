package br.edu.ifsuldeminas.livraria_springboot_tdd.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Autor;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Livro;
import br.edu.ifsuldeminas.livraria_springboot_tdd.repository.AutorRepository;

@SpringBootTest
class LivroServiceTest {

    @Autowired
    private LivroService livroService;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    void naoDevePermitirTituloDuplicado() {
        // 🔹 Criar e salvar autor primeiro
        Autor autor = new Autor();
        autor.setNome("Autor Teste");
        autor.setPaisOrigem("Brasil");
        autor = autorRepository.save(autor);

        // 🔹 Primeiro livro
        Livro livro1 = new Livro();
        livro1.setTitulo("Título Duplicado");
        livro1.setAnoPublicacao(2024);
        livro1.setLingua("Português");
        livro1.setAutores(List.of(autor));

        livroService.salvar(livro1);

        // 🔹 Segundo livro com mesmo título
        Livro livro2 = new Livro();
        livro2.setTitulo("Título Duplicado");
        livro2.setAnoPublicacao(2025);
        livro2.setLingua("Português");
        livro2.setAutores(List.of(autor));

        assertThrows(RuntimeException.class, () -> livroService.salvar(livro2));
    }
}
