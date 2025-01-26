package servico.bookmarket.statemachine.actions.customers;

import java.util.stream.Stream;

import servico.Bookstore;
import servico.bookmarket.statemachine.actions.BookstoreAction;

/**
 * Classe que implementa as ações relacionadas a sessão do cliente.
 */
public class RefreshCustomerSessionAction extends BookstoreAction {

    private static final long serialVersionUID = -5391031909452321478L;

    int cId;
    long now;

    /**
     * Método construtor da classe.
     *
     * @param id
     * @param now
     */
    public RefreshCustomerSessionAction(int id, long now) {
        cId = id;
        this.now = now;
    }

    /**
     * Executa a função do {@linkplain Bookstore} que atualiza a sessão do
     * cliente.
     *
     * @param bookstore
     * @return
     */
    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        Bookstore.refreshCustomerSession(cId, now);
        return null;
    }
}