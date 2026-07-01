package br.edu.ifsuldeminas.livraria_springboot_tdd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "edicoes")
public class Edicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer anoEdicao;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private Integer numPaginas;

    @Column(nullable = false)
    private Integer quantEstoque;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    @JsonBackReference   
    private Livro livro;

    public Edicao() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAnoEdicao() { return anoEdicao; }
    public void setAnoEdicao(Integer anoEdicao) { this.anoEdicao = anoEdicao; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public Integer getNumPaginas() { return numPaginas; }
    public void setNumPaginas(Integer numPaginas) { this.numPaginas = numPaginas; }

    public Integer getQuantEstoque() { return quantEstoque; }
    public void setQuantEstoque(Integer quantEstoque) { this.quantEstoque = quantEstoque; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
}
