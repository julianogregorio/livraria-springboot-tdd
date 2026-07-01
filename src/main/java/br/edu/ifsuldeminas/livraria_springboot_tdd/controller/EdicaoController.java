package br.edu.ifsuldeminas.livraria_springboot_tdd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Edicao;
import br.edu.ifsuldeminas.livraria_springboot_tdd.service.EdicaoService;

@RestController
@RequestMapping("/edicoes")
public class EdicaoController {

    @Autowired
    private EdicaoService edicaoService;

    @PostMapping
    public Edicao criar(@RequestBody Edicao edicao) {
        try {
            return edicaoService.salvar(edicao);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public List<Edicao> listar() {
        return edicaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Edicao buscar(@PathVariable Integer id) {
        try {
            return edicaoService.buscarPorId(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // 🔹 Corrigido: quantidade como RequestParam
    @PostMapping("/{id}/vender")
    public Edicao vender(@PathVariable Integer id, @RequestParam int quantidade) {
        try {
            return edicaoService.vender(id, quantidade);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // 🔹 Atualizar edição
    @PutMapping("/{id}")
    public Edicao atualizar(@PathVariable Integer id, @RequestBody Edicao edicaoAtualizada) {
        try {
            return edicaoService.atualizar(id, edicaoAtualizada);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // 🔹 Deletar edição
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        try {
            edicaoService.deletar(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
