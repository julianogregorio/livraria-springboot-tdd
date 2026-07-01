package br.edu.ifsuldeminas.livraria_springboot_tdd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Edicao;

public interface EdicaoRepository extends JpaRepository<Edicao, Integer> {
    
}
