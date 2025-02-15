# BookMarket - INF329 - Grupo 07 ![Coverage](https://gcesario203.github.io/Bookmarket-grupo07/coverage-badge.svg)

#### Douglas Sermarini (ex147730)
#### Gabriel Cesário (ex188751)
#### Joseíto Júnior (ex188800)
#### Stephenson Oliveira (ex189571)
#### Vitor Gomes (ex188807)

Este repositório contém o código-fonte e documentação do projeto BookMarket, um sistema de e-commerce para livros usados desenvolvido como parte da disciplina INF329 – Prática de Engenharia de Software, ministrada pelo Prof. Luiz Eduardo Busato na UNICAMP.

## Objetivos

1. **Projeto Orientado a Objetos e Implementação em Java**: Construir e evoluir um sistema baseado nos conceitos de orientação a objetos.
2. **Implementação de um sistema de recomendação com machine learning**: Implementar um sistema de recomendação utilizando a biblioteca mahout baseado nas preferencias dos usúarios.
3. **Prática de Processo Ágil Minimalista**: Implementar o projeto seguindo um processo ágil com ferramentas como controle de versão, testes automatizados e kanban.

## Principais Funcionalidades

- **Documentação da Arquitetura**: Uso de comentários Javadoc para descrever a estrutura do sistema.
- **Testes Unitários**: Implementação de testes para os métodos públicos das classes principais.
- **Algoritmo de Bestsellers**: Geração de uma lista com os `n` livros mais vendidos.
- **Sistema de avaliação**: Avaliação de livros com notas de 0 (péssimo) a 5 (ótimo).
- **Recomendações Personalizadas**:
  - Sugestões de livros baseadas no perfil de consumo dos clientes.
  - Sugestões de livros para assinantes com base no menor preço disponível.

## Tecnologias e Bibliotecas

- **Linguagem**: [Java 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html) ![Java](https://img.shields.io/badge/Java-11-%23ED8B00?logo=openjdk&logoColor=white)
- **Biblioteca para Recomendação**: [Apache Mahout 0.13.0](https://mahout.apache.org/docs/0.13.0/) ![Apache Mahout](https://img.shields.io/badge/Apache%20Mahout-0.13.0-%23007396?logo=apache&logoColor=white)
  - Pearson Correlation Similarity
  - User-Based Recommendations
- **Testes Unitários**: [JUnit 4.10](https://junit.org/) ![JUnit](https://img.shields.io/badge/JUnit-4.10-%2325A162?logo=junit5&logoColor=white)
- **Cobertura de Código**: [JaCoCo 0.8.10](https://www.eclemma.org/jacoco/) ![JaCoCo](https://img.shields.io/badge/JaCoCo-0.8.10-%23009688?logo=codecov&logoColor=white)
- **Gerenciador de dependências** : [Maven](https://maven.apache.org/) ![Maven](https://img.shields.io/badge/Maven-%23C71A36?logo=apache-maven&logoColor=white)

### Dependências Maven
```xml
<dependency>
    <groupId>org.apache.mahout</groupId>
    <artifactId>mahout-mr</artifactId>
    <version>0.13.0</version>
</dependency>
<details> <summary>Configuração completa do JaCoCo</summary>
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
</details> 
```


## Histórias de Usuário

- **Bestsellers**: "Como visitante, quero que o BookMarket liste os `n` livros mais vendidos no mercado."
- **Recomendações**:
  - "Eu, como cliente, quero receber cinco sugestões de livros do meu interesse com o seu respectivo valor médio."
  - "Como cliente, quero avaliar um livro com notas de 0 a 5."
  - "Eu, como assinante, quero receber cinco sugestões de livros do meu interesse pelo menor valor disponível no BookMarket."

## Referências

- [Isinkaye, Folajimi e Ojokoh (2015). "Recommendation systems: Principles, methods and evaluation". *Egyptian Informatics Journal*, 16(3):261–273.](https://doi.org/10.1016/j.eij.2015.06.005)
- [@book{36682,title	= {Mahout in Action},author	= {Robin Anil and Sean Owen and Ted Dunning and Ellen Friedman},year	= {2010},URL	= {http://manning.com/owen/},booktitle	= {Mahout in Action},pages	= {350},address	= {Manning Publications Co. Sound View Ct. #3B Greenwich, CT 06830}}](https://research.google/pubs/mahout-in-action/)

## 📈 Relatório de Contribuições (GitInspector)
[🔍 Visualizar Relatório GitInspector](https://gcesario203.github.io/Bookmarket-grupo07/gitinspector-report.html)


---
**Professor Responsável:** Luiz Eduardo Busato  
**Disciplina:** INF329 – Prática de Engenharia de Software
