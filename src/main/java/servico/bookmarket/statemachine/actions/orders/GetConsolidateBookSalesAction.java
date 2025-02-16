package servico.bookmarket.statemachine.actions.orders;

import java.util.HashMap;
import java.util.stream.Stream;

import servico.bookmarket.statemachine.actions.BookstoreAction;
import servico.bookstore.Bookstore;

/**
 * Ação da maquina de estado da Bookmarket responsável por pegar uma lista chave valor
 * dos livros que possuem vendas unificadas em uma unica bookstore
 * 
 * @param subject - tema do livro que serão retornados
 * @return Mapa onde a chave é o livro e o valor é a quantidade total vendida
 *         nesta livraria
 */
public class GetConsolidateBookSalesAction extends BookstoreAction
{
	private static final long serialVersionUID = 12121212145566633L;
	
	String subject;
	
	public GetConsolidateBookSalesAction(String subject) {
		this.subject = subject;
	}
	
	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        return bookstore
                .map(existingBookstore -> existingBookstore.getConsolidatedBookSales(this.subject))
                .reduce(new HashMap<>(), (accumulatedSales, currentStoreSales) -> {
                    currentStoreSales.forEach((book, quantity) -> accumulatedSales.put(book,
                            accumulatedSales.getOrDefault(book, 0) + quantity));
                    return accumulatedSales;
                });
	}

}
