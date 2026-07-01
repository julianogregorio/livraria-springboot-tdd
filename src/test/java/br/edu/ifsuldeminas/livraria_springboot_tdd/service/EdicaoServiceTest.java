package br.edu.ifsuldeminas.livraria_springboot_tdd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Edicao;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Livro;
import br.edu.ifsuldeminas.livraria_springboot_tdd.repository.LivroRepository;

@SpringBootTest
class EdicaoServiceTest {

    @Autowired
    private EdicaoService edicaoService;

    @Autowired
    private LivroRepository livroRepository;

    private Livro criarLivroBasePersistido(String titulo) {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAnoPublicacao(1881);
        livro.setLingua("Português");
        return livroRepository.save(livro);
    }

    @Test
    void naoDevePermitirEstoqueNegativo() {
        Edicao edicao = new Edicao();
        edicao.setAnoEdicao(2020);
        edicao.setPreco(49.90);
        edicao.setNumPaginas(300);
        edicao.setQuantEstoque(-5);
        edicao.setLivro(criarLivroBasePersistido("Livro Teste Estoque"));

        assertThrows(RuntimeException.class, () -> edicaoService.salvar(edicao));
    }

    @Test
    void naoDevePermitirPrecoNegativo() {
        Edicao edicao = new Edicao();
        edicao.setAnoEdicao(2020);
        edicao.setPreco(-10.0);
        edicao.setNumPaginas(200);
        edicao.setQuantEstoque(5);
        edicao.setLivro(criarLivroBasePersistido("Livro Teste Preço"));

        assertThrows(RuntimeException.class, () -> edicaoService.salvar(edicao));
    }

    @Test
    void naoDevePermitirVendaMaiorQueEstoque() {
        Edicao edicao = new Edicao();
        edicao.setAnoEdicao(2020);
        edicao.setPreco(39.90);
        edicao.setNumPaginas(150);
        edicao.setQuantEstoque(3);
        edicao.setLivro(criarLivroBasePersistido("Livro Teste Venda Estoque"));

        Edicao salvo = edicaoService.salvar(edicao);

        assertThrows(RuntimeException.class, () -> edicaoService.vender(salvo.getId(), 5));
    }

    @Test
    void naoDevePermitirVendaComQuantidadeZeroOuNegativa() {
        Edicao edicao = new Edicao();
        edicao.setAnoEdicao(2020);
        edicao.setPreco(29.90);
        edicao.setNumPaginas(100);
        edicao.setQuantEstoque(10);
        edicao.setLivro(criarLivroBasePersistido("Livro Teste Venda Zero"));

        Edicao salvo = edicaoService.salvar(edicao);

        assertThrows(RuntimeException.class, () -> edicaoService.vender(salvo.getId(), 0));
        assertThrows(RuntimeException.class, () -> edicaoService.vender(salvo.getId(), -2));
    }

    @Test
    void deveRealizarVendaComSucesso() {
        Edicao edicao = new Edicao();
        edicao.setAnoEdicao(2020);
        edicao.setPreco(59.90);
        edicao.setNumPaginas(250);
        edicao.setQuantEstoque(10);
        edicao.setLivro(criarLivroBasePersistido("Livro Teste Venda Sucesso"));

        Edicao salvo = edicaoService.salvar(edicao);

        Edicao atualizado = edicaoService.vender(salvo.getId(), 3);

        assertEquals(7, atualizado.getQuantEstoque());
    }
}
