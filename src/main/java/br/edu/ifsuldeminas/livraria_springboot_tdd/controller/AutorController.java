package br.edu.ifsuldeminas.livraria_springboot_tdd.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Autor;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Livro;
import br.edu.ifsuldeminas.livraria_springboot_tdd.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @PostMapping
    public Autor criar(@RequestBody Autor autor) {
        try {
            return autorService.salvar(autor);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("já existente")) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public List<Autor> listar() {
        return autorService.listarTodos();
    }

    @GetMapping("/{id}")
    public Autor buscar(@PathVariable Integer id) {
        try {
            return autorService.buscarPorId(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}/livros")
    public List<Livro> listarLivros(@PathVariable Integer id) {
        try {
            return autorService.listarLivrosDoAutor(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // 🔹 Atualizar autor
    @PutMapping("/{id}")
    public Autor atualizar(@PathVariable Integer id, @RequestBody Autor autorAtualizado) {
        try {
            return autorService.atualizar(id, autorAtualizado);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // 🔹 Deletar autor
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        try {
            autorService.deletar(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
