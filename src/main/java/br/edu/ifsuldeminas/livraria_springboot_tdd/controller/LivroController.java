package br.edu.ifsuldeminas.livraria_springboot_tdd.controller;

import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Livro;
import br.edu.ifsuldeminas.livraria_springboot_tdd.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    public Livro criar(@RequestBody Livro livro) {
        try {
            return livroService.salvar(livro);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Título já existente")) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
            if (e.getMessage().contains("pelo menos um autor")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public List<Livro> listarTodos() {
        return livroService.listarTodos();
    }

    @GetMapping("/{id}")
    public Livro buscarPorId(@PathVariable Integer id) {
        try {
            return livroService.buscarPorId(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}/autores")
    public List<?> listarAutoresDeLivro(@PathVariable Integer id) {
        try {
            return livroService.listarAutoresDeLivro(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
