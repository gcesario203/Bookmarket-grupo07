package servico.bookmarket.statemachine.actions.carts;

import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.ExpecificBookstoreAction;

/**
 * Class that adds shopping cart creation actions.
 */
public class CreateCartAction extends ExpecificBookstoreAction {

    private static final long serialVersionUID = 8255648428785854052L;

    long now;
    int customerId;

    /**
     * Constructor method for a specific {@linkplain Bookstore}.
     *
     * @param now
     */
    public CreateCartAction(int idBookstore, long now, int customerId) {
    	super(idBookstore);
        this.now = now;
        this.customerId = customerId;
        
    }

    /**
     * Creates a shopping cart for a specific {@linkplain Bookstore}
     *
     * @param bookstore
     * @return
     */
    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        return getBookstoreById(bookstore).createCart(customerId, now);
    }
}
