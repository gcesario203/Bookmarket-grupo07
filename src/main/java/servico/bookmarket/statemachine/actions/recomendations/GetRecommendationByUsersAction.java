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
 * Book recommendationns actions based on customers similarity.
 * Uses a recommendation system based on customers, considering ratings done.
 */
public class GetRecommendationByUsersAction extends BookstoreAction {
    private static final long serialVersionUID = 2798556125861360648L;

    int customerId;
    int numOfRecommendations;

    /**
     * Class constructor method.
     *
     * @param customerId           Unique identifier os customer that recommendations will be generated.
     * @param numOfRecommendations Maximum number of recommended books.
     */
    public GetRecommendationByUsersAction(int customerId, int numOfRecommendations) {
        this.customerId = customerId;
        this.numOfRecommendations = numOfRecommendations;
    }

    /**
     * Executes books recommendation based on customer ratings.
     *
     * @param bookstore Bookstore stream that has necessary data to generate recommendation.
     * @return A list of recommended books based on customer's similarity.
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
