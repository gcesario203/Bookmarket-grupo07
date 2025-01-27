package servico.bookmarket.statemachine.actions.reviews;

import java.util.stream.Stream;

import dominio.Book;
import dominio.Customer;
import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.ExpecificBookstoreAction;

public class CreateReviewAction extends ExpecificBookstoreAction {
	private static final long serialVersionUID = 6039962163328790677L;
	
	Customer customer;
	Book book;
	double value;
	
	public CreateReviewAction(Customer customer, Book book, double value, int bookstoreId) {
		super(bookstoreId);
		this.customer = customer;
		
		this.book = book;
		
		this.value = value;
	}
	
	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		try {
			return getBookstoreById(bookstore).createReview(this.customer, this.book, this.value);	
		}
		catch (Exception e) {
			throw new RuntimeException(e);
        }
	}
}