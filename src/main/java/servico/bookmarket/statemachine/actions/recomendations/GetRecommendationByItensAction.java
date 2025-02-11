package servico.bookmarket.statemachine.actions.recomendations;

import servico.bookmarket.statemachine.actions.reviews.GetUniqueReviewsAction;
import servico.bookstore.Bookstore;
import servico.bookstore.utils.MahoutUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import dominio.Review;
import servico.bookmarket.statemachine.actions.BookstoreAction;

/**
 * Ação que gera recomendações de livros com base em itens previamente
 * avaliados.
 * Utiliza um sistema de recomendação baseado em itens, considerando as
 * avaliações feitas pelo usuário.
 */
public class GetRecommendationByItensAction extends BookstoreAction {
    private static final long serialVersionUID = 7439962163328790677L;

    int customerId;
    int numOfRecommendations;

    /**
     * Construtor da ação de recomendação baseada em itens.
     *
     * @param customerId           Identificador único do usuário para o qual as
     *                             recomendações serão geradas.
     * @param numOfRecommendations Número máximo de livros recomendados.
     */
    public GetRecommendationByItensAction(int customerId, int numOfRecommendations) {
        this.customerId = customerId;
        this.numOfRecommendations = numOfRecommendations;
    }

    /**
     * Executa a recomendação de livros com base na similaridade entre itens
     * avaliados pelo usuário.
     *
     * @param bookstore Stream da livraria contendo os dados necessários para gerar
     *                  as recomendações.
     * @return Uma lista de livros recomendados baseada na similaridade entre os
     *         itens previamente avaliados.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        List<Review> reviews = (List<Review>) new GetUniqueReviewsAction().executeOn(bookstore);
        List<RecommendedItem> recommendedItems = MahoutUtils.recommendItemBased(
                reviews,
                this.customerId,
                this.numOfRecommendations);

        List<Object> recommendedBooks = recommendedItems.stream()
                .map(i -> Bookstore.getBook((int) i.getItemID()))
                .collect(Collectors.toList());

        return recommendedBooks;
    }
}