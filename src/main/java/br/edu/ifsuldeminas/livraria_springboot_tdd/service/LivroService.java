package br.edu.ifsuldeminas.livraria_springboot_tdd.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Livro;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Autor;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Edicao;
import br.edu.ifsuldeminas.livraria_springboot_tdd.repository.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        if (livroRepository.existsByTitulo(livro.getTitulo())) {
            throw new RuntimeException("Título já existente!");
        }
        if (livro.getAutores() == null || livro.getAutores().isEmpty()) {
            throw new RuntimeException("Livro deve ter pelo menos um autor!");
        }
        return livroRepository.save(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Integer id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado!"));
    }

    // 🔹 Listar todos os autores de um livro
    public List<Autor> listarAutoresDoLivro(Integer livroId) {
        Livro livro = buscarPorId(livroId);
        return livro.getAutores();
    }

    // 🔹 Listar todas as edições de um livro
    public List<Edicao> listarEdicoesDoLivro(Integer livroId) {
        Livro livro = buscarPorId(livroId);
        return livro.getEdicoes();
    }
}
