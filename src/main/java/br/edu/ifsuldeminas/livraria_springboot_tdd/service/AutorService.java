package br.edu.ifsuldeminas.livraria_springboot_tdd.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Autor;
import br.edu.ifsuldeminas.livraria_springboot_tdd.repository.AutorRepository;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public Autor salvar(Autor autor) {
        if (autorRepository.existsByNome(autor.getNome())) {
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
}
