package br.edu.ifsuldeminas.livraria_springboot_tdd.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Edicao;
import br.edu.ifsuldeminas.livraria_springboot_tdd.service.EdicaoService;

@RestController
@RequestMapping("/edicoes")
public class EdicaoController {

    @Autowired
    private EdicaoService edicaoService;

    @PostMapping
    public Edicao criar(@RequestBody Edicao edicao) {
        return edicaoService.salvar(edicao);
    }

    @GetMapping
    public List<Edicao> listar() {
        return edicaoService.listarTodas();
    }

    @GetMapping("/{id}")
    public Edicao buscar(@PathVariable Integer id) {
        return edicaoService.buscarPorId(id);
    }
}
