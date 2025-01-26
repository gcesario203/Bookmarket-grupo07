package servico.bookmarket.statemachine.actions.reviews;

import java.io.IOException;
import java.util.stream.Stream;

import servico.Bookstore;
import servico.bookmarket.statemachine.actions.ExpecificBookstoreAction;

public class ChangeReviewAction extends ExpecificBookstoreAction{
	private static final long serialVersionUID = 6239962163328790677L;
	
	int id;
	double value;
	
	public ChangeReviewAction(int id, double value, int bookstoreId) {
		super(bookstoreId);
		this.id = id;
		
		this.value = value;
	}

	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		try {
			return getBookstoreById(bookstore).changeReviewValue(id, value);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}