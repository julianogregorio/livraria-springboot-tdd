package br.edu.ifsuldeminas.livraria_springboot_tdd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
    Optional<Livro> findByTitulo(String titulo);
}
