package br.edu.ifsuldeminas.livraria_springboot_tdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import br.edu.ifsuldeminas.livraria_springboot_tdd.model.Edicao;
import br.edu.ifsuldeminas.livraria_springboot_tdd.repository.EdicaoRepository;

@Service
public class EdicaoService {

    @Autowired
    private EdicaoRepository edicaoRepository;

    public Edicao salvar(Edicao edicao) {
        if (edicao.getQuantEstoque() < 0) {
            throw new RuntimeException("Estoque não pode ser negativo!");
        }
        if (edicao.getPreco() == null || edicao.getPreco() <= 0) {
            throw new RuntimeException("Preço deve ser positivo!");
        }
        return edicaoRepository.save(edicao);
    }

    public List<Edicao> listarTodas() {
        return edicaoRepository.findAll();
    }

    public Edicao buscarPorId(Integer id) {
        return edicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edição não encontrada!"));
    }

    // 🔹 Lógica de venda (estoque decrementa)
    public Edicao vender(Integer id, int quantidade) {
        Edicao edicao = buscarPorId(id);
        if (quantidade <= 0) {
            throw new RuntimeException("Quantidade de venda deve ser positiva!");
        }
        if (edicao.getQuantEstoque() < quantidade) {
            throw new RuntimeException("Estoque insuficiente!");
        }
        edicao.setQuantEstoque(edicao.getQuantEstoque() - quantidade);
        return edicaoRepository.save(edicao);
    }
}
