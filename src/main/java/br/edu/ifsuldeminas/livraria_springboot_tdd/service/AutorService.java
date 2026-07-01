package br.edu.ifsuldeminas.livraria_springboot_tdd.service;

import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Autor;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Livro;
import br.edu.ifsuldeminas.livraria_springboot_tdd.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public Autor salvar(Autor autor) {
        if (autorRepository.findByNome(autor.getNome()).isPresent()) {
            throw new RuntimeException("Autor já existente!");
        }
        return autorRepository.save(autor);
    }

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Autor buscarPorId(Integer id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado!"));
    }

    public List<Livro> listarLivrosDoAutor(Integer id) {
        Autor autor = buscarPorId(id);
        return autor.getLivros();
    }

    // 🔹 Atualizar autor
    public Autor atualizar(Integer id, Autor autorAtualizado) {
        Autor autor = buscarPorId(id);
        autor.setNome(autorAtualizado.getNome());
        autor.setPaisOrigem(autorAtualizado.getPaisOrigem());
        return autorRepository.save(autor);
    }

    // 🔹 Deletar autor
    public void deletar(Integer id) {
        Autor autor = buscarPorId(id);
        autorRepository.delete(autor);
    }
}
