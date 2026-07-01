package br.edu.ifsuldeminas.livraria_springboot_tdd.service;

import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Livro;
import br.edu.ifsuldeminas.livraria_springboot_tdd.service.LivroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class LivroServiceTest {

    @Autowired
    private LivroService livroService;

    @Test
    void naoDevePermitirTituloDuplicado() {
        Livro livro1 = new Livro();
        livro1.setTitulo("Dom Casmurro");
        livro1.setAnoPublicacao(1899);
        livro1.setLingua("Português");
        livroService.salvar(livro1);

        Livro livro2 = new Livro();
        livro2.setTitulo("Dom Casmurro");
        livro2.setAnoPublicacao(1900);
        livro2.setLingua("Português");

        assertThrows(RuntimeException.class, () -> livroService.salvar(livro2));
    }
}
