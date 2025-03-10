package servico.bookmarket.statemachine.actions.books;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dominio.Stock;
import servico.bookmarket.statemachine.actions.BookstoreAction;
import servico.bookstore.Bookstore;

/**
 * This class adds average book price search related actions.
 */
public class GetBookPriceAverageAction extends BookstoreAction{
	private static final long serialVersionUID = 1L;
	int bookId;
	
	public GetBookPriceAverageAction(int bookId) {
		this.bookId = bookId;
	}

	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		List<Stock> stocks = bookstore.map(bk -> bk.getStock(this.bookId))
									  	.filter(stock -> stock != null && stock.getQty() > 0)
										.collect(Collectors.toList());
		
		if(stocks.isEmpty())
			return 0d;
		
		return stocks.stream().mapToDouble(stock -> stock.getCost())
							  .sum() / stocks.size();
	}
}
