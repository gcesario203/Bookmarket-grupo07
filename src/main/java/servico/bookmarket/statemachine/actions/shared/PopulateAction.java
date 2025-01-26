package servico.bookmarket.statemachine.actions.shared;

import java.util.Random;
import java.util.stream.Stream;

import servico.Bookstore;
import servico.bookmarket.statemachine.actions.BookstoreAction;

/**
 * Classe que implementa as ações relacionadas a preenchimento dos dados do
 * {@linkplain Bookstore}.
 */
public class PopulateAction extends BookstoreAction {

    private static final long serialVersionUID = -5240430799502573886L;

    long seed;
    long now;
    int items;
    int customers;
    int addresses;
    int authors;
    int orders;

    /**
     * Método construtor da classe.
     *
     * @param seed
     * @param now
     * @param items
     * @param customers
     * @param addresses
     * @param authors
     * @param orders
     */
    public PopulateAction(long seed, long now, int items, int customers,
            int addresses, int authors, int orders) {
        this.seed = seed;
        this.now = now;
        this.items = items;
        this.customers = customers;
        this.addresses = addresses;
        this.authors = authors;
        this.orders = orders;
    }

    /**
     * Popula os dados do {@linkplain Bookstore}.
     *
     * @param bookstore
     * @return
     */
    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        Bookstore.populate(seed, now, items, customers, addresses, authors);
        Random rand = new Random(seed);
        bookstore.forEach(instance -> instance.populateInstanceBookstore(orders, rand, now));
        return true;
    }
}
