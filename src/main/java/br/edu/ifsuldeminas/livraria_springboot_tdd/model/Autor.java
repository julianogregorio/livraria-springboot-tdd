package br.edu.ifsuldeminas.livraria_springboot_tdd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 45)
    private String paisOrigem;

    @ManyToMany(mappedBy = "autores")
    @JsonIgnore   // evita loop e erro de referência
    private List<Livro> livros;

    public Autor() {}

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPaisOrigem() { return paisOrigem; }
    public void setPaisOrigem(String paisOrigem) { this.paisOrigem = paisOrigem; }

    public List<Livro> getLivros() { return livros; }
    public void setLivros(List<Livro> livros) { this.livros = livros; }
}
