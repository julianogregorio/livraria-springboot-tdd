package br.edu.ifsuldeminas.livraria_springboot_tdd.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Autor;
import br.edu.ifsuldeminas.livraria_springboot_tdd.service.AutorService;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @PostMapping
    public Autor criar(@RequestBody Autor autor) {
        return autorService.salvar(autor);
    }

    @GetMapping
    public List<Autor> listar() {
        return autorService.listarTodos();
    }

    @GetMapping("/{id}")
    public Autor buscar(@PathVariable Integer id) {
        return autorService.buscarPorId(id);
    }
}
