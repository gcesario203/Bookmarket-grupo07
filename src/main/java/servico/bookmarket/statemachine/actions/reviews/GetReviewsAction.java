package servico.bookmarket.statemachine.actions.reviews;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import dominio.Review;
import servico.Bookstore;
import servico.bookmarket.statemachine.actions.BookstoreAction;

public class GetReviewsAction extends BookstoreAction{
	private static final long serialVersionUID = 7439962163328790677L;

	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		List<Review> reviews = new ArrayList<Review>();
		
		bookstore.forEach(b -> reviews.addAll(b.getReviews()));
		
		return reviews;
	}
}