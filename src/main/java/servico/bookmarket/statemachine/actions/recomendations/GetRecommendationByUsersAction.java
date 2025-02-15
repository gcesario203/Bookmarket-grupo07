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
 * Ação que gera recomendações de livros com base na similaridade entre
 * usuários.
 * Utiliza um sistema de recomendação baseado em usuários, considerando as
 * avaliações feitas.
 */
public class GetRecommendationByUsersAction extends BookstoreAction {
    private static final long serialVersionUID = 2798556125861360648L;

    int customerId;
    int numOfRecommendations;

    /**
     * Construtor da ação de recomendação baseada em usuários.
     *
     * @param customerId           Identificador único do usuário para o qual as
     *                             recomendações serão geradas.
     * @param numOfRecommendations Número máximo de livros recomendados.
     */
    public GetRecommendationByUsersAction(int customerId, int numOfRecommendations) {
        this.customerId = customerId;
        this.numOfRecommendations = numOfRecommendations;
    }

    /**
     * Executa a recomendação de livros com base nas avaliações de usuários
     * semelhantes.
     *
     * @param bookstore Stream da livraria contendo os dados necessários para gerar
     *                  as recomendações.
     * @return Uma lista de livros recomendados baseada na similaridade entre
     *         usuários.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        List<Review> reviews = (List<Review>) new GetUniqueReviewsAction().executeOn(bookstore);
        List<RecommendedItem> recommendedItems = MahoutUtils.recommendUserBased(
                reviews,
                this.customerId,
                this.numOfRecommendations);

        List<Object> recommendedBooks = recommendedItems.stream()
                .map(i -> Bookstore.getBook((int) i.getItemID()))
                .collect(Collectors.toList());

        return recommendedBooks;
    }
}
