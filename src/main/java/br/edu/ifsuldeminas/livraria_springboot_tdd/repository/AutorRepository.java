package br.edu.ifsuldeminas.livraria_springboot_tdd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Integer> {
    boolean existsByNome(String nome);
}
