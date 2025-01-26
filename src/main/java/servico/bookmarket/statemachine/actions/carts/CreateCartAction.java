package servico.bookmarket.statemachine.actions.carts;

import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.ExpecificBookstoreAction;

/**
 * Classe que implementa as ações relacionadas a criação do carrinho de
 * compras.
 */
public class CreateCartAction extends ExpecificBookstoreAction {

    private static final long serialVersionUID = 8255648428785854052L;

    long now;

    /**
     * Método construtor do carrinho de compras para um
     * {@linkplain Bookstore} específico.
     *
     * @param now
     */
    public CreateCartAction(int idBookstore, long now) {
    	super(idBookstore);
        this.now = now;
    }

    /**
     * Cria um carrinho de compras para um {@linkplain Bookstore}
     * específico.
     *
     * @param bookstore
     * @return
     */
    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        return getBookstoreById(bookstore).createCart(now);
    }
}
