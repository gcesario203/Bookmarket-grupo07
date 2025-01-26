package servico.bookmarket.statemachine.actions.reviews;

import java.util.stream.Stream;

import dominio.Book;
import servico.Bookstore;
import servico.bookmarket.statemachine.actions.ExpecificBookstoreAction;

public class GetReviewByBookAction extends ExpecificBookstoreAction{
	private static final long serialVersionUID = 6439962163328790677L;
	
	Book book;
	
	public GetReviewByBookAction(Book book, int bookstoreId) {
		super(bookstoreId);
		this.book = book;
	}
	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		return getBookstoreById(bookstore).getReviewsByBook(this.book);
	}
}