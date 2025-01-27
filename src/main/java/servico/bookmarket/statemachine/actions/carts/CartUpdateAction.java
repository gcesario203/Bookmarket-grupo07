package servico.bookmarket.statemachine.actions.carts;

import java.util.List;
import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.ExpecificBookstoreAction;

/**
 * Classe que implementa as ações relacionadas a atualização do carrinho de
 * compras.
 */
public class CartUpdateAction extends ExpecificBookstoreAction {

    private static final long serialVersionUID = -6062032194650262105L;

    final int cId;
    final Integer bId;
    final List<Integer> bIds;
    final List<Integer> quantities;
    final long now;

    /**
     * Método construtor da classe.
     *
     * @param id
     * @param id2
     * @param ids
     * @param quantities
     * @param now
     */
    public CartUpdateAction(int storeId, int id, Integer id2, List<Integer> ids,
            List<Integer> quantities, long now) {
    	super(storeId);
        cId = id;
        bId = id2;
        bIds = ids;
        this.quantities = quantities;
        this.now = now;
    }

    /**
     * Atualiza um carrinho de compras em um {@linkplain Bookstore}.
     *
     * @param bookstore
     * @return
     */
    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        return getBookstoreById(bookstore).cartUpdate(cId, bId, bIds, quantities, now);
    }
}
