# BookMarket - INF329 - Grupo 07

### Douglas Sermarini (ex147730)
### Gabriel Cesário (ex188751)
### Joseíto Júnior (ex188800)
### Stephenson Oliveira (ex189571)
### Vitor Gomes (ex188807)

Este repositório contém o código-fonte e documentação do projeto BookMarket, um sistema de e-commerce para livros usados desenvolvido como parte da disciplina INF329 – Prática de Engenharia de Software, ministrada pelo Prof. Luiz Eduardo Busato na UNICAMP.

## Objetivos

1. **Projeto Orientado a Objetos e Implementação em Java**: Construir e evoluir um sistema baseado nos conceitos de orientação a objetos.
2. **Prática de Processo Ágil Minimalista**: Implementar o projeto seguindo um processo ágil com ferramentas como controle de versão, testes automatizados e kanban.

## Principais Funcionalidades

- **Documentação da Arquitetura**: Uso de comentários Javadoc para descrever a estrutura do sistema.
- **Testes Unitários**: Implementação de testes para os métodos públicos das classes principais.
- **Algoritmo de Bestsellers**: Geração de uma lista com os `n` livros mais vendidos.
- **Recomendações Personalizadas**:
  - Sugestões de livros baseadas no perfil de consumo dos clientes.
  - Avaliação de livros com notas de 0 (péssimo) a 5 (ótimo).
  - Sugestões de livros para assinantes com base no menor preço disponível.

## Tecnologias e Bibliotecas

- **Linguagem**: Java
- **Biblioteca para Recomendação**: Apache Mahout
  - Pearson Correlation Similarity
  - User-Based Recommendations

### Dependência Maven
```xml
<dependency>
    <groupId>org.apache.mahout</groupId>
    <artifactId>mahout-mr</artifactId>
    <version>0.13.0</version>
</dependency>
```

## Estrutura do Projeto

1. **Algoritmos de Recomendação**: Baseados em filtragem colaborativa utilizando similaridade de avaliações entre clientes.
2. **Modelagem de Dados**:
   - Uso de classes do Mahout, como `FastByIDMap`, `GenericPreference`, e `DataModel`.
   - População de dados via o método `populateEvaluation`.
3. **Testes Unitários**:
   - Cobertura das histórias de usuário relacionadas a bestsellers e recomendações.

## Histórias de Usuário

- **Bestsellers**: "Como visitante, quero que o BookMarket liste os `n` livros mais vendidos no mercado."
- **Recomendações**:
  - "Eu, como cliente, quero receber cinco sugestões de livros do meu interesse com o seu respectivo valor médio."
  - "Como cliente, quero avaliar um livro com notas de 0 a 5."
  - "Eu, como assinante, quero receber cinco sugestões de livros do meu interesse pelo menor valor disponível no BookMarket."

## Referências

- [Isinkaye, Folajimi e Ojokoh (2015). "Recommendation systems: Principles, methods and evaluation". *Egyptian Informatics Journal*, 16(3):261–273.](https://doi.org/10.1016/j.eij.2015.06.005)

---
**Professor Responsável:** Luiz Eduardo Busato  
**Disciplina:** INF329 – Prática de Engenharia de Software
