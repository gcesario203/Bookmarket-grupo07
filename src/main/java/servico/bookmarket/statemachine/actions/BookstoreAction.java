package servico.bookmarket.statemachine.actions;

import java.io.Serializable;
import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.interfaces.Action;

/**
 * Abstract class to represent {@linkplain Bookstore} implementation actions patterns methods.
 */
public abstract class BookstoreAction implements Action<Stream<Bookstore>>,
        Serializable {

    /**
     * Determine on which {@linkplain Bookstore} action will be executed.
     *
     * @param sm
     * @return
     */
    @Override
    public Object executeOn(Stream<Bookstore> sm) {
        return executeOnBookstore(sm);
    }

    /**
     * Abstract method that will be implemented on a concrete class determining
     * which action will be executed on {@linkplain Bookstore}.
     *
     * @param bookstore
     * @return
     */
    public abstract Object executeOnBookstore(Stream<Bookstore> bookstore);
}
