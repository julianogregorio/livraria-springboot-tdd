package br.edu.ifsuldeminas.livraria_springboot_tdd;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Aplicação Spring Boot está rodando!";
    }
}
