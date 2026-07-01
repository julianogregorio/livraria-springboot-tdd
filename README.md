# 📚 Livraria Spring Boot TDD

Projeto desenvolvido como parte da disciplina **Programação Orientada a Objetos**, no curso de **Bacharelado em Sistemas de Informação** do **IFSULDEMINAS - Campus Machado**.  
Este sistema implementa uma livraria digital com operações de CRUD para Autor, Livro e Edição, aplicando regras de negócio específicas e utilizando a prática de **Test-Driven Development (TDD)**.

---

## 👨‍💻 Desenvolvedores
- Juliano Gregório  
- Viviane Tamíris  

---

## 🏛️ Arquitetura
O projeto segue o padrão **MVC (Model-View-Controller)**:
- **Controllers**: expõem os endpoints REST da API.  
- **Services**: concentram as regras de negócio.  
- **Repositories**: fazem a comunicação com o banco de dados via Spring Data JPA.  

### Padrões de Projeto aplicados
- **Singleton**: aplicado implicitamente pelo Spring Boot na gestão de beans.  
- **Repository**: abstração da persistência com Spring Data JPA.  
- **Strategy**: aplicável em regras variáveis, como cálculo de descontos.  
- **Builder**: útil para construção de objetos complexos como Livro.  
- **Factory Method**: poderia ser usado para criação de diferentes tipos de livros (físico, digital).  

---

## 📑 Classes e Atributos

### Autor
- `id` (Long)  
- `nome` (String)  
- `email` (String)  

### Livro
- `id` (Long)  
- `titulo` (String)  
- `preco` (BigDecimal)  
- `autor` (Autor)  

### Edição
- `id` (Long)  
- `ano` (Integer)  
- `estoque` (Integer)  
- `livro` (Livro)  

---

## ⚖️ Regras de Negócio
- Não permitir duplicidade de autor ou livro.  
- Impedir que o estoque seja negativo.  
- Impedir que o preço seja negativo.  
- Validar que a venda de exemplares só ocorra se houver quantidade suficiente em estoque.  
- Garantir que cada edição esteja vinculada a um livro existente.  
- Garantir que cada livro esteja vinculado a um autor existente.  
- Retornar erro adequado (HTTP 400) em casos de violação das regras.  

---

## 🌐 Endpoints da API

### Autor
- `POST /autores` → cria um novo autor.  
- `GET /autores` → lista todos os autores.  
- `GET /autores/{id}` → busca autor por ID.  
- `PUT /autores/{id}` → atualiza dados de um autor.  
- `DELETE /autores/{id}` → remove um autor.  

### Livro
- `POST /livros` → cria um novo livro.  
- `GET /livros` → lista todos os livros.  
- `GET /livros/{id}` → busca livro por ID.  
- `PUT /livros/{id}` → atualiza dados de um livro.  
- `DELETE /livros/{id}` → remove um livro.  

### Edição
- `POST /edicoes` → cria uma nova edição.  
- `GET /edicoes` → lista todas as edições.  
- `GET /edicoes/{id}` → busca edição por ID.  
- `PUT /edicoes/{id}` → atualiza dados de uma edição.  
- `DELETE /edicoes/{id}` → remove uma edição.  
- `PUT /edicoes/vender?id={id}&quantidade={qtd}` → registra venda de exemplares (com validação de estoque).  

---

## 🧪 Testes
O projeto foi desenvolvido com **TDD**, utilizando o padrão **AAA (Arrange-Act-Assert)**.  
Todos os testes foram executados com sucesso, garantindo que as regras de negócio foram corretamente implementadas.  

Exemplo de teste:
```java
@Test
void naoDevePermitirVendaAcimaDoEstoque() {
    Edicao edicao = new Edicao();
    edicao.setEstoque(5);
    edicaoRepository.save(edicao);

    assertThrows(IllegalArgumentException.class, () -> {
        edicaoService.vender(edicao.getId(), 10);
    });
}
