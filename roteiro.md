## 1° Caso de uso
```
Como cliente quero avaliar um livro com notas que variam entre 0 (péssimo) e 5 (ótimo)
```

#### Testes do bookmarket
- [shouldCreateAReviewForABookstore](src/test/java/servico/BookmarketTest.java#:287)

#### Testes do bookstore
- [shouldHasPopulatedReviews](src/test/java/servico/BookstoreTest.java#:431)


#### Testes de domínio
- [ReviewTest](src/test/java/dominio/ReviewTest.java#:18)

#### Gerador de Ids
- [IdGeneratorTest](src/test/java/servico/IdGeneratorTest.java#:11)


## 2° Caso de uso
```
Como visitante, quero que o Bookmarket liste os n livros mais vendidos no mercado, onde n pode variar entre 1 e 100.
```

#### Testes do bookmarket
- [shouldGetTheFiveBestSellers](src/test/java/servico/BookmarketTest.java#:913)
- [shouldGetRandomBestSellers](src/test/java/servico/BookmarketTest.java#:833)
- [shouldGetTheBestSeller](src/test/java/servico/BookmarketTest.java#:855)


## 3° Caso de uso
```
Eu, como cliente, quero receber cinco sugestões de livros do meu interesse com o seu respectivo valor médio;
```
#### Testes do bookmarket
- [shouldGetRecommendationForDefaultCustomer](src/test/java/servico/BookmarketTest.java#:1273)
- [shouldReturnValidRecommendationsFromSyntheticDataset](src/test/java/servico/BookmarketTest.java#:1013)
- [shouldGetFiveRecommendationsWithNonSyntheticData](src/test/java/servico/BookmarketTest.java#:1177)
  
#### Testes do bookstore
- [shouldReturnValidItemsRecommendationsFromSyntheticDataset](src/test/java/servico/BookstoreTest.java#:817)
- [shouldReturnValidRecommendationsFromSyntheticDataset](src/test/java/servico/BookstoreTest.java#:746)
- [shouldGetTheAverageCostFromABook](src/test/java/servico/BookstoreTest.java#:973)
- [shouldUpdateRelatedBooks](src/test/java/servico/BookstoreTest.java#:614)

#### Domínio
- [Mahout](servico/bookstore/utils/MahoutUtils.java)
- [Type Enum](dominio/Customer.java#:346)

## 4° Caso de uso
```
Eu, como assinante, quero receber cinco sugestões de livros do meu interesse pelo menor valor disponível no Bookmarket.
```
#### Testes do bookmarket
- [shouldGetRecommendationForSubscriberCustomer](src/test/java/servico/BookmarketTest.java#:1193)
- [shouldGetTheMinimumBookValueCost](src/test/java/servico/BookmarketTest.java#:956)
	