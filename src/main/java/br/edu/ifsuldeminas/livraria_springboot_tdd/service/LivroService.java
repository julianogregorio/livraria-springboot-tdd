package br.edu.ifsuldeminas.livraria_springboot_tdd.service;

import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Autor;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Edicao;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Livro;
import br.edu.ifsuldeminas.livraria_springboot_tdd.repository.AutorRepository;
import br.edu.ifsuldeminas.livraria_springboot_tdd.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public Livro salvar(Livro livro) {
        if (livroRepository.findByTitulo(livro.getTitulo()).isPresent()) {
            throw new RuntimeException("Título já existente!");
        }
        if (livro.getAutores() == null || livro.getAutores().isEmpty()) {
            throw new RuntimeException("Livro deve ter pelo menos um autor!");
        }

        List<Autor> autoresValidados = livro.getAutores().stream()
                .map(a -> autorRepository.findById(a.getId())
                        .orElseThrow(() -> new RuntimeException("Autor não encontrado!")))
                .toList();

        livro.setAutores(autoresValidados);
        return livroRepository.save(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Integer id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado!"));
    }

    public List<Autor> listarAutoresDeLivro(Integer id) {
        Livro livro = buscarPorId(id);
        return livro.getAutores();
    }

    public List<Edicao> listarEdicoesDeLivro(Integer id) {
        Livro livro = buscarPorId(id);
        return livro.getEdicoes();
    }

    // 🔹 Atualizar livro
    public Livro atualizar(Integer id, Livro livroAtualizado) {
        Livro livro = buscarPorId(id);

        // Atualiza campos básicos
        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
        livro.setLingua(livroAtualizado.getLingua());

        // Atualiza autores (validando IDs)
        if (livroAtualizado.getAutores() != null && !livroAtualizado.getAutores().isEmpty()) {
            List<Autor> autoresValidados = livroAtualizado.getAutores().stream()
                    .map(a -> autorRepository.findById(a.getId())
                            .orElseThrow(() -> new RuntimeException("Autor não encontrado!")))
                    .toList();
            livro.setAutores(autoresValidados);
        }

        return livroRepository.save(livro);
    }

    // 🔹 Deletar livro
    public void deletar(Integer id) {
        Livro livro = buscarPorId(id);
        livroRepository.delete(livro);
    }
}
