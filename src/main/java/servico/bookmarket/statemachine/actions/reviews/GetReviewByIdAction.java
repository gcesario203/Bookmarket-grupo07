package servico.bookmarket.statemachine.actions.reviews;

import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.ExpecificBookstoreAction;

public class GetReviewByIdAction extends ExpecificBookstoreAction{
	private static final long serialVersionUID = 4945075110938952124L;
	
	int id;
	
	public GetReviewByIdAction(int id, int bookstoreId) {
		super(bookstoreId);
		this.id = id;
	}
	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		return getBookstoreById(bookstore).getReviewById(this.id);
	}
}
