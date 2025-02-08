package servico.bookmarket.statemachine.actions.reviews;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.BookstoreAction;

public class GetUniqueReviewsAction extends BookstoreAction {
	private static final long serialVersionUID = 7439962163328790678L;

	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		return bookstore
				.flatMap(b -> b.getReviews().stream())
				.collect(Collectors.toMap(
						r -> r.getCustomer().getId() + "-" + r.getBook().getId() + "-" + r.getBookstoreId(),
						r -> r,
						(existing, replacement) -> existing))
				.values()
				.stream()
				.collect(Collectors.toList());
	}
}
