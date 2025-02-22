package servico.bookmarket.statemachine.actions.reviews;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.BookstoreAction;
import dominio.Review;

/**
 * Action responsible for getting unique reviews, considering rating average, in case of duplication.
 * <p>
 * This method groups reviews by pair (customer, book) and, for each group, calculates average rating.
 * Then, creates a new instance of {@code Review} with same customer and book,and with rating being
 * calculated average.
 * If a {@code IOException} occurs during rating creation, a {@code RuntimeException} will be triggered.
 * </p>
 */
public class GetUniqueReviewsAction extends BookstoreAction {
    private static final long serialVersionUID = 7439962163328790678L;

    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        // Group reviews for compound keys by customer's id ad book
        Map<String, List<Review>> groupedReviews = bookstore
                .flatMap(b -> b.getReviews().stream())
                .collect(Collectors.groupingBy(r -> r.getCustomer().getId() + "-" + r.getBook().getId()));

        // For each group, calculates rating average and creates a unique review
        return groupedReviews.values().stream()
                .map(reviewsGroup -> {
                    Review baseReview = reviewsGroup.get(0);
                    double averageRating = reviewsGroup.stream()
                            .mapToDouble(Review::getRating)
                            .average()
                            .orElse(baseReview.getRating());
                    try {
                        return new Review(baseReview.getCustomer(), baseReview.getBook(), averageRating, 0);
                    } catch (IOException e) {
                        throw new RuntimeException("Error when creating average rating", e);
                    }
                })
                .collect(Collectors.toList());
    }
}
