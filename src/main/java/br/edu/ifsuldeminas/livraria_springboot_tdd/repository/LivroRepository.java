package br.edu.ifsuldeminas.livraria_springboot_tdd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
    boolean existsByTitulo(String titulo);
}
