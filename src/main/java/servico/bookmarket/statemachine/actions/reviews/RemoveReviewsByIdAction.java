package servico.bookmarket.statemachine.actions.reviews;

import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.ExpecificBookstoreAction;

public class RemoveReviewsByIdAction extends ExpecificBookstoreAction{
	private static final long serialVersionUID = 1535510251114648637L;
	
	int id;
	
	public RemoveReviewsByIdAction(int id, int bookstoreId) {
		super(bookstoreId);
		this.id = id;
	}
	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		return getBookstoreById(bookstore).removeReviewById(this.id);
	}
}
