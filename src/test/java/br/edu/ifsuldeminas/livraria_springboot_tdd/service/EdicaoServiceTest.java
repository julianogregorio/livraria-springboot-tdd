package br.edu.ifsuldeminas.livraria_springboot_tdd.service;

import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Edicao;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class EdicaoServiceTest {

    @Autowired
    private EdicaoService edicaoService;

    @Test
    void naoDevePermitirEstoqueNegativo() {
        Livro livro = new Livro();
        livro.setTitulo("Memórias Póstumas de Brás Cubas");
        livro.setAnoPublicacao(1881);
        livro.setLingua("Português");

        Edicao edicao = new Edicao();
        edicao.setAnoEdicao(2020);
        edicao.setPreco(49.90);
        edicao.setNumPaginas(300);
        edicao.setQuantEstoque(-5); // estoque inválido
        edicao.setLivro(livro);

        assertThrows(RuntimeException.class, () -> edicaoService.salvar(edicao));
    }
}
