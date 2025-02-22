package servico.bookmarket.statemachine.actions.reviews;

import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.ExpecificBookstoreAction;

public class GetReviewsByBookstoreAction extends ExpecificBookstoreAction{

	private static final long serialVersionUID = 8136710222147009776L;

	public GetReviewsByBookstoreAction(int bookstoreId) {
		super(bookstoreId);
	}
	
	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		return getBookstoreById(bookstore).getReviews();
	}
}