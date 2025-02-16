package servico.bookmarket.statemachine.actions.orders;

import dominio.Book;
import servico.bookmarket.statemachine.actions.BookstoreAction;
import servico.bookstore.Bookstore;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Classe que implementa as ações relacionadas ao retorno do consolidado de vendas.
 */
public class getConsolidatedBookSalesAction extends BookstoreAction {
    private static final long serialVersionUID = -6180666666118139002L;

    public void getConsolidatedBookSalesAction() {
    }

    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        HashMap<Book, Integer> salesByBook = (HashMap<Book, Integer>) new getConsolidatedBookSalesAction().executeOnBookstore(bookstore);
        return salesByBook;
    }

}