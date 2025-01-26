package servico.bookmarket.statemachine.actions.reviews;

import java.util.stream.Stream;

import dominio.Customer;
import servico.Bookstore;
import servico.bookmarket.statemachine.actions.ExpecificBookstoreAction;

public class GetReviewByCustomerAction extends ExpecificBookstoreAction{
	private static final long serialVersionUID = 6439962163328790677L;
	
	Customer customer;
	
	public GetReviewByCustomerAction(Customer customer, int bookstoreId) {
		super(bookstoreId);
		this.customer = customer;
	}
	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		return getBookstoreById(bookstore).getReviewsByCustomer(this.customer);
	}
}