package servico.bookmarket.statemachine.actions;

import java.io.Serializable;
import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.interfaces.Action;

/**
 * Classe abstrata para representar os métodos padrões da implementação das
 * ações na {@linkplain Bookstore}.
 *
 */
public abstract class BookstoreAction implements Action<Stream<Bookstore>>,
        Serializable {

    /**
     * Determina em qual {@linkplain Bookstore} a ação será executada.
     *
     * @param sm
     * @return
     */
    @Override
    public Object executeOn(Stream<Bookstore> sm) {
        return executeOnBookstore(sm);
    }

    /**
     * Método abstrato que será implementado na classe concreta determinando
     * qual ação será executada no {@linkplain Bookstore}.
     *
     * @param bookstore
     * @return
     */
    public abstract Object executeOnBookstore(Stream<Bookstore> bookstore);
}
