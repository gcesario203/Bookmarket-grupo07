package servico.bookmarket.statemachine.actions.books;

import java.util.stream.Stream;

import dominio.Book;
import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.BookstoreAction;

/**
 * Classe que implementa as ações relacionadas a atualização do livro.
 */
public class UpdateBookAction extends BookstoreAction {

    private static final long serialVersionUID = -745697943594643776L;

    int bId;
    double cost;
    String image;
    String thumbnail;
    long now;

    /**
     * Método construtor da classe.
     *
     * @param id
     * @param cost
     * @param image
     * @param thumbnail
     * @param now
     */
    public UpdateBookAction(int id, double cost, String image,
            String thumbnail, long now) {
        bId = id;
        this.cost = cost;
        this.image = image;
        this.thumbnail = thumbnail;
        this.now = now;
    }

    /**
     * Executa o método na {@linkplain Bookstore} que atualiza os dados de
     * um livro específico.
     *
     * @param bookstore
     * @return
     */
    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        Bookstore.updateBook(bId, image, thumbnail, now);
        
        bookstore.forEach(bk -> bk.updateStock(bId, cost));
        return null;
    }
}