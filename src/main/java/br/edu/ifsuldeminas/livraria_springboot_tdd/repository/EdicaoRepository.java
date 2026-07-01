package br.edu.ifsuldeminas.livraria_springboot_tdd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Edicao;

public interface EdicaoRepository extends JpaRepository<Edicao, Integer> {
    // Aqui não precisamos de métodos extras por enquanto,
    // mas se quiser validar duplicidade de ano ou algo específico,
    // pode adicionar depois, por exemplo:
    // Optional<Edicao> findByAnoEdicao(Integer anoEdicao);
}
