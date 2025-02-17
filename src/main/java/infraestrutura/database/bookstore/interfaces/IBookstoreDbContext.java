package infraestrutura.database.bookstore.interfaces;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dominio.Book;
import dominio.Cart;
import dominio.Order;
import dominio.Review;
import dominio.Stock;

/**
 * Interface responsável por conter a abstração que um banco de dados
 * de uma Bookstore deve possuir
 */
public interface IBookstoreDbContext {
    public Map<Book, Stock> getStockByBook();
    public List<Cart> getCartsById();
    public List<Review> getReviewsByIds();
    public List<Order> getOrdersById();
    public LinkedList<Order> getOrdersByCreation();
    public void flushDatabase();
}
