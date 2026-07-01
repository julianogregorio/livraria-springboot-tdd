package br.edu.ifsuldeminas.livraria_springboot_tdd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Edicao;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Livro;
import br.edu.ifsuldeminas.livraria_springboot_tdd.repository.EdicaoRepository;
import br.edu.ifsuldeminas.livraria_springboot_tdd.repository.LivroRepository;

@Service
public class EdicaoService {

    @Autowired
    private EdicaoRepository edicaoRepository;

    @Autowired
    private LivroRepository livroRepository;

    public Edicao salvar(Edicao edicao) {
        if (edicao.getQuantEstoque() < 0) {
            throw new RuntimeException("Estoque não pode ser negativo!");
        }
        if (edicao.getPreco() < 0) {
            throw new RuntimeException("Preço não pode ser negativo!");
        }

        Livro livro = livroRepository.findById(edicao.getLivro().getId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado!"));
        edicao.setLivro(livro);

        return edicaoRepository.save(edicao);
    }

    public List<Edicao> listarTodos() {
        return edicaoRepository.findAll();
    }

    public Edicao buscarPorId(Integer id) {
        return edicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edição não encontrada!"));
    }

    public Edicao vender(Integer id, int quantidade) {
        Edicao edicao = buscarPorId(id);

        if (quantidade <= 0) {
            throw new RuntimeException("Quantidade inválida para venda!");
        }
        if (quantidade > edicao.getQuantEstoque()) {
            throw new RuntimeException("Quantidade maior que o estoque disponível!");
        }

        edicao.setQuantEstoque(edicao.getQuantEstoque() - quantidade);
        return edicaoRepository.save(edicao);
    }

    // 🔹 Atualizar edição
    public Edicao atualizar(Integer id, Edicao edicaoAtualizada) {
        Edicao edicao = buscarPorId(id);

        edicao.setAnoEdicao(edicaoAtualizada.getAnoEdicao());
        edicao.setPreco(edicaoAtualizada.getPreco());
        edicao.setNumPaginas(edicaoAtualizada.getNumPaginas());
        edicao.setQuantEstoque(edicaoAtualizada.getQuantEstoque());

        if (edicaoAtualizada.getLivro() != null) {
            Livro livro = livroRepository.findById(edicaoAtualizada.getLivro().getId())
                    .orElseThrow(() -> new RuntimeException("Livro não encontrado!"));
            edicao.setLivro(livro);
        }

        return edicaoRepository.save(edicao);
    }

    // 🔹 Deletar edição
    public void deletar(Integer id) {
        Edicao edicao = buscarPorId(id);
        edicaoRepository.delete(edicao);
    }
}
