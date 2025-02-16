package servico.bookmarket.statemachine.actions.orders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import servico.bookmarket.statemachine.actions.BookstoreAction;
import servico.bookstore.Bookstore;

public class GetBestSellersAction extends BookstoreAction {

	private static final long serialVersionUID = 34893412122L;
	
	String subject;
	Integer numberOfBooks;
	
	public GetBestSellersAction(String subject, Integer numberOfBooks) {
		this.subject = subject;
		this.numberOfBooks = numberOfBooks;
	}
	
	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		return bookstore
						.map(existingBookstore -> existingBookstore.getBestSellers(subject, numberOfBooks))
						.flatMap(List::stream)
						.limit(numberOfBooks)
						.collect(Collectors.toList());
	}

}
