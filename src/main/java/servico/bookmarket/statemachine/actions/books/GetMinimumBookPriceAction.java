package servico.bookmarket.statemachine.actions.books;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import dominio.Stock;
import servico.bookmarket.statemachine.actions.BookstoreAction;
import servico.bookstore.Bookstore;

public class GetMinimumBookPriceAction extends BookstoreAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int bookId;
	
	public GetMinimumBookPriceAction(int bookId) {
		this.bookId = bookId;
	}
	
	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		return bookstore.map(expecificBookstore -> expecificBookstore.getStock(bookId))
				 .filter(stock -> stock != null && stock.getQty() > 0)
				 .min(Comparator.comparingDouble(Stock::getCost));
	}

}
