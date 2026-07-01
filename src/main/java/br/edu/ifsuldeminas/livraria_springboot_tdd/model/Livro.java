package br.edu.ifsuldeminas.livraria_springboot_tdd.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    @JsonManagedReference   
    private List<Edicao> edicoes;

    @ManyToMany
    @JoinTable(
        name = "livro_autor",
        joinColumns = @JoinColumn(name = "livro_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

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
