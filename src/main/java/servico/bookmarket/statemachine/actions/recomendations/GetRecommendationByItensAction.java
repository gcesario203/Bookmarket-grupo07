package servico.bookmarket.statemachine.actions.recomendations;

import servico.bookmarket.statemachine.actions.reviews.GetUniqueReviewsAction;
import servico.bookstore.Bookstore;
import servico.bookstore.utils.MahoutUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import dominio.Review;
import servico.bookmarket.statemachine.actions.BookstoreAction;

/**
 * Generates books recomendation based on rated items before.
 * Uses recommendation system based on items, considering previous ratings done by costumer.
 */
public class GetRecommendationByItensAction extends BookstoreAction {
    private static final long serialVersionUID = 7439962163328790677L;

    int customerId;
    int numOfRecommendations;

    /**
     * Class constructor method.
     *
     * @param customerId           Identifier of a single user
     *                             that recommendation will be generated.
     * @param numOfRecommendations Maximum number of recommended books.
     */
    public GetRecommendationByItensAction(int customerId, int numOfRecommendations) {
        this.customerId = customerId;
        this.numOfRecommendations = numOfRecommendations;
    }

    /**
     * Executes books recommendation based on similarity of customer's rated items.
     *
     * @param bookstore Bookstore stream that has necessary data to generate recommendation.
     * @return A list of recommended books based on previous rated items similarity.
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
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        return recommendedBooks;
    }
}