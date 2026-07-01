package br.edu.ifsuldeminas.livraria_springboot_tdd.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Livro;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Autor;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Edicao;
import br.edu.ifsuldeminas.livraria_springboot_tdd.service.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    public Livro criar(@RequestBody Livro livro) {
        return livroService.salvar(livro);
    }

    @GetMapping
    public List<Livro> listar() {
        return livroService.listarTodos();
    }

    @GetMapping("/{id}")
    public Livro buscar(@PathVariable Integer id) {
        return livroService.buscarPorId(id);
    }

    // 🔹 Listar todos os autores de um livro
    @GetMapping("/{id}/autores")
    public List<Autor> listarAutores(@PathVariable Integer id) {
        return livroService.listarAutoresDoLivro(id);
    }

    // 🔹 Listar todas as edições de um livro
    @GetMapping("/{id}/edicoes")
    public List<Edicao> listarEdicoes(@PathVariable Integer id) {
        return livroService.listarEdicoesDoLivro(id);
    }
}
