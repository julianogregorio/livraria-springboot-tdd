package br.edu.ifsuldeminas.livraria_springboot_tdd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String titulo;

    @Column(nullable = false)
    private Integer anoPublicacao;

    @Column(nullable = false, length = 45)
    private String lingua;

    // Relacionamento com Edições (1:N)
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    @JsonManagedReference   // aqui sim funciona (1:N)
    private List<Edicao> edicoes;

    // Relacionamento com Autores (N:N via Lista)
    @ManyToMany
    @JoinTable(
        name = "lista",
        joinColumns = @JoinColumn(name = "livro_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    @JsonIgnore   // evita loop na serialização
    private List<Autor> autores;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(Integer anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public String getLingua() { return lingua; }
    public void setLingua(String lingua) { this.lingua = lingua; }

    public List<Edicao> getEdicoes() { return edicoes; }
    public void setEdicoes(List<Edicao> edicoes) { this.edicoes = edicoes; }

    public List<Autor> getAutores() { return autores; }
    public void setAutores(List<Autor> autores) { this.autores = autores; }
}
