package servico.bookmarket.statemachine.actions.books;

import java.util.stream.Stream;

import dominio.Book;
import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.BookstoreAction;

/**
 * This class adds book update actions.
 */
public class UpdateBookAction extends BookstoreAction {

    private static final long serialVersionUID = -745697943594643776L;

    int bId;
    double cost;
    String image;
    String thumbnail;
    long now;

    /**
     * Class constructor method.
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
     * Execute method on {@linkplain Bookstore} that updates data of a specific book
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