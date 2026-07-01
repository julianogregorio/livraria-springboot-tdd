package br.edu.ifsuldeminas.livraria_springboot_tdd.service;

import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AutorServiceTest {

    @Autowired
    private AutorService autorService;

    @Test
    void naoDevePermitirNomeDuplicado() {
        Autor autor1 = new Autor();
        autor1.setNome("Machado de Assis");
        autor1.setPaisOrigem("Brasil");
        autorService.salvar(autor1);

        Autor autor2 = new Autor();
        autor2.setNome("Machado de Assis");
        autor2.setPaisOrigem("Brasil");

        assertThrows(RuntimeException.class, () -> autorService.salvar(autor2));
    }
}
