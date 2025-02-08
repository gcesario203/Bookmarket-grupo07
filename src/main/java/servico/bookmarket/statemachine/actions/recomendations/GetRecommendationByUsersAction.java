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

public class GetRecommendationByUsersAction extends BookstoreAction {
    private static final long serialVersionUID = 2798556125861360648L;

    int customerId;

    public GetRecommendationByUsersAction(int customerId) {
        this.customerId = customerId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        List<Review> reviews = (List<Review>) new GetUniqueReviewsAction().executeOn(bookstore);
        List<RecommendedItem> recommendedItems = MahoutUtils.recommendUserBased(reviews, this.customerId, 5);

        List<Object> recommendedBooks = recommendedItems.stream()
                .map(i -> Bookstore.getBook((int) i.getItemID()))
                .collect(Collectors.toList());

        return recommendedBooks;
    }
}
