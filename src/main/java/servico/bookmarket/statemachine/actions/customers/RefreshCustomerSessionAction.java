package servico.bookmarket.statemachine.actions.customers;

import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.BookstoreAction;

/**
 * Class that adds customer session actions.
 */
public class RefreshCustomerSessionAction extends BookstoreAction {

    private static final long serialVersionUID = -5391031909452321478L;

    int cId;
    long now;

    /**
     * Class constructor method.
     *
     * @param id
     * @param now
     */
    public RefreshCustomerSessionAction(int id, long now) {
        cId = id;
        this.now = now;
    }

    /**
     * Executes function of {@linkplain Bookstore} that updates customer session.
     *
     * @param bookstore
     * @return
     */
    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        try {
			Bookstore.refreshCustomerSession(cId, now);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
        return null;
    }
}